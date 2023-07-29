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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Utility class to aid in localizing enumerations.
 * This class is quite opinionated.
 * It expects a properties file (with the same name as the enumeration).
 * In this properties file you must have a key equal to the simple name of the enumeration.
 * The value should describe the enumeration itself.
 * For each enumerated constant a key/value should be present, where the key matches the name of the enumerated constant.
 * Take a look at {@link com.schotanus.science.physics.astronomy.Planet} for example.
 * It is accompanied by com/schotanus/science/physics/astronomy/Planet.properties with this content:
 * <pre><code>
 * Planet=Planets in our solar system
 *
 * MERCURY=Mercury
 * VENUS=Venus
 * EARTH=Earth
 * MARS=Mars
 * JUPITER=Jupiter
 * SATURN=Saturn
 * URANUS=Uranus
 * NEPTUNE=Neptune
 * </code></pre>
 */
public final class CEnumeration {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CEnumeration.class);

    /**
     * Private constructor prevents creation of an instance outside this class.
     */
    private CEnumeration() {
    }

    /**
     * Gets the description of the enumeration as a whole.
     * <br>This method uses the simple class name of the supplied enumeration, as the key to the resource bundle file to get
     * the description.
     * @param enumeration The enumeration to get the description for.
     * @param locale Locale used to localize the description.
     *  <br>When null is supplied the default locale is used.
     * @return Localized description of the supplied enumeration.
     *  When no description is found the simple name of the enumeration is returned.
     * @throws MissingResourceException When the resource bundle could not be located.
     * @throws NullPointerException When the supplied enumeration is null.
     */
    public static String getDescription(final Class<? extends Enum<?>> enumeration, final Locale locale) {
        Objects.requireNonNull(enumeration);

        String result;
        final ResourceBundle resourceBundle = CEnumeration.getResourceBundle(enumeration, locale);
        try {
            result = resourceBundle.getString(enumeration.getSimpleName());
        } catch (final MissingResourceException exception) {
            logger.atWarn().log("Resource bundle: {}, is missing key: {}", enumeration.getName(), enumeration.getSimpleName());
            result = enumeration.getSimpleName();
        }

        return result;
    }

    /**
     * Gets the description of the supplied enumerated constant.
     * <br>This method uses the name of the enumerated constant as a key to the resource bundle file to get the description.
     * @param enumeratedConstant The enumerated constant to get the description for.
     * @param locale Locale used to localize the description.
     *  <br>When null is supplied the default locale is used.
     * @return Localized description of the supplied enumerated constant.
     * @throws MissingResourceException When either the resource bundle or the key (name of enumerated constant) is not found.
     * @throws NullPointerException When the supplied enumerated constant is null.
     */
    public static String getDescription(final Enum<?> enumeratedConstant, final Locale locale) {
        return getDescription(enumeratedConstant, enumeratedConstant.name(), locale);
    }

    /**
     * Gets the description of the supplied enumerated constant using the supplied key as the key to the resource bundle..
     * @param enumeratedConstant The enumerated constant to get the description for.
     * @param key The key to the resource bundle.
     * @param locale Locale used to localize the description.
     *  <br>When null is supplied the default locale is used.
     * @return Localized description of the supplied enumerated constant.
     * @throws MissingResourceException When either the resource bundle or the key (name of enumerated constant) is not found.
     * @throws NullPointerException When either the supplied enumerated constant or key is null.
     */
    public static String getDescription(final Enum<?> enumeratedConstant, final String key, final Locale locale) {
        final Class<? extends Enum<?>> enumeratedClass = Objects.requireNonNull(enumeratedConstant).getDeclaringClass();

        final ResourceBundle resourceBundle =
                CEnumeration.getResourceBundle(enumeratedClass, CLocale.getLocaleOrDefault(locale));
        try {
            return resourceBundle.getString(key);
        } catch (final MissingResourceException exception) {
            logger.atWarn().log("Resource bundle: {}, is missing key: {}", enumeratedClass.getName(), enumeratedConstant.name());
            throw exception;
        }
    }

    /**
     * Gets {@link CodeDescription CodeDescriptions} for all the enumerated  constants of the supplied enumeration, using the
     * names of the enumerated constants as the code for the different {@link CodeDescription CodeDescriptions}.
     * @param <T> The type of the enumeration.
     * @param enumeration The enumeration to get the CodeDescription objects for.
     * @param locale Locale used to localize the descriptions.
     *  <br>When null is supplied the default locale is used.
     * @return {@link CodeDescription CodeDescriptions} for the enumerated constants of the supplied enumeration.
     * @throws NullPointerException When the supplied enumeration is null.
     */
    public static <T extends Enum<T>> List<CodeDescription<String>> getCodeDescriptions(
            final Class<T> enumeration, final Locale locale) {

        final T[] enumeratedConstants = enumeration.getEnumConstants();
        final List<CodeDescription<String>> codeDescriptions = new ArrayList<>(enumeratedConstants.length);
        for (final T enumeratedConstant : enumeratedConstants) {
            codeDescriptions.add(CEnumeration.getCodeDescription(enumeratedConstant, locale));
        }

        return codeDescriptions;
    }

    /**
     * Gets the CodeDescription of the supplied enumeratedConstant, using the name of the supplied enumerated constant as the
     * code for the returned CodeDescription.
     * @param <T> The type of the enumerated constant.
     * @param enumeratedConstant The enumerated constant to get the CodeDescription for.
     * @param locale Locale used to localize the description.
     *  <br>When null is supplied the default locale is used.
     * @return The localized CodedDescription of the supplied enumeratedConstant.
     *  <br>The code in the returned object is determined by the result of enumeratedConstant.name().
     *  @throws NullPointerException When the supplied enumeratedConstant is null.
     */
    public static <T extends Enum<T>> CodeDescription<String> getCodeDescription(
            final T enumeratedConstant, final Locale locale) {

        return new CodeDescription<>(enumeratedConstant.name(), CEnumeration.getDescription(enumeratedConstant, locale));
    }

    /**
     * Gets the CodeDescription of the supplied enumeratedConstant, using the name of the supplied enumerated constant as the
     * code for the returned CodeDescription.
     * @param <T> The type of the enumerated constant.
     * @param enumeratedConstant The enumerated constant to get the CodeDescription for.
     * @param code The code to get the localized description.
     * @param locale Locale used to localize the description.
     *  <br>When null is supplied the default locale is used.
     * @return The localized CodedDescription of the supplied enumeratedConstant.
     *  @throws NullPointerException When the supplied enumeratedConstant is null.
     */
    public static <T extends Enum<T>> CodeDescription<String> getCodeDescription(
            final T enumeratedConstant, final String code, final Locale locale) {

        return new CodeDescription<>(enumeratedConstant.name(), CEnumeration.getDescription(enumeratedConstant, code, locale));
    }

    /**
     * Gets the resource bundle associated with an enumeration.
     * @param enumeration The class of the enumeration.
     * @param locale The locale used for localization.
     *  <br>When null is supplied the default locale is used.
     * @return The resource bundle associated with an enumeration.
     * @throws MissingResourceException When no resource bundle could be found.
     */
    public static ResourceBundle getResourceBundle(final Class<? extends Enum<?>> enumeration, final Locale locale) {
        try {
            return ResourceBundle.getBundle(
                    enumeration.getName(), CLocale.getLocaleOrDefault(locale), enumeration.getClassLoader());
        } catch (final MissingResourceException exception) {
            logger.atError().log("Enumeration: {} has no properties file!", enumeration.getName());
            throw exception;
        }
    }
}
