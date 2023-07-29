/*
 * Copyright (C) 2008 Kees Schotanus
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link CLocale}.
 */
final class CLocaleTest {

    @Test
    void testGetLocaleOrDefault() {
        // Test getting the default locale
        assertEquals(Locale.getDefault(), CLocale.getLocaleOrDefault(null));

        // Test getting the supplied locale
        assertEquals(Locale.FRANCE, CLocale.getLocaleOrDefault(Locale.FRANCE));
    }

    @Test
    void testValueOf() {
        // Test converting some predefined locales
        assertEquals(Locale.FRANCE, CLocale.valueOf(Locale.FRANCE.toString()));
        assertEquals(Locale.GERMANY, CLocale.valueOf(Locale.GERMANY.toString()));

        // Test missing country (which is allowed)
        assertEquals(new Locale("nl", "", ""), CLocale.valueOf("nl"));

        // Test missing country but with a present variant
        assertEquals(new Locale("nl", "", "linux"), CLocale.valueOf("nl__linux"));

        // Test passing in null and empty languages
        Assertions.assertThrows(NullPointerException.class, () -> CLocale.valueOf(null));

        // Test passing in empty languages
        Assertions.assertThrows(IllegalArgumentException.class, () -> CLocale.valueOf(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CLocale.valueOf("  "));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CLocale.valueOf("  _NL"));

        // Test missing language
        Assertions.assertThrows(IllegalArgumentException.class, () -> CLocale.valueOf("_US_"));

        // Test passing to many arguments
        Assertions.assertThrows(IllegalArgumentException.class, () -> CLocale.valueOf("nl_NL_var_extra"));

        // Test using invalid countries
        Assertions.assertThrows(IllegalArgumentException.class, () -> CLocale.valueOf("nl_QQ"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CLocale.valueOf("nl_Q"));
    }

    @Test
    void testIsValidIsoCountry() {
        // Test valid country
        assertTrue(CLocale.isValidIsoCountry("NL"));

        // Test invalid (null) country
        assertFalse(CLocale.isValidIsoCountry(null));
    }

    @Test
    void testIsInstalledLocale() {
        // Test valid country
        assertTrue(CLocale.isInstalledLocale(Locale.US));

        // Test invalid country
        assertFalse(CLocale.isInstalledLocale(new Locale("qq", "ZZ")));
    }
}
