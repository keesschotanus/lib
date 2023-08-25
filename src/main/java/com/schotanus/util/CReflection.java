/*
 * Copyright (C) 2002 Kees Schotanus
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.schotanus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;


/**
 * Utility class to aid in using reflection.<br>
 * Two groups of methods exist in this class.
 * <ol>
 *   <li>Methods that get methods and fields from a class<li>
 *   <li>Methods that aid in evaluating an expression.</li>
 * </ol>
 * An expression that may be evaluated is: bean.nestedBean.anotherNestedBean.integerArray[3]<br>
 * This assumes that the object you use has a getBean() method, and that this bean in turn contains getNestedBean().
 * The result of getNestedBean() should be another bean containing a getIntegerArray() method returning an integer array, from
 * which the fourth element is etched.
 * See {@link #evaluatePropertyExpression(Object, String)} for details.
 */
public final class CReflection {

    /**
     * Mapping of primitive class types to a default value.<br>
     * This map is necessary when invoking a method using {@link Method#invoke(Object, Object...)} and one of the arguments is
     * of primitive type.
     */
    private static final Map<Class<?>, Object> primitiveClassToDefaultValue = new HashMap<>();

    /*
     * Initialize the map of primitive class types to their corresponding default value.
     */
    static {
        primitiveClassToDefaultValue.put(Boolean.TYPE, Boolean.FALSE);
        primitiveClassToDefaultValue.put(Byte.TYPE, (byte)0);
        primitiveClassToDefaultValue.put(Short.TYPE, (short)0);
        primitiveClassToDefaultValue.put(Character.TYPE, (char)0);
        primitiveClassToDefaultValue.put(Integer.TYPE, 0);
        primitiveClassToDefaultValue.put(Long.TYPE, 0L);
        primitiveClassToDefaultValue.put(Float.TYPE, 0F);
        primitiveClassToDefaultValue.put(Double.TYPE, 0D);
    }

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CReflection.class);

    /**
     * Prefixes for getters.<br>
     * Getters normally start with "get" but for booleans they can also start with "is" or "has".
     */
    private static final String[] GET_METHOD_PREFIXES = {"get", "is", "has"};

    /**
     * Predicate to filter public static methods.
     */
    public static final Predicate<Method> isPublicStaticMethod =
            method -> Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers());

    /**
     * Predicate to filter getters.<br>
     * A getter is a public, non-static method that accepts no parameters and returns a scalar value.
     * Its name starts with "get", "is" or "has".
     * No check is made if the return type of the "is" or "has" methods is a boolean type.
     */
    public static final Predicate<Method> isGetter = method ->
            (method.getName().startsWith("get") || method.getName().startsWith("is") || method.getName().startsWith("has"))
                    && Modifier.isPublic(method.getModifiers())
                    && !Modifier.isStatic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && !method.getReturnType().equals(void.class);

    /**
     * Predicate to filter setters.<br>
     * A setter is a public, non-static method that accepts no parameters and returns a scalar value.
     * Its name starts with "set".
     */
    public static final Predicate<Method> isSetter = method -> method.getName().startsWith("set")
                && Modifier.isPublic(method.getModifiers())
                && !Modifier.isStatic(method.getModifiers())
                && method.getParameterTypes().length == 1
                && method.getReturnType().equals(void.class);

    /**
     * Creates a predicate to filter on methods that are annotated with the supplied annotation.
     * @param annotation Annotation the method should have.
     * @return A method predicate filtering on annotations.
     */
    public static Predicate<Method> isMethodAnnotatedWith(Class<? extends Annotation> annotation) {
        return method -> method.getAnnotation(annotation) != null;
    }

    /**
     * Creates a predicate to filter on fields that are annotated with the supplied annotation.
     * @param annotation Annotation the field should have.
     * @return A field predicate filtering on annotations.
     */
    public static Predicate<Field> isFieldAnnotatedWith(Class<? extends Annotation> annotation) {
        return field -> field.getAnnotation(annotation) != null;
    }

    /**
     * Prevents the creation of an instance of this utility class.
     */
    private CReflection() {
    }

    /**
     * Gets all declared methods of the supplied class.<br>
     * This list excludes synthetic methods from jacoco for example.
     * @param theClass The class to get the methods from.
     * @param predicate The predicate to filter the methods.
     *  When null, no predicate will be applied.
     * @return List of all declared methods that adhere to the supplied predicate.
     */
    public static List<Method> getDeclaredMethods(final Class<?> theClass, Predicate<Method> predicate) {
        return Arrays.stream(
                theClass.getDeclaredMethods())
                .filter(method -> !method.isSynthetic())
                .filter(predicate == null ? method -> true : predicate)
                .toList();
    }

    /**
     * Gets all methods of the supplied class, including the methods declared at any super class level, up-to, but not including
     * Object.<br>
     * This prevents methods like: getClass() from appearing in the list.<br>
     * Note: This ist excludes synthetic methods.
     * @param theClass The class to get the methods from.
     * @param predicate The predicate to filter the methods.
     *  When null, no predicate will be applied.
     * @return List of all methods that adhere to the supplied predicate.
     */
    public static List<Method> getMethods(final Class<?> theClass, Predicate<Method> predicate) {
        final List<Method> result = new ArrayList<>();
        Class<?> currentClass = theClass;
        while (currentClass != null && currentClass != Object.class) {
            result.addAll(CReflection.getDeclaredMethods(currentClass, predicate));
            currentClass = currentClass.getSuperclass();
        }

        return result;
    }

    /**
     * Gets all declared fields of the supplied class, excluding synthetic members added by code coverage tools.
     * @param theClass The class to get the fields from.
     * @param predicate Predicate to filter the fields.
     *  When null, no predicate will be applied.
     * @return List of all declared (non-synthetic) fields that adhere to the supplied predicate.
     */
    public static List<Field> getDeclaredFields(final Class<?> theClass, Predicate<Field> predicate) {
        return Arrays.stream(theClass.getDeclaredFields())
                .filter(predicate == null ? filed -> true : predicate)
                .filter(field -> !field.isSynthetic())
                .toList();
    }
    /**
     * Gets all the fields of the supplied class.<br>
     * This includes all the fields declared in the super classes of the supplied class, but excludes the synthetic members that
     * may have been added by code coverage tools.
     * @param theClass The class to get the fields from.
     * @param predicate Predicate to filter the fields.
     *  When null, no predicate will be applied.
     * @return All the fields declared in the supplied class.<br>
     *  Fields are ordered from the supplied class up to, but not including, fields of class Object.
     */
    public static List<Field> getFields(final Class<?> theClass, final Predicate<Field> predicate) {
        final List<Field> result = new ArrayList<>();
        Class<?> currentClass = theClass;
        while (currentClass != null && currentClass != Object.class) {
            result.addAll(CReflection.getDeclaredFields(currentClass, predicate));
            currentClass = currentClass.getSuperclass();
        }

        return result;
    }

    /**
     * Gets the value of the supplied property expression from the supplied bean.<br>
     * The supplied propertyExpression may be a simple property name like "name", but it may also be a chained property name
     * like: "company.name".
     * In the latter case, it is assumed that a getCompany() method exists on the supplied bean and that it returns an Object
     * that in turn has a getName() method.<br>
     * Finally, it is possible to use an index like: "name[0]" for example.
     * In this case, "name" should resolve to an array or list.
     * @param bean The bean to get the property value from.
     * @param propertyExpression The property expression to evaluate.<br>
     *  A property expression looks like this: propertyName([int|"n"])*(.propertyName([int|"n"])*)*
     * @return The value corresponding to the supplied property expression.
     * @throws ReflectiveOperationException When the supplied property expression is empty or when it can not be evaluated.
     * @throws NullPointerException When either the supplied bean or property expression is null.
     */
    public static Object evaluatePropertyExpression(final Object bean, final String propertyExpression)
            throws ReflectiveOperationException {
        Object result = Objects.requireNonNull(bean);
        Objects.requireNonNull(propertyExpression);
        if (propertyExpression.trim().length() == 0) {
            throw new IllegalArgumentException("propertyExpression may not be empty!");
        }

        // Split the property expression in property names
        final String[] propertyNames = propertyExpression.split("\\.");

        // Execute the corresponding getters
        for (final String propertyName : propertyNames) {
            final String scalarPropertyName = getScalarPropertyName(propertyName);
                final int openingBracketIndex = propertyName.indexOf("[");
                if (openingBracketIndex == -1) {
                    // Scalar property
                    result = CReflection.invokeGetMethod(result, scalarPropertyName);
                } else {
                    // Indexed property
                    final String index = propertyName.substring(openingBracketIndex);
                    result = evaluatePropertyIndexExpression(result, scalarPropertyName, index);
                }
        }

        return result;
    }

    /**
     * Gets the scalar (without an index) property name of the supplied propertyName.<br>
     * For example: both getScalarPropertyName("age") and getScalarPropertyName("age[0]") would return "age".<br>
     * Note: This method does not test whether the supplied property name is a valid Java property name.
     * "age{...}" for example is happily accepted and returned as-is, since it has no brackets.
     * @param propertyName The property name to get the scalar property name from.
     * @return The scalar property name.<br>
     * When the supplied property name already represents a scalar, it is returned as-is.
     * @throws IllegalArgumentException When the opening and closing brackets in the property name don't match.
     * @throws NullPointerException When the supplied property name is null.
     */
    public static String getScalarPropertyName(final String propertyName) {
        CString.checkMatchingOpenAndCloseCharacters(Objects.requireNonNull(propertyName), '[', ']');

        final int openingBracketIndex = propertyName.indexOf('[');
        return openingBracketIndex == -1 ? propertyName : propertyName.substring(0, openingBracketIndex);
    }

    /**
     * Evaluates the supplied index expression against the supplied bean.<br>
     * Assuming the index expression is: [0], then this method would get the first element from the supplied bean.
     * Naturally the supplied bean must either be an array or a list.
     * @param bean The bean against which the index expression is evaluated.
     * @param propertyName The property name.
     * @param indexExpression The index expression to evaluate.<br>
     *  The index expression must be in the form: [int] or [n].
     *  [3], for example would get the fourth element of an array or the third element of a list.
     *  [n] would get the last element from the supplied bean.<br>
     *  Note: You can't use [n] with methods like getPropertyName(int index) as there is no way of knowing the size of the
     *  underlying object.
     * @return The result of evaluating the supplied index expression against the supplied bean.
     * @throws NumberFormatException When the supplied index expression is not in a valid format.
     * @throws NullPointerException When one of the supplied parameters is null.
     * @throws ReflectiveOperationException When the getter could not be invoked.
     */
    private static Object evaluatePropertyIndexExpression(
            final Object bean, final String propertyName, final String indexExpression) throws ReflectiveOperationException {
        Object result = Objects.requireNonNull(bean);
        Objects.requireNonNull(propertyName);
        Objects.requireNonNull(indexExpression);

        if (hasGetterWithIndexMethod(result, propertyName)) {
            final int index = Integer.parseInt(indexExpression.substring(1, indexExpression.length() -1));
            result = invokeGetMethod(result, propertyName, index);
        } else {
            result = invokeGetMethod(result, propertyName);
            // Loop through all indices
            for (int indexOfOpeningBracket = indexExpression.indexOf('[');
                 indexOfOpeningBracket != -1;
                 indexOfOpeningBracket = indexExpression.indexOf('[', indexOfOpeningBracket + 1)) {

                // Get the index expression without the brackets
                final String index = indexExpression.substring(
                        indexOfOpeningBracket + 1, indexExpression.indexOf(']', indexOfOpeningBracket)).trim();

                result = getNonScalarPropertyValue(result, propertyName, index);
            }
        }
        return result;
    }


    /**
     * @param bean The bean to get the property value from.
     * @param propertyName Name of the property.
     * @param index The array index or ordinal for a list.
     * @return The property value.
     * @throws IllegalArgumentException When the supplied bean is neither a list nor an array.
     * @throws NullPointerException When the supplied bean is null.
     */
    private static Object getNonScalarPropertyValue(final Object bean, final String propertyName, final String index) {
        Object result = Objects.requireNonNull(bean);
        if (result instanceof List<?> listBean) {
            final int listIndex = "n".equals(index) ? listBean.size() - 1 : Integer.parseInt(index);
            result = listBean.get(listIndex);
        } else if (result.getClass().isArray()) {
            final Object[] arrayBean = (Object[])result;
            final int arrayIndex = "n".equals(index) ? arrayBean.length - 1 : Integer.parseInt(index);
            result = arrayBean[arrayIndex];
        } else {
            throw new IllegalArgumentException(String.format("The property: %s, must be a List or array but is of type: %s",
                    propertyName, result.getClass().getName()));
        }
        return result;
    }

    /**
     * Determines if the supplied bean has a getPropertyName(int index) method.
     * @param bean The bean to check for a getter with an index.
     * @param propertyName Name of the property from which the name of the getter is derived.
     * @return True when the supplied bean has a getPropertyName(int index) method.
     */
    public static boolean hasGetterWithIndexMethod(final Object bean, final String propertyName) {
        Objects.requireNonNull(bean);
        final String getMethodName = CReflection.propertyToGetMethod(Objects.requireNonNull(propertyName));

        boolean result = false;
        try {
            bean.getClass().getMethod(getMethodName, int.class);
            result = true;
        } catch (final NoSuchMethodException exception) {
            assert true;
        }

        return result;
    }

    /**
     * Invokes the get...() method corresponding to the supplied property name.<br>
     * Note: for boolean properties it is customary to use is...() or has...() instead of get...() so this method will attempt
     * to invoke these methods when the get...() method fails.
     * @param object Object on which the get...() method will be invoked.
     * @param propertyName Name of the property.
     *  <br>This name determines what get...() method should be invoked.
     * @return The object that is the result of object.getPropertyName().
     * @throws ReflectiveOperationException When an error occurs while invoking the method.
     * @throws NullPointerException When the supplied object or property name is null.
     * @throws NoSuchMethodException When no method corresponds to the supplied property name.
     */
    public static Object invokeGetMethod(final Object object, final String propertyName) throws ReflectiveOperationException  {
        Objects.requireNonNull(object);
        Objects.requireNonNull(propertyName);

        final Method getMethod = propertyToGetMethod(object, propertyName);
        return getMethod.invoke(object);
    }

    /**
     * Invokes the get...(index) method corresponding to the supplied property name.<br>
     * Note: for boolean properties it is customary to use is...() or has...() instead of get...() so this method will attempt
     * to invoke these  methods when the get...() method fails.
     * @param object Object on which the get...() method will be invoked.
     * @param propertyName Name of the property.<br>
     *  This name determines what get...() method should be invoked.
     * @param index Index of the array element that should be fetched.
     * @return The object that is the result of object.getPropertyName(index).
     * @throws ReflectiveOperationException When an error occurs while invoking the method.
     * @throws NullPointerException When the supplied object is null or when the supplied property name is null.
     * @throws NoSuchMethodException When no method corresponds to the supplied property name.
     */
    public static Object invokeGetMethod(final Object object, final String propertyName, final int index)
            throws ReflectiveOperationException {
        Objects.requireNonNull(object);
        Objects.requireNonNull(propertyName);

        for (final String methodPrefix : GET_METHOD_PREFIXES) {
            final String methodName = methodPrefix
                    + propertyName.substring(0, 1).toUpperCase(Locale.getDefault()) + propertyName.substring(1);
            try {
                final Method method = object.getClass().getMethod(methodName, Integer.TYPE);
                return method.invoke(object, index);
            } catch (final NoSuchMethodException ignore) {
                assert true; // Ignore and try a new method.
            }
        }

        throw new NoSuchMethodException(String.format("Could not invoke:(get|is|has) for property:%s, index=%d on:%s",
                propertyName, index, object.getClass().getName()));
    }

    /**
     * Invokes the set...(propertyValue) method that corresponds to the supplied property.
     * @param object Object on which the set...() method will be invoked.
     * @param propertyName Name of the property.
     * @param propertyValue Value to which the property should be set.
     * @param propertyClass Class of the property.<br>
     *  When this parameter is null the class is taken from the supplied propertyValue.
     *  Supply a non-null value when the property is of primitive type.
     * @throws ReflectiveOperationException When an error occurs while invoking the method.
     * @throws NullPointerException When the supplied object is null or when the supplied propertyName is null.
     * @throws IllegalArgumentException When both the property value and the property class are null.
     */
    public static void invokeSetMethod(
            final Object object,
            final String propertyName,
            final Object propertyValue,
            final Class<?> propertyClass)
            throws ReflectiveOperationException {
        Objects.requireNonNull(object);
        if (propertyClass == null && propertyValue == null) {
            throw new IllegalArgumentException("propertyValue and propertyClass can't both be null!");
        }

        final Method method = object.getClass().getMethod(propertyToSetMethod(Objects.requireNonNull(propertyName)),
                propertyClass == null ? propertyValue.getClass() : propertyClass);
        method.invoke(object, propertyValue);
    }

    /**
     * Converts the supplied method name to the corresponding property name.<br>
     * This method assumes that the supplied method consists of a lower case word, followed by the name of the property,
     * where the property name starts with an upper case character.
     * @param method Method name.<br>
     *  Should be the name of a method that is either a getter or a setter.
     * @return Property name corresponding to the supplied method name.
     * @throws IllegalArgumentException When the supplied method could not be converted to a property name.
     * @throws NullPointerException When the supplied method is null.
     */
    public static String methodToProperty(final String method) {
        for (int i = 0; i < Objects.requireNonNull(method).length(); ++i) {
            if (Character.isUpperCase(method.charAt(i))) {
                // Convert found char to lower case and remove preceding chars.
                return Character.toLowerCase(method.charAt(i)) + method.substring(i + 1);
            }
        }

        // Error, no upper case character found in method.
        throw new IllegalArgumentException(
                String.format("Could not convert method: %s, to property name.", method));
    }

    /**
     * Gets the name of the method that would get the supplied property.<br>
     * For a property named 'thing' this method would return getThing.
     * @param property The name of the property.
     * @return The name of the method that would get the supplied property.
     * @throws NullPointerException When the supplied property is null.
     */
    public static String propertyToGetMethod(final String property) {
        return "get" + Objects.requireNonNull(property).substring(0, 1).toUpperCase(Locale.getDefault())
                + property.substring(1);
    }

    /**
     * Gets the method that would get the supplied property.<br>
     * For a property named 'thing' for example, the getter is normally named getThing().
     * When this method exists, it is returned.
     * Additionally, the methods called isThing() and hasThing() are checked for existence.
     * @param object The object that contains the supplied property.
     * @param property Name of the property.
     * @return The method that corresponds to the getter of the supplied property.
     * @throws NoSuchMethodException When no getter method exists that corresponds to the supplied property.
     * @throws NullPointerException When either the supplied object or property is null.
     */
    public static Method propertyToGetMethod(final Object object, final String property) throws NoSuchMethodException {
        for (final String methodPrefix : GET_METHOD_PREFIXES) {
            final String methodName = methodPrefix
                    + property.substring(0, 1).toUpperCase(Locale.getDefault()) + property.substring(1);
            try {
                return object.getClass().getMethod(methodName);
            } catch (final NoSuchMethodException ignore) {
                assert true; // Ignore and try a new method.
            }
        }

        throw new NoSuchMethodException(String.format(String.format("No getter (get|is|has) exists for property: %s on: %s",
                property, object.getClass().getName())));
    }

    /**
     * Creates the name of the set...() method from the supplied property name.<br>
     * Technically this method converts the first character of the property name to upper case and prepends "set" to the result.
     * @param property Name of the property.
     * @return Name of the set method corresponding to the supplied parameter.
     * @throws NullPointerException When the supplied property is null.
     */
    public static String propertyToSetMethod(final String property) {
        return "set" + property.substring(0, 1).toUpperCase(Locale.getDefault())
                + property.substring(1);
    }

    /**
     * Clears all member fields on the supplied object.<br>
     * Effectively this method calls all setters with the appropriate default value corresponding to the type of the parameter.
     * Primitive booleans are initialized to false, remaining primitives to 0 and references to null.<br>
     * Fields that could not be cleared, appear in the log as warnings.
     * @param object The object for which all fields must be cleared.
     * @throws NullPointerException When the supplied object is null.
     */
    public static void clearAllMembers(final Object object) {
        final List<Method> setters = getMethods(object.getClass(), CReflection.isSetter);
        for (final Method setter : setters) {
            /*
             * Get the default value for a primitive type.
             * When the type is not primitive the default value is null.
             */
            final Class<?> parameterType = setter.getParameterTypes()[0];
            final Object parameterValue = primitiveClassToDefaultValue.get(parameterType);
            try {
                setter.invoke(object, parameterValue);
            } catch (final IllegalArgumentException | ReflectiveOperationException exception) {
                logger.atWarn().log(String.format("Could not clear field with setter: %s on: %s",
                        setter.getName(), object.getClass().getName()), exception);
            }
        }
    }

    /**
     * Determines what method called the method with the supplied methodName.<br>
     * Be careful with using constructors and initializers. They are coded as &lt;init&gt; and &lt;clinit&gt;.
     * Executing the following code:
     * <pre><code>CReflection.getCallingMethod("com.schotanus.util.CReflectionTest.&lt;init&gt;</code></pre>
     * results in: "newInstance", for example.<br>
     * Note: Do not use this method in combination with Aspect/J or comparable tools as this method will fail and return an
     * Aspect/J method instead of the method you want.
     * @param methodName Name of the method for which the caller must be returned.<br>
     *  To be absolutely sure you that you get the correct result, you may want to fully qualify the name of the method, e.g.:
     *  com.schotanus.util.CFile.readTextFile.<br>
     *  Note: don't add parenthesis to the case-sensitive name.
     * @return Simple, unqualified name of the method that called the method with the supplied methodName or null when no
     *  caller could be located.
     */
    public static String getCallingMethod(final String methodName) {
        String result = null;

        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTraceElements.length; ++i) {
            final String stackMethodName = methodName.indexOf('.') == -1
                    ? stackTraceElements[i].getMethodName()
                    : stackTraceElements[i].getClassName() + "." + stackTraceElements[i].getMethodName();
            if (stackMethodName.equals(methodName)) {
                // The method calling us is the next stack trace element (since the most recent call is at the top)
                result = stackTraceElements[i + 1].getMethodName();
                break;
            }
        }

        return result;
    }

}
