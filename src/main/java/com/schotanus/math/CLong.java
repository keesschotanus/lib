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


/**
 * Utility class for Mathematical functions that operate on long values.
 */
public final class CLong {

    /**
     * The maximum number for which the factorial can be calculated using a long value.
     */
    public static final int MAX_FACTORIAL = 20;

    /**
     * Private constructor prevents creation of an instance outside this class.
     */
    private CLong() {
    }


    /**
     * Computes the greatest common divisor of two long values.<br>
     * Given two integers a and b, a common divisor of a and b is an integer which divides both numbers, and the greatest common
     * divisor is the largest such integer.<br>
     * The algorithm used is the one by Euclid found in Book VII of Euclid's 'Elements'.<br>
     * @param first The first value.
     * @param second The second value.
     * @return Greatest common divisor of the supplied values.
     * @throws IllegalArgumentException When the supplied second parameter is 0.
     */
    public static long greatestCommonDivisor(final long first, final long second) {
        if (second == 0) {
            throw new IllegalArgumentException("Second value can not be 0!");
        }

        // Copy final parameters in non-final variables
        long a = first;
        long b = second;

        long r = a % b;     // Compute remainder
        while (r != 0) {    // which will very soon be 0.
            a = b;
            b = r;
            r = a % b;
        }

        return b;
    }

    /**
     * Computes the least common multiple (lcm) of two long values.<br>
     * The lcm of a and b is the smallest integer that is divisible by both a and b.
     * The lcm can easily be computed if the greatest common divisor (gcd) of a and b is known: (lcm = a*b/gcd).
     * @param a The first value.
     * @param b The second value.
     * @return Least common multiple of the supplied values a and b.
     */
    public static long leastCommonMultiple(final long a, final long b) {
        return a * b / CLong.greatestCommonDivisor(a, b);
    }

    /**
     * Determines if the supplied value is a prime number.<br>
     * A number is a prime if the number can only be divided by 1 and by itself.
     * By definition 0 and 1 are not prime numbers.<br>
     * You can check whether a number is a prime by dividing the number by all values from 2 up to and including the square root
     * of the number.
     * If none of these divisions results in an integer the number is a prime number.<br>
     * The only optimization made here is that the number is only divided by odd numbers from 3 up.
     * In theory this would be twice as fast compared to having no optimization at all but still this method is quite slow for
     * large values!<br>
     * Did you know that for every n &gt;= 2 at least one prime number exists between n and 2 times n?
     * @param value Value to check if it is a prime number or not.
     * @return True when the supplied value is a prime, false otherwise.
     */
    public static boolean isPrime(final long value) {
        if (value == 2L) {
            return true;
        } else if (value < 2 || value % 2 == 0) {
            return false;
        }

        final long three = 3;
        final long root = (long)Math.sqrt(value);
        for (long i = three; i <= root; i += 2) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes the factorial of the supplied value.<br>
     * The factorial of 0 is 1 and for all other positive numbers n it is defined as: 1x2x3x...n
     * @param value Value for which the factorial is calculated.
     * @return Factorial of the supplied value.
     * @throws IllegalArgumentException When the supplied value is not in the range:<br>
     * 0 &lt;= value &lt;= {@link CLong#MAX_FACTORIAL}.
     */
    public static long factorial(final int value) {
        if (value < 0 || value > MAX_FACTORIAL) {
            throw new IllegalArgumentException(
                    String.format("Value: %d, is not in the range [%d,%d]", value, 0, MAX_FACTORIAL));
        }

        long factorial = 1L;
        for (long i = 2L; i <= value; i++) {
            factorial *= i;
        }

        return factorial;
    }

    /**
     * Rounds the supplied value to the supplied position.
     * <br>The value is rounded to the nearest neighbor unless both neighbors are equidistant,
     * in which case the value is rounded up.<br>
     * For example:<br>
     * CLong.round( 54, 1) =&gt;  50 since value is rounded down.<br>
     * CLong.round(-55, 1) =&gt; -50 since value is rounded up.
     * @param value The value to round.
     * @param position The position to which this value should be rounded.
     *  <br>1 rounds to tens.
     *  <br>2 rounds to hundreds.
     *  <br>3 rounds to thousands.
     * @return Supplied value rounded to the supplied position.
     * @throws IllegalArgumentException When the supplied position is negative.
     */
    public static long round(final long value, final int position) {
        if (position < 0) {
            throw new IllegalArgumentException("You can only round to a positive position, but the position is:" + position);
        }

        return (long) (Math.round(value / Math.pow(10, position)) * Math.pow(10, position));
    }

    /**
     * Rounds the supplied value up to the supplied position.
     * CLong.round( 54, 1) =&gt;  60 since value is rounded up.<br>
     * CLong.round(-55, 1) =&gt; -50 since value is rounded up.
     * @param value The value to round.
     * @param position The position to which this value should be rounded.
     *  <br>1 rounds to tens.
     *  <br>2 rounds to hundreds.
     *  <br>3 rounds to thousands.
     * @return Supplied value rounded to the supplied position.
     * @throws IllegalArgumentException When the supplied position is negative.
     */
    public static long roundUp(final long value, final int position) {
        if (position < 0) {
            throw new IllegalArgumentException("You can only round to a positive position, but the position is:" + position);
        }

        return (long)(Math.ceil(value / Math.pow(10, position)) * Math.pow(10, position));
    }

}
