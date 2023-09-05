/*
 * Copyright (C) 2008 Kees Schotanus
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link CLong}.
 */
final class CLongTest {

    /**
     * Tests {@link CLong#greatestCommonDivisor(long, long)}.
     */
    @Test
    void testGreatestCommonDivisor() {
        assertEquals(4L, CLong.greatestCommonDivisor(4L, 8L));
        assertEquals(4L, CLong.greatestCommonDivisor(8L, 4L));
        assertEquals(1L, CLong.greatestCommonDivisor(3L, 5L));
        assertEquals(1L, CLong.greatestCommonDivisor(5L, 3L));

        // Second parameter may not be null
        assertThrows(IllegalArgumentException.class, () -> CLong.greatestCommonDivisor(1L, 0L));
    }

    /**
     * Tests {@link CLong#leastCommonMultiple(long, long)}.
     */
    @Test
    void testLeastCommonMultiple() {
        assertEquals(8L, CLong.leastCommonMultiple(8L, 4L));
        assertEquals(8L, CLong.leastCommonMultiple(4L, 8L));
        assertEquals(15L, CLong.leastCommonMultiple(3L, 5L));
        assertEquals(15L, CLong.leastCommonMultiple(5L, 3L));
    }

    /**
     * Tests {@link CLong#isPrime(long)}.
     */
    @Test
    void testIsPrime() {
        assertFalse(CLong.isPrime(0L));
        assertFalse(CLong.isPrime(1L));
        assertTrue(CLong.isPrime(2L));
        assertTrue(CLong.isPrime(3L));
        assertFalse(CLong.isPrime(4L));
        assertTrue(CLong.isPrime(5L));
        assertFalse(CLong.isPrime(6L));
        assertTrue(CLong.isPrime(7L));
        assertFalse(CLong.isPrime(8L));
        assertFalse(CLong.isPrime(9L));
        assertFalse(CLong.isPrime(10L));
        assertTrue(CLong.isPrime(11L));
        assertFalse(CLong.isPrime(12L));
        assertTrue(CLong.isPrime(13L));

        // Test some larger numbers to avoid lookup in the table of prime numbers
        assertTrue(CLong.isPrime(33_331));
        assertTrue(CLong.isPrime(333_331));
        assertTrue(CLong.isPrime(3_333_331));
        assertTrue(CLong.isPrime(33_333_331));
        assertFalse(CLong.isPrime(333_333_331));
    }

    /**
     * Tests {@link CLong#factorial(int)}.
     */
    @Test
    void testFactorial() {
        assertEquals(1L, CLong.factorial(0));
        assertEquals(1L, CLong.factorial(1));
        assertEquals(2L, CLong.factorial(2));
        assertEquals(6L, CLong.factorial(3));
        assertEquals(24L, CLong.factorial(4));
        assertEquals(120L, CLong.factorial(5));

        // Test with a negative number
        assertThrows(IllegalArgumentException.class, () -> CLong.factorial(-1));

        // Test with a too large number
        assertThrows(IllegalArgumentException.class, () -> CLong.factorial(CLong.MAX_FACTORIAL + 1));
    }

    /**
     * Tests {@link CLong#round(long, int)}.
     */
    @Test
    void testRound() {
        // Rounding to units should leave the original value unchanged
        assertEquals(1L, CLong.round(1, 0));
        assertEquals(5L, CLong.round(5, 0));
        assertEquals(11L, CLong.round(11, 0));
        assertEquals(55L, CLong.round(55, 0));

        // Round to tens
        assertEquals(10L, CLong.round(10, 1));
        assertEquals(40L, CLong.round(40, 1));
        assertEquals(50L, CLong.round(50, 1));
        assertEquals(50L, CLong.round(54, 1));
        assertEquals(60L, CLong.round(55, 1));
        assertEquals(60L, CLong.round(56, 1));
        assertEquals(90L, CLong.round(90, 1));
        assertEquals(90L, CLong.round(94, 1));
        assertEquals(100L, CLong.round(95, 1));
        assertEquals(100L, CLong.round(99, 1));

        // Test rounding negative numbers to tens
        assertEquals(-10L, CLong.round(-10, 1));
        assertEquals(-40L, CLong.round(-40, 1));
        assertEquals(-50L, CLong.round(-50, 1));
        assertEquals(-50L, CLong.round(-54, 1));
        assertEquals(-50L, CLong.round(-55, 1));
        assertEquals(-60L, CLong.round(-56, 1));
        assertEquals(-90L, CLong.round(-90, 1));
        assertEquals(-90L, CLong.round(-94, 1));
        assertEquals(-90L, CLong.round(-95, 1));
        assertEquals(-100L, CLong.round(-99, 1));

        // Test rounding with a negative position
        assertThrows(IllegalArgumentException.class, () -> CLong.round(1, -1));
    }

    /**
     * Tests {@link CLong#roundUp(long, int)}.
     */
    @Test
    void testRoundUp() {
        // Rounding to units should leave the original value unchanged
        assertEquals(1L, CLong.roundUp(1, 0));
        assertEquals(5L, CLong.roundUp(5, 0));

        // Round to tens
        assertEquals(10L, CLong.roundUp(10, 1));
        assertEquals(20L, CLong.roundUp(11, 1));

        // Test rounding negative numbers to tens
        assertEquals(-10L, CLong.roundUp(-10, 1));
        assertEquals(-10L, CLong.roundUp(-10, 1));

        // Test rounding up with a negative position
        assertThrows(IllegalArgumentException.class, () -> CLong.roundUp(1, -1));
    }

    /**
     * Tests {@link CLong#factorize(long)}.
     */
    @Test
    void testFactorize() {
        // Try with small value that force usage of the cached prime numbers
        final List<PrimeFactor> expectedPrimeFactorsOf121 = List.of(new PrimeFactor(BigInteger.valueOf(11),2));
        final List<PrimeFactor> primeFactorsOf121 = CLong.factorize(121L);
        assertEquals(expectedPrimeFactorsOf121, primeFactorsOf121);

        // Try with a large number to make sure that the result is actually computed, as opposed to being cached
        final List<PrimeFactor> expectedPrimeFactorsOf987654321 = List.of(
                new PrimeFactor(BigInteger.valueOf(3L),2),
                new PrimeFactor(BigInteger.valueOf(17L),2),
                new PrimeFactor(BigInteger.valueOf(379721L),1)
        );
        final List<PrimeFactor> primeFactorsOf987654321 = CLong.factorize(987654321L);
        assertEquals(expectedPrimeFactorsOf987654321, primeFactorsOf987654321);
    }
}
