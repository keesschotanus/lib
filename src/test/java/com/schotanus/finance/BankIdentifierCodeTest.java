/*
 * Copyright (C) 2010 Kees Schotanus
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

import com.schotanus.util.Iso3166Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link BankIdentifierCode}.
 */
class BankIdentifierCodeTest {

    /**
     * Default short BIC for testing.
     */
    private static final BankIdentifierCode shortBic = new BankIdentifierCode("BANKNLVI");

    /**
     * Default long BIC for testing.
     */
    private static final BankIdentifierCode longBic = new BankIdentifierCode("BANKNLVIXXX");

    @Test
    final void testConstructor() {
        // Test with a valid BIC.
        assertDoesNotThrow(() -> new BankIdentifierCode("INGBNL2A"));
    }

    @Test
    final void testIsValidBankIdentifierCode() {
        // Test with valid BIC.
        assertTrue(BankIdentifierCode.isValidBankIdentifierCode("INGBNL2A"));
        // Test with invalid BIC.
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("INVALIDBIC"));
    }

    @Test
    final void testCheckBankIdentifierCode() {
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode(null), "Null input is invalid");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode(""), "Empty input is invalid");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("A"), "Too short input is invalid");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("AAAAAAAAAAAA"), "Too long input is invalid");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("TIENNL22XX"), "Not 8 or 11 positions is invalid");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode(shortBic.toString().toLowerCase()), "Lowercase is invalid");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("########"), "Non alphanumeric is invalid");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("1234NLXX"), "Bank code must consist of letters");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("BANK12XX"), "Country code must consist of letters");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("BANKQQXX"), "Country code does not exist");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("BANKNL##"), "Location code must be alphanumeric");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("BANKNLXX###"), "Branch code must be alphanumeric");

        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("BANKNL20"), "Disconnected BICs are not allowed");
        assertFalse(BankIdentifierCode.isValidBankIdentifierCode("BANKNL21"), "Test BICs are not allowed");
    }

    @Test
    final void testGetBankCode() {
        assertEquals("BANK", shortBic.getBankCode());
        assertEquals("BANK", longBic.getBankCode());
    }

    @Test
    final void testGetCountryCode() {
        assertEquals(Iso3166Country.NETHERLANDS.getAlpha2Code(), shortBic.getCountryCode());
        assertEquals(Iso3166Country.NETHERLANDS.getAlpha2Code(), longBic.getCountryCode());
    }

    @Test
    final void testGetLocationCode() {
        assertEquals("VI", shortBic.getLocationCode());
        assertEquals("VI", longBic.getLocationCode());
    }

    @Test
    final void testGetBranchCode() {
        // Test with short bic that does not have a branch code
        assertEquals("", shortBic.getBranchCode());

        // Test with long bic that does have a branch code
        assertEquals("XXX", longBic.getBranchCode());
    }

    @Test
    final void testToString() {
        assertEquals("BANKNLVI", shortBic.toString());
        assertEquals("BANKNLVIXXX", longBic.toString());
    }

}
