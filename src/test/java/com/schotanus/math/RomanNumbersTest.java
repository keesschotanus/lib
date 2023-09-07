/* $Id: RomanNumbersTest.java 3760 2012-05-17 11:50:27Z keess $
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

package com.schotanus.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link RomanNumbers}.
 */
final class RomanNumbersTest {

    /**
     * Tests {@link RomanNumbers#toRoman(int)}.
     */
    @Test
    void testToRoman() {
        assertEquals( "I", RomanNumbers.toRoman(1));
        assertEquals("II", RomanNumbers.toRoman(2));
        assertEquals("", RomanNumbers.toRoman(0)); // Romans did not know zero
        assertEquals("III", RomanNumbers.toRoman(3));
        assertEquals("IV", RomanNumbers.toRoman(4));
        assertEquals("V", RomanNumbers.toRoman(5));
        assertEquals("VI" , RomanNumbers.toRoman(6));
        assertEquals("VII", RomanNumbers.toRoman(7));
        assertEquals("VIII", RomanNumbers.toRoman(8));
        assertEquals("IX", RomanNumbers.toRoman(9));
        assertEquals("X", RomanNumbers.toRoman(10));
    }

    /**
     * Tests {@link RomanNumbers#toArabic(String)}.
     */
    @Test
    void testToArabic() {
        assertEquals(1, RomanNumbers.toArabic("I"));
        assertEquals(2, RomanNumbers.toArabic("II"));
        assertEquals(3, RomanNumbers.toArabic("III"));
        assertEquals(4, RomanNumbers.toArabic("IV"));
        assertEquals(5, RomanNumbers.toArabic("V"));
        assertEquals(10, RomanNumbers.toArabic("X"));
        assertEquals(50, RomanNumbers.toArabic("L"));
        assertEquals(100, RomanNumbers.toArabic("C"));
        assertEquals(500, RomanNumbers.toArabic("D"));
        assertEquals(1000, RomanNumbers.toArabic("M"));

        // Not actually a valid Roman number, but it shows how the number is parsed
        assertEquals(334, RomanNumbers.toArabic("IVXLCDM"));

        // Test non existing Roman numeral
        assertThrows(IllegalArgumentException.class, () -> RomanNumbers.toArabic("Not a Roman number"));
    }

    /**
     * Tests {@link RomanNumbers#isValid(String)}.
     */
    @Test
    void testIsValid() {
        assertTrue(RomanNumbers.isValid("V"));
        assertFalse(RomanNumbers.isValid("VD"));
    }

    /**
     * Tests {@link RomanNumbers#toArabic(String)} and {@link RomanNumbers#toRoman(int)}.<br>
     * Converts all arabic numbers from 0 to {@link RomanNumbers#LARGEST_ROMAN_NUMBER}, to a Roman number,
     * converts the Roman number to an Arabic number and checks for equality.
     */
    @Test
    void conversions() {
        for (int i = 0; i <= RomanNumbers.LARGEST_ROMAN_NUMBER; i++) {
            final String roman = RomanNumbers.toRoman(i);
            final int arabic = RomanNumbers.toArabic(roman);
            assertEquals(i, arabic, "Error: i=%d, roman=%s, arabic=%d".formatted(i, roman, arabic));
        }
    }

}
