/*
 * Copyright (C) 2009 Kees Schotanus
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

package com.schotanus.finance;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link ModuloCheck}.
 */
final class ModuloCheckTest {

    @Test
    void testIsModulo10() {
        assertFalse(ModuloCheck.isModulo10(null));
        assertFalse(ModuloCheck.isModulo10(""));
        assertFalse(ModuloCheck.isModulo10("  "));
        assertTrue(ModuloCheck.isModulo10("0"));
        assertTrue(ModuloCheck.isModulo10("8763"));
        assertFalse(ModuloCheck.isModulo10("7863"));
        assertFalse(ModuloCheck.isModulo10("7836"));
        assertFalse(ModuloCheck.isModulo10("3768"));
        assertTrue(ModuloCheck.isModulo10("00008763"));

        assertTrue(ModuloCheck.isModulo10("446667651"));
    }

    /**
     * Tests {@link ModuloCheck#isModulo11(String)}
     */
    @Test
    void testIsModulo11WithStrings() {
        assertTrue(ModuloCheck.isModulo11("123456789"));

        // Test with non-numeric value
        assertFalse(ModuloCheck.isModulo11("NoDigits"));
    }

    /**
     * Tests {@link ModuloCheck#isModulo11(long)}
     */
    @Test
    void testIsModulo11WithNumbers() {
        assertTrue(ModuloCheck.isModulo11(736160221));

        assertTrue(ModuloCheck.isModulo11(51));

        assertFalse(ModuloCheck.isModulo11(736160221 + 1));
    }

    @Test
    void testIsBurgerServiceNummer() {
        assertTrue(ModuloCheck.isBurgerServiceNummer("123456782"));
        assertTrue(ModuloCheck.isBurgerServiceNummer("111222333"));
        assertFalse(ModuloCheck.isBurgerServiceNummer("61"));
        assertTrue(ModuloCheck.isBurgerServiceNummer("000000061"));
        assertTrue(ModuloCheck.isBurgerServiceNummer("00000061"));
        assertTrue(ModuloCheck.isBurgerServiceNummer("048261063"));
        assertTrue(ModuloCheck.isBurgerServiceNummer("12344321"));

        // Test with non-numeric value of legal length
        assertFalse(ModuloCheck.isBurgerServiceNummer("No Number"));
    }

    @Test
    void testIsModulo97() {
        assertTrue(ModuloCheck.isModulo97("510007547061BE62"));
        assertFalse(ModuloCheck.isModulo97("510007547061BE61"));

        // Test with non-alphanumeric value
        assertThrows(IllegalArgumentException.class, () -> ModuloCheck.isModulo97("NotValid!"));
    }
}
