/*
 * Copyright (C) 2002 Kees Schotanus
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.schotanus.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for {@link CResourceBundle}.
 */
class CResourceBundleTest {

    private static CResourceBundle bundle;

    @BeforeAll
    public static void setUp() {
        bundle = new CResourceBundle("com.schotanus.util.top");
        bundle.addResourceBundle("com.schotanus.util.middle");
        bundle.addResourceBundle("com.schotanus.util.bottom");
    }

    @Test()
    void testConstructorWithNonExistingBundle() {
        Assertions.assertThrows(MissingResourceException.class,
            () -> new CResourceBundle("com.schotanus.util.nonExisting"));
    }

    @Test
    void testGetLocalizedMessageWithBundleName() {
        // Test getting an existing message
        final String bundleName = CResourceBundleTest.class.getName();
        assertEquals("Bonjour Patrick, comment ça va?",
                CResourceBundle.getLocalizedMessage(Locale.FRANCE, bundleName,"hello", "Patrick"));

        // Test getting a non-existing message
        assertEquals("doesNotExist",
                CResourceBundle.getLocalizedMessage(Locale.FRANCE, bundleName,"doesNotExist" ));
    }

    @Test
    void testGetLocalizedMessageWithResourceBundle() {
        // Test getting an existing message
        final ResourceBundle resourceBundle =
                ResourceBundle.getBundle("com.schotanus.util.CResourceBundleTest", Locale.getDefault());
        assertEquals("Hello Patrick, how are you today?",
                CResourceBundle.getLocalizedMessage(resourceBundle,"hello", "Patrick"));

        // Test getting a non-existing message
        assertEquals("doesNotExist",
                CResourceBundle.getLocalizedMessage(resourceBundle,"doesNotExist" ));
    }

    @Test
    void testGetBoolean() {
        // Test getting a present boolean true value
        final Optional<Boolean> optionalTrueBoolean = bundle.getBoolean("booleanTrue");
        assertTrue(optionalTrueBoolean.isPresent());
        assertEquals(Boolean.TRUE, optionalTrueBoolean.orElseThrow());

        // Test getting a present boolean false value
        final Optional<Boolean> optionalFalseBoolean = bundle.getBoolean("booleanFalse");
        assertTrue(optionalFalseBoolean.isPresent());
        assertFalse(optionalFalseBoolean.orElseThrow());

        // Test getting a present boolean value that is incorrect
        final Optional<Boolean> optionalIncorrectBoolean = bundle.getBoolean("booleanIncorrect");
        assertTrue(optionalIncorrectBoolean.isPresent());
        assertFalse(optionalIncorrectBoolean.orElseThrow());

        // Test getting an absent boolean value
        final Optional<Boolean> optionalAbsentBoolean = bundle.getBoolean("booleanDoesNotExist");
        assertTrue(optionalAbsentBoolean.isEmpty());
    }

    @Test
    void testGetRequiredBoolean() {
        // Test getting a present boolean true value
        assertTrue(bundle.getRequiredBoolean("booleanTrue"));

        // Test getting a present boolean false value
        assertFalse(bundle.getRequiredBoolean("booleanFalse"));

        // Test getting a present, incorrect boolean value
        assertFalse(bundle.getRequiredBoolean("booleanIncorrect"));

        // Test getting an absent boolean value
        Assertions.assertThrows(MissingResourceException.class, () -> bundle.getRequiredBoolean("nonExistingBoolean"));
    }

    @Test
    void testGetInteger() {
        // Test getting a present correct integer value
        final Optional<Integer> optionalCorrectInteger = bundle.getInteger("integerCorrect");
        assertTrue(optionalCorrectInteger.isPresent());
        assertEquals(Integer.valueOf(123), optionalCorrectInteger.orElseThrow());

        // Test getting a present incorrect integer value
        Assertions.assertThrows(NumberFormatException.class, () -> bundle.getInteger("integerIncorrect"));

        // Test getting an absent integer value
        assertTrue(bundle.getInteger("integerDoesNotExist").isEmpty());
    }

    @Test
    void testGetRequiredInteger() {
        // Test getting a present integer value
        assertEquals(Integer.valueOf(123), bundle.getRequiredInteger("integerCorrect"));

        // Test getting a present incorrect integer value
        Assertions.assertThrows(NumberFormatException.class, () -> bundle.getRequiredInteger("integerIncorrect"));

        // Test getting an absent integer value
        Assertions.assertThrows(MissingResourceException.class, () -> bundle.getRequiredInteger("integerDoesNotExist"));
    }

    @Test
    void testGetLong() {
        // Test getting a present correct long value
        final Optional<Long> optionalCorrectLong = bundle.getLong("longCorrect");
        assertTrue(optionalCorrectLong.isPresent());
        assertEquals(Long.valueOf(123456789), optionalCorrectLong.orElseThrow());

        // Test getting a present incorrect long value
        Assertions.assertThrows(NumberFormatException.class, () -> bundle.getLong("longIncorrect"));

        // Test getting an absent long value
        assertTrue(bundle.getLong("longDoesNotExist").isEmpty());
    }

    @Test
    void testGetRequiredLong() {
        // Test getting a present long value
        assertEquals(Long.valueOf(123456789), bundle.getRequiredLong("longCorrect"));

        // Test getting a present incorrect long value
        Assertions.assertThrows(NumberFormatException.class, () -> bundle.getRequiredLong("longIncorrect"));

        // Test getting an absent long value
        Assertions.assertThrows(MissingResourceException.class, () -> bundle.getRequiredLong("longDoesNotExist"));
    }

    @Test
    void testGetDouble() {
        // Test getting a present correct double value
        final Optional<Double> optionalCorrectDouble = bundle.getDouble("doubleCorrect");
        assertTrue(optionalCorrectDouble.isPresent());
        assertEquals(Double.valueOf(12345678.12345678), optionalCorrectDouble.orElseThrow());

        // Test getting a present incorrect double value
        Assertions.assertThrows(NumberFormatException.class, () -> bundle.getDouble("doubleIncorrect"));

        // Test getting an absent double value
        assertTrue(bundle.getDouble("longDoesNotExist").isEmpty());
    }

    @Test
    void testGetRequiredDouble() {
        // Test getting a present double value
        assertEquals(Double.valueOf(12345678.12345678), bundle.getRequiredDouble("doubleCorrect"));

        // Test getting a present incorrect double value
        Assertions.assertThrows(NumberFormatException.class, () -> bundle.getRequiredDouble("doubleIncorrect"));

        // Test getting an absent double value
        Assertions.assertThrows(MissingResourceException.class, () -> bundle.getRequiredDouble("doubleDoesNotExist"));
    }

    @Test
    void testGetString() {
        // Test getting a present string value
        assertEquals("bottom", bundle.getString("layer"));

        // Test getting an absent string value
        assertNull(bundle.getString("stringDoesNotExist"));
    }

    @Test
    void testGetStringWithArguments() {
        // Test getting a present string value with an actual argument
        assertEquals("Hello world!", bundle.getString("helloMessage", "world!"));
    }

    @Test
    void testGetRequiredString() {
        // Test getting a present string value
        assertEquals("bottom", bundle.getRequiredString("layer"));

        // Test getting an absent string value
        Assertions.assertThrows(MissingResourceException.class, () -> bundle.getRequiredString("stringDoesNotExist"));
    }

    @Test
    void testGetRequiredStringWithArguments() {
        // Test getting a present string value with an actual argument
        assertEquals("Hello world!", bundle.getRequiredString("helloMessage", "world!"));

        // Test getting an absent string value but with an actual argument
        Assertions.assertThrows(MissingResourceException.class,
            () -> bundle.getRequiredString("stringDoesNotExist", "world!"));
    }

    @Test
    void testFormat() {
        // Test with valid arguments
        assertEquals("Record 1 of 2", bundle.format("Record {0} of {1}", 1, 2));

        // Test with missing arguments
        assertEquals("Record {0} of {1}", bundle.format("Record {0} of {1}", (Object[]) null));
        assertEquals("Record {0} of {1}", bundle.format("Record {0} of {1}", (Object) null));
    }

    @Test
    void testToString() {
        assertEquals("[com.schotanus.util.top,com.schotanus.util.middle,com.schotanus.util.bottom]",
                bundle.toString());
    }
}
