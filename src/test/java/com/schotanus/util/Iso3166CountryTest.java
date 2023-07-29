/* Copyright (C) 2009 Kees Schotanus
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

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link Iso3166Country}.
 */
final class Iso3166CountryTest {

    /**
     * The Dutch locale.
     */
    private static final Locale dutchLocale = new Locale("nl", "NL");

    @Test
    void testConstructorUsingDates() {
        // Test getting country code of the USA
        assertEquals("US", Iso3166Country.UNITED_STATES_OF_AMERICA.getAlpha2Code());

        // Test getting country code of The Netherlands
        assertEquals("NL", Iso3166Country.NETHERLANDS.getAlpha2Code());
    }

    @Test
    void testGetAlpha2Code() {
        // Test getting country code of the USA
        assertEquals("US", Iso3166Country.UNITED_STATES_OF_AMERICA.getAlpha2Code());

        // Test getting country code of The Netherlands
        assertEquals("NL", Iso3166Country.NETHERLANDS.getAlpha2Code());
    }

    @Test
    void testIsActive() {
        // Test getting an active country
        assertTrue(Iso3166Country.NETHERLANDS.isActive());

        // Test getting an inactive country
        assertFalse(Iso3166Country.NETHERLANDS_ANTILLES.isActive());

        // The Netherlands Antilles was active in 2009
        assertTrue(Iso3166Country.NETHERLANDS_ANTILLES.isActive(
                LocalDate.of(DateConstants.YEAR_2009, Month.JANUARY, DateConstants.DAY_1)));

        // The German Democratic Republic was active in 1988
        assertTrue(Iso3166Country.GERMANY_DEMOCRATIC_REPUBLIC.isActive(
                LocalDate.of(DateConstants.YEAR_1988, Month.JANUARY, DateConstants.DAY_1)));
    }

    @Test
    void testGetActiveFrom() {
        // Test getting a country with an active from value
        assertEquals(LocalDate.of(DateConstants.YEAR_1974, Month.DECEMBER, DateConstants.DAY_15),
                Iso3166Country.GERMANY_DEMOCRATIC_REPUBLIC.getActiveFrom());
    }

    @Test
    void testGetActiveUntil() {
        // Test getting a country with an active from value
        assertEquals(LocalDate.of(DateConstants.YEAR_1990, Month.DECEMBER, DateConstants.DAY_4),
                Iso3166Country.GERMANY_DEMOCRATIC_REPUBLIC.getActiveUntil());
    }

    @Test
    void testGetCodeDescriptions() {
        final List<CodeDescription<String>> codeDescriptions = Iso3166Country.getCodeDescriptions(dutchLocale, null);
        // Get the code/descriptions and test if the first and last country are the correct ones
        assertEquals("Afghanistan", codeDescriptions.get(0).description());
        assertEquals("Zwitserland", codeDescriptions.get(codeDescriptions.size() - 1).description());
    }

    @Test
    void testIsValidAlpha2Code() {
        // Test getting some valid alpha2 codes
        assertTrue(Iso3166Country.isValidAlpha2Code("NL"));
        assertTrue(Iso3166Country.isValidAlpha2Code("US"));

        // Test getting an invalid alpha2 code
        assertFalse(Iso3166Country.isValidAlpha2Code("QQ"));
    }

    @Test
    void testGetIso3166Country() {
        // Test getting the US by its alpha2 code
        assertEquals(Iso3166Country.UNITED_STATES_OF_AMERICA, Iso3166Country.getIso3166Country("US"));

        // Test getting a non-existing country code
        assertThrows(IllegalArgumentException.class, () -> Iso3166Country.getIso3166Country("QQ"));
    }

}
