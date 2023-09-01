package com.schotanus.math;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit test class for class {@link Fibonacci}.
 */
class FibonacciTest {

    /**
     * Tests {@link Fibonacci#fibonacci()}.
     */
    @Test
    void testFibonacci() {
        final List<BigInteger> fibonacciSequence = Fibonacci.fibonacci().limit(15).toList();

        // We should have 15 elements in the list and the last element must be 610.
        assertEquals(15, fibonacciSequence.size());
        assertEquals(BigInteger.valueOf(610), fibonacciSequence.get(fibonacciSequence.size() -1) );
    }

    /**
     * Tests {@link Fibonacci#fibonacci(BigInteger, BigInteger)}.
     */
    @Test
    void testFibonacciNonStandard() {
        final List<BigInteger> fibonacciSequence =
                Fibonacci.fibonacci(BigInteger.TWO, BigInteger.TWO).limit(10).toList();

        // We should have 10 elements in the list and the last element must be 178.
        assertEquals(10, fibonacciSequence.size());
        assertEquals(BigInteger.valueOf(178), fibonacciSequence.get(fibonacciSequence.size() -1) );
    }

    /**
     * Tests {@link Fibonacci#isFibonacci(BigInteger)}
     */
    @Test
    void testIsFibonacci() {
        final List<BigInteger> fibonacciSequence = Fibonacci.fibonacci().limit(30).toList();
        // Verify that all numbers in the list are fibonacci numbers
        fibonacciSequence.forEach(fibonacci -> assertTrue(Fibonacci.isFibonacci(fibonacci)));

        final List<BigInteger> nonFibonacciNumbers = List.of(BigInteger.valueOf(4),BigInteger.valueOf(6),BigInteger.valueOf(9));
        // Verify that none of the numbers in the list are fibonacci numbers
        nonFibonacciNumbers.forEach(fibonacci -> assertFalse(Fibonacci.isFibonacci(fibonacci)));
    }

}
