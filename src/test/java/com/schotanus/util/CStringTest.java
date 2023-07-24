/*
 * Copyright (C) 2001 Kees Schotanus
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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link CString}.
 */
class CStringTest {

    @Test
    void testIs() {
        // Test alphabetic value
        assertTrue(CString.is("abc", Character::isAlphabetic));

        // Test non-alphabetic value
        assertFalse(CString.is("123", Character::isAlphabetic));

        // An empty String is considered to be alphabetic
        assertTrue(CString.isAlphanumeric(""));
    }

    @Test
    void testIsAlphanumeric() {
        // Test alphanumeric value
        assertTrue(CString.isAlphanumeric("abc124"));

        // Test non-alphanumeric value
        assertFalse(CString.isAlphanumeric(" "));

        // An empty String is considered to be alphanumeric
        assertTrue(CString.isAlphanumeric(""));
    }
}
