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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for {@link CodeDescriptionComparator}.
 */
class CodeDescriptionComparatorTest {

    private final List<CodeDescription<Integer>> elements = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        elements.add(new CodeDescription<>(1, "Hydrogen"));
        elements.add(new CodeDescription<>(2, "Helium"));
        elements.add(new CodeDescription<>(null, "Null1"));
        elements.add(new CodeDescription<>(3, "Lithium"));
        elements.add(new CodeDescription<>(null, "Null2"));
        elements.add(new CodeDescription<>(4, "Beryllium"));
        // Add Calcium to verify we can sort numerically on code
        elements.add(new CodeDescription<>(20, "Calcium"));
    }

    @Test()
    void testCompareUsingDefaults() {
        elements.sort(new CodeDescriptionComparator<>());
        assertEquals("[Beryllium, Calcium, Helium, Hydrogen, Lithium, Null1, Null2]", elements.toString());
    }

    @Test()
    void testCompareOnDescription() {
        elements.sort(new CodeDescriptionComparator<>(true));
        assertEquals("[Beryllium, Calcium, Helium, Hydrogen, Lithium, Null1, Null2]", elements.toString());
    }

    @Test()
    void testCompareOnDescriptionDescending() {
        elements.sort(new CodeDescriptionComparator<>(true, false));
        assertEquals("[Null2, Null1, Lithium, Hydrogen, Helium, Calcium, Beryllium]", elements.toString());
    }

    @Test()
    void testCompareOnCodeAscending() {
        elements.sort(new CodeDescriptionComparator<>(false, true));
        assertEquals("[Null1, Null2, Hydrogen, Helium, Lithium, Beryllium, Calcium]", elements.toString());
    }

    @Test()
    void testCompareOnCodeDescending() {
        elements.sort(new CodeDescriptionComparator<>(false, false));
        assertEquals("[Calcium, Beryllium, Lithium, Helium, Hydrogen, Null1, Null2]", elements.toString());
    }

    @Test()
    void testCompareOnCodeWithNullValues() {
        final List<CodeDescription<Double>> codeDescriptions = new ArrayList<>();
        codeDescriptions.add(new CodeDescription<>(null, "e"));
        codeDescriptions.add(new CodeDescription<>(-1D, "i"));
        codeDescriptions.add(new CodeDescription<>(null, "pi"));
        codeDescriptions.sort(new CodeDescriptionComparator<>(false, false));

        // The null values should be sorted last.
        // Check that the first element is the one with the non-null code
        assertEquals("i", codeDescriptions.get(0).description());
    }

    @Test()
    void testToString() {
        assertEquals("Sort on: description (ascending)", new CodeDescriptionComparator<Long>().toString());
        assertEquals("Sort on: description (descending)", new CodeDescriptionComparator<Long>(true, false).toString());
        assertEquals("Sort on: code (ascending)", new CodeDescriptionComparator<Long>(false).toString());
        assertEquals("Sort on: code (descending)", new CodeDescriptionComparator<Long>(false,false).toString());
    }

}
