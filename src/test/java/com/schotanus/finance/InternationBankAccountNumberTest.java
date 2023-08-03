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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link InternationalBankAccountNumber}.
 */
class InternationalBankAccountNumberTest {

    /**
     * Default IBAN for testing.
     */
    private static final InternationalBankAccountNumber iban = new InternationalBankAccountNumber("BE62510007547061");

    /**
     * Tests {@link InternationalBankAccountNumber#InternationalBankAccountNumber(String)}
     */
    @Test
    void testConstructorWithSingleString() {
        assertDoesNotThrow(() -> new InternationalBankAccountNumber("BE62510007547061"));
    }

    /**
     * Tests {@link InternationalBankAccountNumber#InternationalBankAccountNumber(String, String)}
     */
    @Test
    void testConstructorWithDoubleString() {
        // Test with two valid bban's
        assertDoesNotThrow(() -> {
            new InternationalBankAccountNumber("BE", "510007547061");
            new InternationalBankAccountNumber("BE", "11873434");
        });

    }

    @Test
    final void testGetCountry() {
        assertEquals(Iso3166Country.BELGIUM.getAlpha2Code(), iban.getCountry());
    }

    @Test
    void testGetCheckDigits() {
        assertEquals( "62", iban.getCheckDigits(), "Check digits must be 2 digits after country code");
    }

    @Test
    void testGetBasicBankAccountNumber() {
        assertEquals("510007547061", iban.getBasicBankAccountNumber());
    }

    @Test
    void testIsValidIban() {
        assertTrue(InternationalBankAccountNumber.isValidIban("BE62510007547061"), "iban is valid");
        assertFalse(InternationalBankAccountNumber.isValidIban("!!62510007547061"), "iban has invalid country");
        assertFalse(InternationalBankAccountNumber.isValidIban("BE10510007547061"), "iban has invalid check digits");
        assertFalse(InternationalBankAccountNumber.isValidIban("BE62A"), "iban has invalid bank account");
        assertFalse(InternationalBankAccountNumber.isValidIban("NOT VALID"), "iban is completely invalid");
        assertFalse(InternationalBankAccountNumber.isValidIban(" "), "iban is empty");
        assertFalse(InternationalBankAccountNumber.isValidIban(null), "iban is null");
    }

    @Test
    void testCheckIban() {
        assertDoesNotThrow(() -> InternationalBankAccountNumber.checkIban("BE62510007547061"), "Correct IBAN");
        assertDoesNotThrow(() -> InternationalBankAccountNumber.checkIban("BE0811873434"),
                "Correct IBAN with 0 check digit at beginning");

        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkIban("BE6251000"), "Incorrect IBAN");
        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkIban("be62510007547061"), "Illegal lower case");
        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkIban("QQ62510007547061"), "Illegal country");
        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkIban("BE#2510007547061"), "Not alphanumeric");
        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkIban("BEAB510007547061"), "Illegal checkdigits");
        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkIban("BE6251000439875934875945794878757578"), "IBAN too long");

        // Check with illegal check digits 00, 01 and 99
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkIban("BE00510007547061"));
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkIban("BE01510007547061"));
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkIban("BE99510007547061"));
    }

    @Test
    void testCheckCountry() {
        assertDoesNotThrow(() -> InternationalBankAccountNumber.checkCountry("NL"));

        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkCountry("NED"), "Too long");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkCountry("N"), "Too short");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkCountry("##"), "Not alphabetic");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkCountry(null), "Empty");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkCountry("  "), "Empty");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkCountry("nl"), "Lowercase");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkCountry("QQ"), "Unknown country");
    }

    @Test
    void testCheckBasicBankAccountNumber() {
        assertDoesNotThrow(() -> InternationalBankAccountNumber.checkBasicBankAccountNumber("510007547061"), "Correct BBAN");

        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkBasicBankAccountNumber(null));
        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkBasicBankAccountNumber("   "));
        assertThrows(IllegalArgumentException.class,
                () -> InternationalBankAccountNumber.checkBasicBankAccountNumber("01234567890123456789012345678912345"), "Too long");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkBasicBankAccountNumber("123!@#"),
                "Incorrect bank account should fail");
        assertThrows(IllegalArgumentException.class, () -> InternationalBankAccountNumber.checkBasicBankAccountNumber("12345678abcdef"), "Not upper case");
    }

    @Test
    void testToPaperFormat() {
        assertEquals("BE62 5100 0754 7061", iban.toPaperFormat(), "Paper format uses groups of 4 chars separated by a space");
    }

    @Test
    void testToString() {
        assertEquals("BE62510007547061", iban.toString(), "IBAN String representation as-is");
    }

}
