/*
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Utility class, consisting of mathematical methods that operate on BigIntegers.<br>
 * Keep in mind that using methods of this class could involve the creation of a lot of large objects.
 */
public class CBigInteger {

    private static final BigInteger THREE = BigInteger.valueOf(3);

    /**
     * Private constructor, prevents creation of an instance outside this class.
     */
    private CBigInteger() {
    }

    /**
     * Determines if the supplied number is a prime number.<br>
     * A number is a prime if the number is only divisible by 1 and by itself.
     * By definition 0 and 1 are not primes.<br>
     * You can check whether a number is a prime by dividing the number by all values from 2 up to and including the square
     * root of the number.
     * If none of these divisions result in an integer the number is a prime.<br>
     * The only optimization made here is that the number is only divided by odd numbers from 3 up.
     * In theory this would be twice as fast compared to having no optimization at all but still this method is quite slow for
     * large values!<br>
     * Did you know that for every n &gt;= 2 at least one prime exists between n and 2 times n?<br>
     * Since instances are immutable this method is quite slow and involves the creation of a lot of objects!
     * @return True when the supplied number is a prime, false otherwise.
     */
    public static boolean isPrime(final BigInteger number) {
        boolean isPrime;
        if (number.compareTo(BigInteger.TWO) == 0) {
            isPrime = true;
        } else if (number.compareTo(BigInteger.TWO) < 0 || number.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
            isPrime = false;
        } else {
            isPrime = true;
            final BigInteger root = number.sqrt();
            for (BigInteger bigInteger = THREE;
                 bigInteger.compareTo(root) <= 0;
                 bigInteger = bigInteger.add(BigInteger.TWO)) {
                if (number.mod(bigInteger).compareTo(BigInteger.ZERO) == 0) {
                    isPrime = false;
                    break;
                }
            }
        }

        return isPrime;
    }

    /**
     * Computes the factorial of the supplied number<br>
     * The factorial of 0 is 1 and for all other positive numbers n it is defined as: 1x2x3x...n<br>
     * Precondition: Value &gt;= 0.
     * No check however is made in this method, it just returns 1 when the precondition is not met.<br>
     * Note: Calculating the factorial of large numbers takes considerable time since a lot of new BigIntegers are created in
     * the process.
     * @return Factorial of the supplied number or 1 when the precondition is not met.
     * @see CLong#factorial(int)
     */
    public static BigInteger factorial(final BigInteger number) {
        BigInteger factorial = BigInteger.ONE;
        BigInteger counter = BigInteger.TWO;
        while (counter.compareTo(number) <= 0) {
            factorial = factorial.multiply(counter);
            counter = counter.add(BigInteger.ONE);
        }

        return factorial;
    }

    /**
     * Computes Collatz Conjecture.<br>
     * For details see: <a href="http://en.wikipedia.org/wiki/Collatz_conjecture">Wikipedia</a><br>
     * The simple rules are that while the number is not 1
     * &nbsp;&nbsp;&nbsp;&nbsp;If the number is even, divide it by two, else multiply with 3 and add 1.
     * @param number The number to compute Collatz Conjecture for.
     * @return The list of all the numbers in Collatz Conjecture.<br>
     *  The returned list starts with the supplied number and ends with the number 1.
     *  That is to say, if Collatz is right.
     *  When Collatz is not right, the list will grow until you are out of memory.
     *  Unfortunately Collatz can still be right when you run out of memory.
     *  Maybe you just have to increase the available memory.
     */
    public static List<BigInteger> computeCollatzConjecture(final BigInteger number) {
        if (number.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("Collatz Conjecture expects a positive integer, but received:" + number);
        }

        final List<BigInteger> result = new ArrayList<>();
        result.add(number);

        BigInteger currentValue = number;
        while (!currentValue.equals(BigInteger.ONE)) {
            if (!currentValue.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                currentValue = currentValue.multiply(THREE).add(BigInteger.ONE);
                result.add(currentValue);
                // At this point we must have an even number, so divide by 2.
            }
            currentValue = currentValue.divide(BigInteger.TWO);
            result.add(currentValue);
        }

        return result;
    }

    /**
     * Factorizes the supplied BigInteger number into its prime factors.<br>
     * Note: This method is really slow and has to be optimized.
     * Consider using {@link CLong#factorize(long)} for cases where computations using log values are sufficient.
     * On my machine factorizing the first million numbers takes 275ms using longs, using BigIntegers it takes 6022ms.
     * @param number The number to factorize.
     * @return A list of prime factors.
     * @throws IllegalArgumentException When the supplied number is &lt; 2.
     * @throws NullPointerException When the supplied number is null.
     * @see CLong#factorize(long)
     */
    public static List<PrimeFactor> factorize(final BigInteger number) {
        if (number.compareTo(BigInteger.TWO) < 0) {
            throw new IllegalArgumentException("Factorization needs a number larger than one, but is:" + number);
        }

        final List<PrimeFactor> primeFactors = new ArrayList<>();

        final BigInteger lastFactor = number.sqrt();
        BigInteger factor = BigInteger.TWO;
        BigInteger remainder = factorize(primeFactors, number, factor);
        for (factor = BigInteger.valueOf(3); factor.compareTo(lastFactor) <= 0 && remainder.compareTo(BigInteger.ONE) != 0;
             factor = factor.add(BigInteger.TWO)) {
            remainder = factorize(primeFactors, remainder, factor);
        }

        if (remainder.compareTo(BigInteger.ONE) != 0) {
            primeFactors.add(new PrimeFactor(remainder, 1));
        }

        return primeFactors;
    }

    /**
     * Determines whether the supplied BigInteger factor is a prime factor of the supplied number.<br>
     * When the supplied factor is a factor of the supplied number, a prime factor is added to the list of prime factors.
     * @param primeFactors The list of prime factors found so far.
     * @param number The number to check.
     * @param factor The factor to check whether it is a prime factor of the supplied number.
     * @return When the supplied factor is no prime factor of the supplied number then the number is returned as is,
     *  otherwise the number that remains after factoring out the supplied factor is returned.<br>
     *  For example: number=45 and factor=2. Since 2 is no factor of 45, the number 45 is returned.
     *  When the number=45 and the factor=3 then the number 45 / 3 / 3 =&gt; 5 is returned and 3 with an exponent of 2 is added
     *  to the supplied ist of prime factors.
     */
    private static BigInteger factorize(
            final List<PrimeFactor> primeFactors, final BigInteger number, final BigInteger factor) {
        int exponent = 0;

        BigInteger remainder = number;
        BigInteger[] divAndMod = remainder.divideAndRemainder(factor);
        while (divAndMod[1].compareTo(BigInteger.ZERO) == 0) {
            ++exponent;
            remainder = divAndMod[0];
            divAndMod = remainder.divideAndRemainder(factor);
        }

        if (exponent != 0) {
            primeFactors.add(new PrimeFactor(factor, exponent));
        }

        return remainder;
    }
}
