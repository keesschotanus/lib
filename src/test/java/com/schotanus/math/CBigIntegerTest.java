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

     /**
      * Tests {@link CBigInteger#factorial(BigInteger)}.
      */
     @Test
     void testFactorize() {
         // Try with 2
         final List<PrimeFactor> expectedPrimeFactorsOf2 = List.of(new PrimeFactor(BigInteger.TWO,1));
         assertEquals(expectedPrimeFactorsOf2, CBigInteger.factorize(BigInteger.TWO));


         // Try with small value
         final List<PrimeFactor> expectedPrimeFactorsOf121 = List.of(new PrimeFactor(BigInteger.valueOf(11),2));
         assertEquals(expectedPrimeFactorsOf121, CBigInteger.factorize(BigInteger.valueOf(121L)));

         // Try with a large number
         final List<PrimeFactor> expectedPrimeFactorsOf987654321 = List.of(
                 new PrimeFactor(BigInteger.valueOf(3L),2),
                 new PrimeFactor(BigInteger.valueOf(17L),2),
                 new PrimeFactor(BigInteger.valueOf(379721L),1)
         );
         assertEquals(expectedPrimeFactorsOf987654321, CBigInteger.factorize(BigInteger.valueOf(987654321L)));


         // Try with a really large number that actually needs a BigInteger.
         final BigInteger factorialOf15 = CBigInteger.factorial(BigInteger.valueOf(15));
         final List<PrimeFactor> expectedPrimeFactorsOf1307674367999 = List.of(
                 new PrimeFactor(BigInteger.valueOf(17L), 1),
                 new PrimeFactor(BigInteger.valueOf(31L),2),
                 new PrimeFactor(BigInteger.valueOf(53L),1),
                 new PrimeFactor(BigInteger.valueOf(1510259L),1)
         );
         final BigInteger factorialOf15Minus1 = factorialOf15.subtract(BigInteger.ONE);
         assertEquals(expectedPrimeFactorsOf1307674367999, CBigInteger.factorize(factorialOf15Minus1));
     }

     /**
      * Tests {@link CLong#factorize(long)} with illegal input.
      */
     @Test
     void testFactorizeWithIllegalInput() {
         assertThrows(IllegalArgumentException.class, () -> CBigInteger.factorize(BigInteger.ONE));
     }
 }
