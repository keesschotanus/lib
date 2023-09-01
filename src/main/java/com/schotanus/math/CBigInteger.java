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
            final BigInteger root = squareRoot(number);
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
     * Computes the square root of the supplied number.<br>
     * This method uses the Babylonian method.
     * This method can be derived from Newton's method but the Babylonian method is older.<br>
     * Mathematically it works like this:<br>
     * Assume the root of <i>S</i> needs to be calculated.<br>
     * Start with an arbitrary positive number as close to the root of <i>S</i> as possible.<br>
     * Let x<sub>n</sub> + 1 be the average of x<sub>n</sub> and <i>S</i> / x<sub>n</sub>.<br>
     * The information above is from:
     * <a href=http://en.wikipedia.org/wiki/Methods_of_computing_square_roots>Methods of computing square roots</a> on
     * WikiPedia.<br>
     * This Babylonian method has been changed to work with BigInteger values without resorting to the usage of BigDecimal
     * objects.
     * @return Approximate root of the supplied number.<br>
     * The returned number is guaranteed to be the closest integer root.
     * @throws ArithmeticException When this object has a negative number.
     */
    public static BigInteger squareRoot(final BigInteger number) throws ArithmeticException{
        if (number.signum() == -1) {
            throw new ArithmeticException("Can't compute the square root of negative number:" + number);
        } else if (number.signum() == 0) {
            return BigInteger.ZERO;
        }

        BigInteger previousApproximation;
        BigInteger currentApproximation;

        // Difference between the two approximations
        BigInteger difference;

        /*
         * Create the initial guess.
         * When this big integer is larger than will fit in a double, the largest long number is used,
         * else we use the square root of the double number as the initial guess.
         */
        final double doubleValue = number.doubleValue();
        if (Double.POSITIVE_INFINITY == doubleValue) {
            currentApproximation = BigInteger.valueOf(Long.MAX_VALUE);
        } else {
            currentApproximation = BigInteger.valueOf((long)Math.sqrt(doubleValue));
        }

        do {
            previousApproximation = currentApproximation;
            currentApproximation = number.divide(previousApproximation);
            currentApproximation = currentApproximation.add(previousApproximation);
            currentApproximation = currentApproximation.divide(BigInteger.TWO);

            difference = previousApproximation.subtract(currentApproximation).abs();
        } while (difference.compareTo(BigInteger.ONE) > 0);

        /*
         * The current and previous approximations are either:
         * - equal
         * - or differ by one.
         * When equal we increment the current approximation by one.
         * The algorithm above uses integer division and that comes down to rounding down.
         * 7 / 2 for example is 3. The answer could be one off!
         *
         * We now have two approximations, and we compute which one is closest to the answer.
         */
        if (currentApproximation.equals(previousApproximation)) {
            currentApproximation = currentApproximation.add(BigInteger.ONE);
        }
        final BigInteger currentDifference = currentApproximation.multiply(currentApproximation).subtract(number).abs();
        final BigInteger previousDifference = previousApproximation.multiply(previousApproximation).subtract(number).abs();

        return currentDifference.compareTo(previousDifference) < 1 ? currentApproximation : previousApproximation;
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
            if (currentValue.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                currentValue = currentValue.divide(BigInteger.TWO);
            } else {
                currentValue = currentValue.multiply(THREE).add(BigInteger.ONE);
                result.add(currentValue);
                // At this point we must have an even number, so divide by 2.
                currentValue = currentValue.divide(BigInteger.TWO);
            }
            result.add(currentValue);
        }

        return result;
    }

}
