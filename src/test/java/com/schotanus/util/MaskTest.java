/*
 * Copyright (C) 2011 LeanApps
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Unit tests for class {@link Mask}.
 */
final class MaskTest {

    /**
     * Tests {@link Mask#fromMaskedValue(String, String)} with normal input.
     */
    @Test
    void testFromMaskedValueWithNormalValues() {
        assertEquals( "a", Mask.fromMaskedValue("[A]", "[a]"), "Mask=[A],Input=[a] => a");
        assertEquals("123456", Mask.fromMaskedValue("##-##-##", "12-34-56"), "Mask=##-##-##,Input=12-34-56 => 123456");

        // Passing value without masks should work.
        assertEquals("123456", Mask.fromMaskedValue("##-##-##", "123456"), "Mask=##-##-##,Input=123456 => 123456");

        // Use the "?" mask character that should accept any vale, even a mask character.
        assertEquals("#", Mask.fromUnmaskedValue("?", "#"), "Mask=?,Input=# => #");
    }

    /**
     * Tests {@link Mask#fromMaskedValue(String, String)} with conversions.
     */
    @Test
    void testFromMaskedValueWithConversions() {
        assertEquals("AbCabC", Mask.fromMaskedValue("[(ALUALU)]", "[(AbcaBC)]"), "Mask=[(ALUALU)],Input=[(AbcaBC)] => AbCabC");
    }

    /**
     * Tests {@link Mask#fromMaskedValue(String, String)} with illegal character.
     */
    @Test
    void testFromMaskedValueWithIllegalCharacter() {
        assertThrows(IllegalArgumentException.class, () -> Mask.fromMaskedValue("#", "a"));
        assertThrows(IllegalArgumentException.class, () -> Mask.fromMaskedValue("N", "."), "A point is not alphanumeric");
    }

    /**
     * Tests {@link Mask#fromMaskedValue(String, String)} with empty input.<br>
     * When the input is empty, the mask should be returned, provided that the mask does not contain one of the special mask
     * characters.
     * In this latter case, the input would be too short for the mask.
     */
    @Test
    void testFromMaskedValueWithEmptyValue() {
        assertEquals("", Mask.fromMaskedValue("[]", ""), "Empty input should result in empty string");
    }

    /**
     * Tests {@link Mask#fromMaskedValue(String, String)} with short input.
     */
    @Test
    void testFromMaskedValueWithShortValue() {
        assertThrows(IllegalArgumentException.class, () -> Mask.fromMaskedValue("(AAA)", "(bb)"));
    }

    /**
     * Tests {@link Mask#fromMaskedValue(String, String)} with long input.
     */
    @Test
    void testFromMaskedValueWithLongValue() {
        assertThrows(IllegalArgumentException.class, () -> Mask.fromMaskedValue("(AAA)", "(abcd)"));
    }

    /**
     * Tests {@link Mask#fromUnmaskedValue(String, String)} with normal input.
     */
    @Test
    void testFromUnmaskedValueWithNormalValues() {
        assertEquals("[a]", Mask.fromUnmaskedValue("[A]", "a"), "Mask=[A],Input=a => [a]");
        assertEquals("12-34-56", Mask.fromUnmaskedValue("##-##-##", "123456"),
                "Mask=##-##-##,Input=123456 => 12-34-56");
        assertEquals("AB-CD-EF", Mask.fromUnmaskedValue("UU-UU-UU", "abcdef"),
                "Mask=UU-UU-UU,Input=abcdef => AB-CD-EF");

        // Passing value with masks should work.
        assertEquals( "11-22-33", Mask.fromUnmaskedValue("##-##-##", "11-22-33"), "Mask=##-##-##,Input=11-22-33 => 11-22-33");
    }

    /**
     * Tests {@link Mask#fromUnmaskedValue(String, String)} with conversions.
     */
    @Test
    void testFromUnmaskedValueWithConversions() {
        assertEquals("[(AbCabC)]", Mask.fromUnmaskedValue("[(ALUALU)]", "AbcaBC"),
                "Mask=[(ALUALU)],Input=[(AbcaBC)] => AbCabC");
    }

    /**
     * Tests {@link Mask#fromUnmaskedValue(String, String)} with illegal character in value.
     */
    @Test
    void testFromUnmaskedValueWithIllegalCharacterInValue() {
        assertThrows(IllegalArgumentException.class, () -> Mask.fromUnmaskedValue("#", "a"));
    }

    /**
     * Tests {@link Mask#fromUnmaskedValue(String, String)} with illegal character in mask.
     */
    @Test
    void testFromUnmaskedValueWithIllegalCharacterInMask() {
        assertThrows(IllegalArgumentException.class, () -> Mask.fromUnmaskedValue("%", "a"));
    }

    /**
     * Tests {@link Mask#fromUnmaskedValue(String, String)} with empty input.
     * <br>When the input is empty, the mask should be returned, provided that
     * the mask does not contain one of the special mask characters. In this
     * latter case, the input would be too short for the mask.
     */
    @Test
    void testFromUnmaskedValueWithEmptyValue() {
        assertEquals("{}", Mask.fromUnmaskedValue("{}", ""),
                "Empty input should result in mask being returned");
    }
    /**
     * Tests {@link Mask#fromUnmaskedValue(String, String)} with short input.
     */
    @Test
    void testFromUnmaskedValueWithShortValue() {
        assertThrows(IllegalArgumentException.class, () -> Mask.fromUnmaskedValue("AAA", "b"));
    }

    /**
     * Tests {@link Mask#fromUnmaskedValue(String, String)} with long input.
     */
    @Test
    void testFromUnmaskedValueWithLongValue() {
        assertThrows(IllegalArgumentException.class, () -> Mask.fromUnmaskedValue("A", "abc"));
    }
}
