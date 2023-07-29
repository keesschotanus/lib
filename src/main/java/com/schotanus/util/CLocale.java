/*
 * Copyright (C) 2003 Kees Schotanus
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

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;


/**
 * Collection of Locale related methods.
 */
public final class CLocale {

    /**
     * The maximum number of elements in a locale we can parse.
     * The elements are language, country and variant.
     */
    private static final int MAX_ELEMENTS_IN_LOCALE = 3;

    /**
     * Private constructor prevents creation of an instance outside this class.
     */
    private CLocale() {
    }

    /**
     * When the supplied locale is null, the default locale is used, otherwise the supplied locale is used.
     * @param locale The locale to use.
     * @return The supplied locale when non-null, otherwise the default locale.
     */
    public static Locale getLocaleOrDefault(final Locale locale) {
        return locale == null ? Locale.getDefault() : locale;
    }

    /**
     * Creates a Locale from the supplied String locale.
     * Note: This method does not support script and language extension.
     * @param locale String representation of a Locale.
     *  It must be in the format: language_country_variant, where country and variant are optional (e.g. "en_US").
     *  <br>See: {@link Locale#toString()}.
     * @return The Locale corresponding to the supplied locale.
     * @throws IllegalArgumentException When no locale can be created from the supplied locale.
     * @throws NullPointerException When the supplied locale is null.
     */
    public static Locale valueOf(final String locale) {
        Objects.requireNonNull(locale);

        final String[] localeArray = locale.split("_");
        if (localeArray.length > MAX_ELEMENTS_IN_LOCALE) {
            throw new IllegalArgumentException(String.format("Illegal locale: %s", locale));
        }

        final String language = localeArray[0].toLowerCase(Locale.getDefault());
        if (language.trim().length() != 2) {
            throw new IllegalArgumentException(String.format("Illegal language: %s in locale: %s", language, locale));
        }

        String country = "";
        if (localeArray.length > 1) {
            country = localeArray[1].trim().toUpperCase(Locale.getDefault());

            // Country may be absent, but when present it must be a valid iso country
            if (country.length() != 0 && (country.length() != 2 || !isValidIsoCountry(country))) {
                throw new IllegalArgumentException(String.format("Illegal country: %s, in locale: %s", country, locale));
            }
        }

        final String variant = localeArray.length == MAX_ELEMENTS_IN_LOCALE ? localeArray[2].trim() : "";

        return new Locale(language, country, variant);
    }

    /**
     * Determines whether the supplied alpha2Code is a valid 2-letter ISO-3166 country or not.
     * @param alpha2Code The 2-letter code to check.
     * @return True when the supplied alpha2Code is a valid 2-letter ISO-3166 country, false when it is not.
     * @see Locale#getISOCountries()
     */
    public static boolean isValidIsoCountry(final String alpha2Code) {
        return Arrays.asList(Locale.getISOCountries()).contains(alpha2Code);
    }

    /**
     * Determines whether the supplied locale is one of the installed locales.
     * <br>Note: Even though for example, the Dutch locale ("nl") is installed, that doesn't mean that all localized
     * information, like the names of all the countries for example, is present.
     * @param locale The locale to check.
     * @return True when the supplied locale is an installed locale, false when the supplied locale is not installed.
     * @see Locale#getAvailableLocales()
     */
    public static boolean isInstalledLocale(final Locale locale) {
        return Arrays.asList(Locale.getAvailableLocales()).contains(locale);
    }

}
