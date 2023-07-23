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

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for {@link CodeDescription}.
 */
class CodeDescriptionTest {

    @Test()
    void testConstructor() {
        final CodeDescription<Integer> codeDescription = new CodeDescription<>(1, "Hydrogen");
        assertEquals(1, codeDescription.code());
        assertEquals("Hydrogen", codeDescription.description());
    }

    @Test()
    void testToString() {
        final CodeDescription<Integer> codeDescription = new CodeDescription<>(null, "Helium");
        assertEquals("Helium", codeDescription.toString());
    }

}
