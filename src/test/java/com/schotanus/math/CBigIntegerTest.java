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

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


 /**
 * Unit tests for class {@link CBigInteger}.
 */
final class CBigIntegerTest {

    /**
     * Tests {@link CBigInteger#isPrime(BigInteger)}.
     */
    @Test
    void testIsPrime() {
        assertFalse(CBigInteger.isPrime(BigInteger.ZERO));
        assertFalse(CBigInteger.isPrime(BigInteger.ONE));
        assertTrue(CBigInteger.isPrime(BigInteger.TWO));
        assertTrue(CBigInteger.isPrime(BigInteger.valueOf(3)));
        assertFalse(CBigInteger.isPrime(BigInteger.valueOf(4)));
        assertTrue(CBigInteger.isPrime(BigInteger.valueOf(5)));
        assertFalse(CBigInteger.isPrime(BigInteger.valueOf(6)));
        assertTrue(CBigInteger.isPrime(BigInteger.valueOf(7)));
        assertFalse(CBigInteger.isPrime(BigInteger.valueOf(8)));
        assertFalse(CBigInteger.isPrime(BigInteger.valueOf(9)));
    }

    /**
     * Tests {@link CBigInteger#factorial(BigInteger)}.
     */
    @Test
    void testFactorial() {
        assertEquals(BigInteger.ONE, CBigInteger.factorial(BigInteger.ZERO));
        assertEquals(BigInteger.ONE, CBigInteger.factorial(BigInteger.ONE));
        assertEquals(BigInteger.TWO, CBigInteger.factorial(BigInteger.TWO));
        assertEquals(BigInteger.valueOf(6), CBigInteger.factorial(BigInteger.valueOf(3)));
    }

     /**
      * Tests {@link CBigInteger#squareRoot(BigInteger)} with a negative number.
      */
     @Test
     void testSquareRootWithNegativeNumber() {
         final BigInteger minusOne = BigInteger.valueOf(-1);
         assertThrows(ArithmeticException.class, () -> CBigInteger.squareRoot(minusOne));
     }

     /**
      * Tests {@link CBigInteger#squareRoot(BigInteger)}.
      */
    @Test
    void testSquareRoot() {
        final int iterations = 1000;

        for (int i = 0; i < iterations; ++i) {
            final int answer = (int)Math.round(Math.sqrt(i));
            assertEquals(BigInteger.valueOf(answer), CBigInteger.squareRoot(BigInteger.valueOf(i)));
        }

        // Test square root of maximum Long value
        assertEquals(BigInteger.valueOf(3037000500L), CBigInteger.squareRoot(BigInteger.valueOf(Long.MAX_VALUE)));
    }

    /**
     * Tests {@link CBigInteger#computeCollatzConjecture(BigInteger)} with negative input.
     */
    @Test
    void testComputeCollatzConjectureWithNegativeInput() {
        final BigInteger negative = BigInteger.valueOf(Long.MIN_VALUE);
        assertThrows(IllegalArgumentException.class, () -> CBigInteger.computeCollatzConjecture(negative));
    }

    /**
     * Tests {@link CBigInteger#computeCollatzConjecture(BigInteger)} with zero input.
     */
    @Test
    void testComputeCollatzConjectureWithZeroInput() {
        assertThrows(IllegalArgumentException.class, () -> CBigInteger.computeCollatzConjecture(BigInteger.ZERO));
    }

    /**
     * Tests {@link CBigInteger#computeCollatzConjecture(BigInteger)} with standard input.
     */
    @Test
    void testComputeCollatzConjecture() {
        // Computing the conjecture for one should result in a single element with value 1.
        final List<BigInteger> resultOfOne = CBigInteger.computeCollatzConjecture(BigInteger.ONE);
        Assert.assertEquals(1, resultOfOne.size());
        Assert.assertEquals(BigInteger.ONE, resultOfOne.get(0));

        // Computing the conjecture for three should result in the element: 3, 10, 5, 16, 8, 4, 2, 1.
        final List<BigInteger> resultOfThree = CBigInteger.computeCollatzConjecture(BigInteger.valueOf(3));
        final int expectedLength = 8;
        Assert.assertEquals(expectedLength, resultOfThree.size());
        Assert.assertEquals(BigInteger.valueOf(3), resultOfThree.get(0));
        Assert.assertEquals(BigInteger.ONE, resultOfThree.get(expectedLength - 1));
    }

}
