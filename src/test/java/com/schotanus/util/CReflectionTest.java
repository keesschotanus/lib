/* 
 * Copyright (C) 2009 Kees Schotanus
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.schotanus.util;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link com.schotanus.util.CReflection}.<br>
 * Generally speaking, the order in which methods for example are returned using reflection, is not determined and can vary from
 * run to run.
 * This is normally solved by getting all the methods in a set and then check whether all expected methods are present.
 */
class CReflectionTest {

    @Test
    void testIsPublicStaticMethod() throws NoSuchMethodException {
        final List<Method> staticMethods = CReflection.getDeclaredMethods(Laboratory.class, CReflection.isPublicStaticMethod);

        assertEquals(3, staticMethods.size());
        assertTrue(staticMethods.contains(Laboratory.class.getMethod("cleanTestTubes")));
        assertTrue(staticMethods.contains(Laboratory.class.getMethod("isSafe")));
        assertTrue(staticMethods.contains(Laboratory.class.getMethod("getControl")));
    }

    @Test
    void testIsGetter() throws NoSuchMethodException {
        final List<Method> getters = CReflection.getDeclaredMethods(Laboratory.class, CReflection.isGetter);

        assertEquals(3, getters.size());
        assertTrue(getters.contains(Laboratory.class.getMethod("getTestTubes")));
        assertTrue(getters.contains(Laboratory.class.getMethod("getName")));
        assertTrue(getters.contains(Laboratory.class.getMethod("getOpen")));
    }

    @Test
    void testIsSetter() {
        final List<Method> setters = CReflection.getDeclaredMethods(Laboratory.class, CReflection.isSetter);

        assertEquals(2, setters.size());
    }

    @Test
    void testIsMethodAnnotatedWith() {
        final List<Method> annotatedMethods =
                CReflection.getDeclaredMethods(Laboratory.class, CReflection.isMethodAnnotatedWith(ReflectionAnnotation.class));

        assertEquals(2, annotatedMethods.size());
    }

    @Test
    void testIsFieldAnnotatedWith() {
        final List<Field> annotatedFields =
                CReflection.getDeclaredFields(Laboratory.class, CReflection.isFieldAnnotatedWith(ReflectionAnnotation.class));

        assertEquals(1, annotatedFields.size());
    }

    @Test
    void testGetDeclaredMethods() throws NoSuchMethodException {
        // Get all methods from MedicalLaboratory
        final List<Method> declaredMethods = CReflection.getDeclaredMethods(MedicalLaboratory.class, null);
        assertEquals(4, declaredMethods.size());

        // Get all methods from Laboratory starting with "get"
        final List<Method> declaredGetMethods = CReflection.getDeclaredMethods(Laboratory.class,
                method -> method.getName().startsWith("get"));
        assertEquals(Laboratory.class.getMethod("getName"), declaredGetMethods.get(0));
    }

    @Test
    void testGetMethods() throws NoSuchMethodException {
        // Get all methods from MedicalLaboratory and superclasses
        final List<Method> methods = CReflection.getMethods(MedicalLaboratory.class, null);
        // Test that a method from MedicalLaboratory is present.
        assertTrue(methods.contains(MedicalLaboratory.class.getMethod("getName")));
        // Test that a method from Laboratory is present.
        assertTrue(methods.contains(Laboratory.class.getMethod("getName")));
    }

    @Test
    void testGetDeclaredFields() throws NoSuchFieldException {
        // Get all fields from MedicalLaboratory
        final List<Field> declaredFields = CReflection.getDeclaredFields(MedicalLaboratory.class, null);
        assertEquals(1, declaredFields.size());

        // Get fields from Laboratory using a predicate
        final List<Field> declaredFieldsOnLaboratory = CReflection.getDeclaredFields(Laboratory.class,
                fields -> fields.getName().startsWith("l"));
        assertEquals(Laboratory.class.getDeclaredField("level"), declaredFieldsOnLaboratory.get(0));
    }

    @Test
    void testGetFields() throws NoSuchFieldException {
        // Get all fields from MedicalLaboratory and super classes
        final List<Field> fields = CReflection.getFields(MedicalLaboratory.class, null);
        // Test that a field from MedicalLaboratory is present.
        assertTrue(fields.contains(MedicalLaboratory.class.getDeclaredField("name")));
        // Test that a field from Laboratory is present.
        assertTrue(fields.contains(Laboratory.class.getDeclaredField("level")));
    }

    /**
     * Tests {@link CReflection#evaluatePropertyExpression(Object, String)} with simple properties..
     */
    @Test
    void testEvaluatePropertyExceptionWithSimpleProperty() throws ReflectiveOperationException {
        final Left bean = new Left();

        // Test with empty property
        assertThrows(IllegalArgumentException.class, () -> CReflection.evaluatePropertyExpression(bean, " "));

        // Test with simple String property
        assertEquals("simpleString", CReflection.evaluatePropertyExpression(bean, "simpleStringProperty"));

        // Test with primitive int property
        assertEquals(17, CReflection.evaluatePropertyExpression(bean, "primitiveIntProperty"));

        // Test with Long property
        assertEquals(19L, CReflection.evaluatePropertyExpression(bean, "longProperty"));

        // Test with non-existing property
        assertThrows(NoSuchMethodException.class, () -> CReflection.evaluatePropertyExpression(bean, "nonExistingProperty"));
    }

    /**
     * Tests {@link CReflection#evaluatePropertyExpression(Object, String)} with simple properties..
     */
    @Test
    void testEvaluatePropertyExceptionWithCompoundProperty() throws ReflectiveOperationException {
        final Left bean = new Left();

        // Test with nested bean which in turn contains a list.
        assertEquals(1, CReflection.evaluatePropertyExpression(bean, "right.simpleList[1]"));

        // Test with nested bean which in turn contains a list from which we get the last element.
        assertEquals(2, CReflection.evaluatePropertyExpression(bean, "right.simpleList[n]"));

    }

    /**
     * Tests {@link CReflection#evaluatePropertyExpression(Object, String)} with single indexed properties.
     */
    @Test
    void testEvaluatePropertyExceptionWithSingleIndexedProperty() throws ReflectiveOperationException {
        final Left bean = new Left();

        // Test with property indexed by an array
        assertEquals(1, CReflection.evaluatePropertyExpression(bean, "integerArray[1]"));

        // Test getting the last element of the array
        assertEquals(2, CReflection.evaluatePropertyExpression(bean, "integerArray[n]"));

        // Test with property indexed by a list
        assertEquals(4, CReflection.evaluatePropertyExpression(bean, "integerList[3]"));

        // Test getting the last element of the list
        assertEquals(5, CReflection.evaluatePropertyExpression(bean, "integerList[n]"));

        // Test getting an indexed property but the property is not indexed.
        assertThrows(IllegalArgumentException.class, () -> CReflection.evaluatePropertyExpression(bean, "longProperty[1]"));

        // Test getting a property where the getter uses an index
        assertEquals(32L, CReflection.evaluatePropertyExpression(new Left(), "longPropertyWithIndex[16]"));

        // Test getting a property with an index, but the property does not exist
        assertThrows(NoSuchMethodException.class,
                () -> CReflection.evaluatePropertyExpression(new Left(), "nonExistingProperty[13]"));
    }

    /**
     * Tests {@link CReflection#evaluatePropertyExpression(Object, String)} with multiple indexed properties.
     */
    @Test
    void testEvaluatePropertyExceptionWithMultipleIndexedProperty() throws ReflectiveOperationException {
        final Left bean = new Left();
        assertEquals(5, CReflection.evaluatePropertyExpression(bean, "twoDimensionalIntegerArray[1][2]"));
    }

    /**
     * Tests {@link CReflection#invokeGetMethod(Object, String, int)}
     */
    @Test
    void testInvokeGetMethodWithIndexedProperty() {
        // Test with non-existing property
        assertThrows(NoSuchMethodException.class,
                () -> CReflection.invokeGetMethod(new Left(), "nonExistingProperty", 13));
    }

    /**
     * Tests {@link CReflection#invokeSetMethod(Object, String, Object, Class)}.
     */
    @Test
    void testInvokeSetMethod() throws ReflectiveOperationException {
        final MedicalLaboratory medicalLaboratory = new MedicalLaboratory();

        // Test invoking a setter and then checking the result using a getter
        CReflection.invokeSetMethod(medicalLaboratory, "name", "lab", String.class);
        assertEquals("lab", medicalLaboratory.getName());

        // Test with null object
        assertThrows(NullPointerException.class, () -> CReflection.invokeSetMethod(null, "name", "lab", String.class));

        // Test with null property value and property class
        assertThrows(IllegalArgumentException.class, () -> CReflection.invokeSetMethod(medicalLaboratory, "name", null, null));

        // Test setting a null value
        CReflection.invokeSetMethod(medicalLaboratory, "name", null, String.class);
        assertNull(medicalLaboratory.getName());
    }

    /**
     * Tests {@link CReflection#methodToProperty(String)}.
     */
    @Test
    void testMethodToProperty() {
        assertEquals("toBeOrNotToBe", CReflection.methodToProperty("getToBeOrNotToBe"));

        // Test with illegal method
        assertThrows(IllegalArgumentException.class, () -> CReflection.methodToProperty("abc"));
    }

    /**
     * Tests {@link CReflection#clearAllMembers(Object)}.
     */
    @Test
    void testClearAllMembers() {
        final MedicalLaboratory medicalLaboratory = new MedicalLaboratory();
        medicalLaboratory.setName("name");
        CReflection.clearAllMembers(medicalLaboratory);
        assertNull(medicalLaboratory.getName());

        // Assert that member from super class has been cleared
        assertFalse(medicalLaboratory.getOpen());
    }

    /**
     * Tests {@link CReflection#getCallingMethod(String)}.
     */
    @Test
    void testGetCallingMethod() {
        // Test non-existing method (that can't have been called)
        assertNull(CReflection.getCallingMethod("nonExistingMethod"));

        // Test existing method
        assertEquals("testGetCallingMethod", nestedMethod(), "Getting caller of the nested method (this method)");
    }

    /**
     * Just a method called from {@link #testGetCallingMethod()} so that the calling method is one of our own
     * (instead of one from JUnit).
     * @return The name of the method that has called this method.
     */
    private String nestedMethod() {
        return CReflection.getCallingMethod(CReflectionTest.class.getName() + ".nestedMethod");
    }


}

@SuppressWarnings("ALL")
class Laboratory {
    @ReflectionAnnotation(value = "Kind")
    public static final String KIND = "3";

    private final String level = "High";

    public static void cleanTestTubes() {
        assert true;
    }

    public static boolean isSafe() {
        return true;
    }

    @ReflectionAnnotation(value = "nice")
    public static void getControl() {
        assert true;
    }

    public void isControl() {
        assert true;
    }

    public void clean() {
        assert true;
    }

    public int getTestTubes() {
        return 3;
    }

    public String getName() {
        return "Area 51";
    }

    // Not a getter since it expects a parameter
    public String getDescription(int param) {
        return String.valueOf(param);
    }


    // Not a getters since it is a private method
    private String getDescription() {
        return "Area 31";
    }

    // Not a setters since it has no parameter
    public void setNothing() {
        assert true;
    }

    // Not a setters since it returns a value
    public String setDescription(final String description) {
        return description;
    }

    // Not a setters since it has too many parameters
    @ReflectionAnnotation(value = "nice")
    public String setDescription(final String description, final int count) {
        return description + count;
    }

    // Not a setters since it is private
    private void setDescription(final int code) {
        setDescription(String.valueOf(code));
    }

    public void setCode(final String code) {
        assert code != null;
    }

    private boolean open;
    public boolean getOpen() { return this.open;}
    public void setOpen(final boolean open) { this.open = open; }
}

@SuppressWarnings("ALL")
class MedicalLaboratory extends Laboratory {
    private String name = "Medical Lab-1";

    public static void preventHazard(final String danger) {
        assert danger.length() > 1;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    private void doIt() {
        assert true;
    }

}

/**
 * To test evaluation of property expressions.
 */
@SuppressWarnings("ALL")
class Left {

    private final String simpleStringProperty = "simpleString";

    private final int primitiveIntProperty = 17;

    private final Long longProperty = 19L;

    /**
     * A property to the right of the left property.
     */
    private final Right right = new Right();

    /**
     * An Integer array.
     */
    private final Integer [] integerArray = {0, 1, 2};

    /**
     * A two-dimensional Integer array.
     */
    private final Integer [][] twoDimensionalIntegerArray = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };

    private final List<Integer> integerList = List.of(1, 2, 3, 4, 5);
    public String getSimpleStringProperty() {
        return this.simpleStringProperty;
    }

    public int getPrimitiveIntProperty() {
        return this.primitiveIntProperty;
    }

    public Long getLongProperty() {
        return this.longProperty;
    }

    public Long getLongPropertyWithIndex(int index) {
        return index * 2L;
    }

    /**
     * Gets the right property.
     * @return The right property.
     */
    public Right getRight() {
        return right;
    }

    /**
     * Gets the Integer array.
     * @return The Integer array.
     */
    public Integer [] getIntegerArray() {
        return integerArray;
    }

    /**
     * Gets the multidimensional Integer array.
     * @return The multidimensional Integer array.
     */
    public Integer [][] getTwoDimensionalIntegerArray() {
        return twoDimensionalIntegerArray;
    }

    /**
     * Gets the list of integers.
     * @return The list of integers.
     */
    public List<Integer> getIntegerList() {
        return integerList;
    }

}

/**
 * To test evaluation of property expressions.
 */
@SuppressWarnings("ALL")
class Right {

    /**
     * The String property.
     */
    private final String stringProperty = "right";

    /**
     * Simple list.
     */
    private final List<Integer> simpleList = Arrays.asList(0, 1, 2);

    /**
     * Two-dimensional list.
     */
    private final List<List<Integer>> twoDimensionalList = new ArrayList<>();

    /**
     * Initializes the two-dimensional list.
     */
    Right() {
        twoDimensionalList.add(Arrays.asList(0, 1, 2));
        twoDimensionalList.add(Arrays.asList(0, 1, 2));
    }

    /**
     * Gets the stringProperty.
     * @return The stringProperty.
     */
    public String getStringProperty() {
        return stringProperty;
    }

    /**
     * Gets the simple list.
     * @return The simple list.
     */
    public List<Integer> getSimpleList() {
        return simpleList;
    }

    /**
     * Gets the two-dimensional list.
     * @return The two-dimensional list.
     */
    public List<List<Integer>> getTwoDimensionalList() {
        return twoDimensionalList;
    }
}

@Target({ ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@interface ReflectionAnnotation {

    /**
     * Value.
     * @return The value.
     */
    String value();
}
