/*
 * Copyright (C) 2023 Kees Schotanus
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
import java.util.stream.Stream;


/**
 * Math class that computes the
 * <a href="https://en.wikipedia.org/wiki/Fibonacci_sequence"></a>Fibonacci sequence</a>.<br>
 * Previous implementations used iterators, but here Streams are uses to generate the sequence.
 * The current implementation only works with BigIntegers since a long can only contain the first 92 Fibonacci numbers.
 * To print the first 15 Fibonacci numbers you can use:<br>
 * <pre><code>
 * Fibonacci.fibonacci().limit(15).forEach(System.out::println);
 * </code></pre>
 * You can even supply the starting values like this:<br>
 * <pre><code>
 * Fibonacci.fibonacci(BigInteger.valueOf(377), BigInteger.valueOf(610)).limit(15).forEach(System.out::println);
 * </code></pre>
 */
public class Fibonacci {

    private BigInteger previous;
    private BigInteger current;

    /**
     * Constructs the standard Fibonacci sequence.
     */
    private Fibonacci() {
        this.previous = BigInteger.ZERO;
        this.current = BigInteger.ONE;
    }
    /**
     * Constructs a possibly non-standard Fibonacci sequence.
     * @param previous The first Fibonacci number.
     * @param current The second Fibonacci number.
     */
    private Fibonacci(final BigInteger previous, final BigInteger current) {
        this.previous = previous;
        this.current = current;
    }

    /**
     * Computes the next Fibonacci number.
     * @return The next Fibonacci number.
     */
    Fibonacci next() {
        final BigInteger result = previous.add(current);
        previous = current;
        current = result;
        return new Fibonacci(previous, current);
    }

    /**
     * Gets the current Fibonacci number.
     * @return The current Fibonacci number.
     */
    BigInteger value() {
        return current;
    }

    /**
     * Creates a Stream of standard Fibonacci numbers.
     * @return A Stream of standard Fibonacci numbers.
     */
    public static Stream<BigInteger> fibonacci() {
        return Stream
                .iterate(new Fibonacci(), Fibonacci::next)
                .map(Fibonacci::value);
    }

    /**
     * Creates a Stream of Fibonacci numbers, where you can supply the first two numbers.
     * @param previous The first Fibonacci number.
     * @param current The current Fibonacci number.
     * @return A Stream of Fibonacci numbers.
     */
    public static Stream<BigInteger> fibonacci(final BigInteger previous, final BigInteger current) {
        return Stream
                .iterate(new Fibonacci(previous, current), Fibonacci::next)
                .map(Fibonacci::value);
    }

    /**
     * Determines if the supplied number represents a number that occurs in the row of Fibonacci numbers.<br>
     * This method uses a test formulated by Ira Gessel:<br>
     * The number n occurs in the row of Fibonacci numbers when 5n<sup>2</sup>&nbsp;+&nbsp;4 or 5n<sup>2</sup>&nbsp;-&nbsp;4
     * is a perfect square.
     * @return True when this CBigInteger represents a number that occurs in the row of Fibonacci numbers.
     */
    public static boolean isFibonacci(final BigInteger number) {
        final BigInteger fiveNumberSquared = number.multiply(number).multiply(BigInteger.valueOf(5));
        final BigInteger fiveNumberSquaredPlusFour = fiveNumberSquared.add(BigInteger.valueOf(4));
        BigInteger squareRoot = fiveNumberSquaredPlusFour.sqrt();

        boolean result = false;
        if (squareRoot.multiply(squareRoot).equals(fiveNumberSquaredPlusFour)) {
            result = true;
        } else {
            final BigInteger fiveNumberSquaredMinusFour = fiveNumberSquared.subtract(BigInteger.valueOf(4));
            squareRoot = fiveNumberSquaredMinusFour.sqrt();
            if (squareRoot.multiply(squareRoot).equals(fiveNumberSquaredMinusFour)) {
                result = true;
            }
        }

        return result;
    }
}
