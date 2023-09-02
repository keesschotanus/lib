/*
 * Copyright (C) 2003 Kees Schotanus
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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Unit tests for class {@link Combinations}.
 */
final class CombinationsTest {

    /**
     * Sample set.
     */
    private final Integer [] sampleSet = new Integer [] {1, 2, 3, 4, 5};

    /**
     * Tests {@link Combinations#countNumberOfCombinations(long, long)}
     */
    @Test
    void testCountNumberOfCombinations() {
        assertEquals(BigInteger.valueOf(2 * 3 * 4 * 5 / (2 * 3 * (2))), Combinations.countNumberOfCombinations(5, 3));

        // For negative n or r > n we expect zero combinations
        assertEquals(BigInteger.ZERO, Combinations.countNumberOfCombinations(0, 0));
        assertEquals(BigInteger.ZERO, Combinations.countNumberOfCombinations(1, 2));
    }

    /**
     * Tests {@link Combinations#iterator(Object[])}.
     * For our sample set consisting of 5 elements we expect 2<sup>5</sup> combinations.
     * But since the empty combination is not returned, we expect one less.
     */
    @Test
    void testIterator() {
        int combinations = 0;

        for (final Iterator<Object []> iterator = Combinations.iterator(sampleSet); iterator.hasNext(); ++combinations) {
            iterator.next();
        }
        assertEquals(combinations, (int)Math.pow(2, sampleSet.length) - 1);

        // Test with empty set
        assertThrows(IllegalArgumentException.class, () -> Combinations.iterator(new String[] {}));

        // Test with a too large set of 21 elements
        assertThrows(IllegalArgumentException.class, () -> Combinations.iterator(new Integer[] {
            0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0
        }));
    }

    /**
     * Tests {@link Combinations#iterator(Object[], int)} with n choose r where n equals 5 and r equals 0.
     */
    @Test
    void testIteratorWithSizeOfZero() {
        assertThrows(IllegalArgumentException.class, () -> Combinations.iterator(sampleSet, 0));
    }

    /**
     * Tests {@link Combinations#iterator(Object[], int)} with n choose r where n equals 5 and r varies from 1 to 5.
     */
    @Test
    void testIteratorWithValuesFromOneToFive() {
        final Integer [][][] expectedResults = new Integer [][][] {
            { {1}, {2}, {3}, {4}, {5} },
            { {1, 2}, {1, 3}, {1, 4}, {1, 5}, {2, 3}, {2, 4}, {2, 5}, {3, 4}, {3, 5}, {4, 5} },
            { {1, 2, 3}, {1, 2, 4}, {1, 2, 5}, {1, 3, 4}, {1, 3, 5}, {1, 4, 5,},{2, 3, 4}, {2, 3, 5}, {2, 4, 5}, {3, 4, 5} },
            { {1, 2, 3, 4}, {1, 2, 3, 5}, {1, 2, 4, 5}, {1, 3, 4, 5}, {2, 3, 4, 5 } },
            { {1, 2, 3, 4, 5} }
        };

        for (int r = 1; r <= 5; ++r) {
            final Iterator<Integer []> iterator = Combinations.iterator(sampleSet, r);
            for (Integer[] expectedResult : expectedResults[r-1]) {
                final Integer[] combination = iterator.next();
                assertEquals(Arrays.toString(expectedResult), Arrays.toString(combination));
            }
            assertFalse(iterator.hasNext());
        }
    }

    /**
     * Tests {@link Combinations#iterator(Object[], int)} with n choose r where n equals 5 and r equals 6.
     */
    @Test
    void testIteratorWithSizeOfSix() {
        assertThrows(IllegalArgumentException.class, () -> Combinations.iterator(sampleSet, sampleSet.length + 1));
    }

    /**
     * Tests {@link Combinations#iterator(Object[], int)}.
     * <br>This method performs an n choose r where n equals 26 and r equals 3.
     */
    @Test
    void testIteratorWithLargeSet() {
        final Object [] alphabetSet = new Object [] {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
        };
        final int r = 3;

        int combinations = 0;
        for (final Iterator<Object []> iterator = Combinations.iterator(alphabetSet, 3); iterator.hasNext(); ++combinations) {
            iterator.next();
        }
        assertEquals(BigInteger.valueOf(combinations), Combinations.countNumberOfCombinations(alphabetSet.length, r));
    }
}
