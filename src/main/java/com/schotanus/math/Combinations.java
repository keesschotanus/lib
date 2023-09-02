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


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Mathematical utility class that handles combinations.<br>
 * Combinations are unordered sets of elements.
 * This class aids in creating combinations of a certain size (r) taken out of a set of elements (n).
 * This is known as 'n choose r' or C(n,r).<br>
 * Consider the set {a,b,c,d,e}.
 * What combinations can be made consisting of 3 elements?
 * The answer is {a,b,c}, {a,b,d}, {a,b,e}, {a,c,d}, {a,c,e}, {a,d,e}, {b,c,d}, {b,c,e}, {b,d,e}, {c,d,e}.
 * More formally the number of combinations can be expressed as:<br> n! / (r!(n-r)!), provided  n &gt;= r &gt;= 0<br>
 * Where n is the size of the set and r is the number of elements taken out of the set.
 * To be mathematically complete the number of combinations when<br>
 * n &lt; 0 or r &lt; 0 or r &gt; n is 0.<br>
 * To lookup more information about combinations on the Internet you may wish to use one or more of the following keywords:
 * Binomial coefficient, combinatorics, Pascal's Triangle.<br>
 * This class uses the Algorithm constructed by Keneth Rosen (Discrete Mathematics and Its Applications 4/E, Section 4.3).
 * The algorithm is actually quite simple.
 * Consider the example above about getting 3 elements out of a set of 5 elements {a,b,c,d,e}.
 * When getting combinations of size r create an array of pointers with r elements and initialize the array with values of
 * 0 to r - 1.
 * Here is what this would look like:<pre>
 * a, b, c, d, e
 * 0  1  2</pre>
 * As you can see a, b , c is the first combination. To get the second
 * combination increase the last pointer.
 * This looks like:<pre>
 * a, b, c, d,  e
 * 0  1     2</pre>
 * Increase the last pointer again, to get:<br><pre>
 * a, b, c, d, e
 * 0  1        2</pre>
 * Now you can't increase the last pointer since it would be out of bounds.
 * We have to increase the previous pointer and let the last pointer point just after the one before.
 * This looks like:<br><pre>
 * a, b, c, d, e
 * 0     1  2</pre>
 * You just have to repeat this process to get all combinations!
 * Check out the source for more information about the algorithm, particularly for efficiently moving around pointers.<br>
 * This class is suitable for large number of combinations.
 * Since combinations are computed one at a time this class consumes very little memory.
 * Due to the possibly large number of combinations the combinations are returned as an array for which no defensive copy has
 * been made.
 */
public final class Combinations {

    /**
     * The maximum number of elements, still considered to be a small set.
     * This value coincides with the maximum value for which the factorial can be computed using a long.
     */
    private static final int MAX_SIZE_OF_SMALL_SET = 20;

    private static final String NULL_SET_MESSAGE = "n may not be null!";

    /**
     * Private constructor prevents creation of an instance outside this class.
     */
    private Combinations() {
    }

    /**
     * Calculates the number of combinations for "n choose r".<br>
     * The formula used is: n! / r!(n - r)!
     *
     * @param n Number of elements in the set.
     * @param r Number of elements taken out of the set.
     * @return Total number of combinations that can be formed.
     *  When n &le; 0 or r &gt; n, 0 is returned.
     */
    public static BigInteger countNumberOfCombinations(final long n, final long r) {

        if (n <= 0 || r > n) {
            return BigInteger.ZERO;
        }

        final BigInteger nFactorial = CBigInteger.factorial(BigInteger.valueOf(n));
        final BigInteger rFactorial = CBigInteger.factorial(BigInteger.valueOf(r));
        return nFactorial.divide(rFactorial.multiply(CBigInteger.factorial(BigInteger.valueOf(n - r))));
    }

    /**
     * Creates an Iterator that can be used to iterate over all possible combinations of elements out of the supplied set.<br>
     * For the set {a,b,c,d,e} this iterator generates the combinations:<br>
     * a,b,c,d,e,ab,ac,ad,ae,bc,bd,be,cd,ce,de,abc,abd,abe,acd,ace,ade,bcd,bce,bde,cde,abcd,abce,abde,acde,bcde,abcde<br>
     * The combinations are ordered on size.
     * Combinations of the same size are ordered lexicographically, provided the supplied set is ordered.<br>
     * Note: The number of combinations can be calculated by 2<sup>set-size</sup>.
     * For a set consisting of 5 elements, 2<sup>5</sup>=32 combinations exist.
     * This method however will give you an iterator that only gets 31 combinations since it does not contain the 'empty'
     * combination.
     * @param <T> Type of single element of a combination.
     * @param set The set containing all the elements.
     * @return An iterator suitable for iterating over all possible combinations of elements out of the supplied set.
     * @throws NullPointerException When the supplied set is null.
     * @throws IllegalArgumentException the supplied set does not contain between 1 and {@link #MAX_SIZE_OF_SMALL_SET} elements.
     */
    public static <T> Iterator<T[]> iterator(final T[] set) {
        Objects.requireNonNull(set, "set cannot be null");

        if (set.length == 0 || set.length > MAX_SIZE_OF_SMALL_SET) {
            throw new IllegalArgumentException("The size of the set is not in the range [1,20], but is:" + set.length);
        }

        return new AllCombinationsIterator<>(set);
    }

    /**
     * Creates an Iterator to iterate over all possible combinations where each combination is of the supplied size r.<br>
     * This iterator will return combinations in lexicographic order provided that the supplied set is in lexicographic order!
     * Depending upon the supplied set and r an efficient iterator is returned.
     * @param <T> Type of single element of a combination.
     * @param set The set containing all the elements.
     * @param r Size each combination should have, in the range: [0,set.length]
     * @return An iterator suitable for iterating over the combinations of r elements.
     * @throws NullPointerException When the supplied set is null.
     * @throws IllegalArgumentException When the supplied r is not in the range [1,set.length()]
     */
    public static <T> Iterator<T[]> iterator(final T[] set, final int r) {
        Objects.requireNonNull(set, "set cannot be null");
        if (r <= 0 || r > set.length) {
            throw new IllegalArgumentException(String.format("r must be in range [1,%d], but is: %d", set.length, r));
        }

        final Iterator<T[]> result;
        if (set.length <= MAX_SIZE_OF_SMALL_SET) {
            result = new SmallSetCombinationsIterator<>(set, r);
        } else {
            result = new LargeSetCombinationsIterator<>(set, r);
        }

        return result;
    }

    /**
     * Computes the next combination.
     * @param n The set of elements.
     * @param r The number of elements taken out of n each time.
     * @param indexes The current indexes.
     * @param combination The current combination.
     * @param <T> The type of the combinations.
     */
    private static <T> void computeNextCombination(final T[] n, final int r, final int[] indexes, final T[] combination) {

        /*
         * Get the first pointer (from right to left) that won't be out of bounds when one is added to its value.
         * a,b,c,d,e
         * 0,1,    2
         * Pinter 2 is out of bounds when one is added, so look to the left to see that pointer one is not out of bounds.
         * a,b,c,d,e
         * 0     1,2
         * Now pointer 2 and 1 would be out of bounds but pointer 0 is not.
         */
        int i = r - 1;
        while (indexes[i] == n.length - r + i) {
            --i;
        }

        /*
         * Increase the previously determined pointer by one and adjust the pointers to the right. For example:
         * a,b,c,d,e
         * 0,    1,2   (pointer 0 must be increased and 1 and 2 must follow)
         *   0,1,2
         */
        indexes[i] += 1;
        for (int j = i + 1; j < r; ++j) {
            indexes[j] = indexes[i] + j - i;
        }

        // Create the combination from n and the indexes
        for (int count = 0; count < r; ++count) {
            combination[count] = n[indexes[count]];
        }
    }

    /**
     * Iterator suitable for iterating over the combinations of a small set (n).<br>
     * A set is small when it contains {@link #MAX_SIZE_OF_SMALL_SET} elements or less.
     * All calculations on these sets can easily be performed using primitives so this iterator is quite fast!
     * @param <T> Type of single element of a combination.
     */
    private static class SmallSetCombinationsIterator<T> implements Iterator<T[]> {

        /**
         * Set of elements.
         */
        private final T[] n;

        /**
         * Number of elements that are taken each time from n.
         */
        private final int r;

        /**
         * Indexes.<br>
         * For every r there is a pointer to n.<br>
         * At any given time the pointers point to the last combination that was returned.
         * For example: n = {a,b,c,d,e} and r=3.
         * After the first call to next() the first pointer points to 'a', the second to 'b' and the third to 'c'.<br>
         * a,b,c,d,e<br>
         * 0,1,2
         */
        private final int[] indexes;

        /**
         * The combination that will be returned.<br>
         * For performance reasons the same combination (with different values of course) is returned.
         */
        private final T[] combination;

        /**
         * Keeps count of the next combination.
         */
        private long nextCombination = 1;

        /**
         * Contains the total number of combinations.
         */
        private final long numberOfCombinations;

        /**
         * Creates an iterator that iterates over the combinations of a small set (n).
         * @param n The set of elements, which must contain at least one element.
         * @param r The number of elements taken out of n each time.<br>
         *  r <= n and r >= 0
         */
        SmallSetCombinationsIterator(final T[] n, final int r) {
            assert n != null : NULL_SET_MESSAGE;
            assert n.length > 0 : "n=" + n.length + ", but should at least be 1";
            assert r > 0 && r <= n.length : String.format("r=%d, but should be in the range [%d .. %d]", r, 1, n.length);

            this.n = n.clone();
            this.r = r;

            this.numberOfCombinations = CLong.factorial(n.length) / (CLong.factorial(r) * CLong.factorial(n.length - r));

            // Each combination consists of r elements
            @SuppressWarnings("unchecked")
            final T[] tempCombination = (T[])Array.newInstance(n[0].getClass(), r);
            this.combination = tempCombination;

            /*
             * A pointer is needed for every r.
             * Initially pointer 0 points to element 0 and pointer 1 points to
             * element 1 and so on. Since the pointer point to the last returned
             * combination we initialize pointer r - 1 to r - 2.
             */
            this.indexes = new int[r];
            for (int i = 0; i < r; ++i) {
                this.indexes[i] = i;
            }
            this.indexes[r - 1] -= 1;
        }

        /**
         * Determines whether there are more combinations to get.
         * @return True when another combination exist, otherwise false.
         */
        @Override
        public boolean hasNext() {
            return this.nextCombination <= this.numberOfCombinations;
        }

        /**
         * Gets the next combination.
         * @return The next combination.<br>
         *  A combination is an array of objects containing r elements.
         * @throws NoSuchElementException When this method is called when there are no more combinations.
         */
        @Override
        public T[] next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            computeNextCombination(this.n, this.r, this.indexes, this.combination);

            ++this.nextCombination;
            return this.combination.clone();
        }

    }

    /**
     * Iterator suitable for iterating over the combinations of a large set (n).<br>
     * A set is large when it contains more than {@link #MAX_SIZE_OF_SMALL_SET} elements.
     * This iterator uses {@link BigInteger BigIntegers} for calculations.
     * @param <T> Type of single element of a combination.
     */
    private static class LargeSetCombinationsIterator<T> implements Iterator<T[]> {

        /**
         * Set of elements.
         */
        private final T[] n;

        /**
         * Number of elements that are taken each time from n.
         */
        private final int r;

        /**
         * Indexes.
         * <br>For every r there is a pointer to n.<br>
         * At any given time the pointer point to the last combination that was
         * returned. For example: n = {a,b,c,d,e} and r=3. After the first call
         * to next the first pointer points to 'a', the second to 'b' and the third to 'c'.<br>
         * a,b,c,d,e<br>
         * 0,1,2
         */
        private final int[] indexes;

        /**
         * The combination that will be returned.
         * <br>For performance reasons the same combination (with different
         * values of course) is returned.
         */
        private final T[] combination;

        /**
         * Keeps count of the next combination.
         */
        private BigInteger nextCombination = BigInteger.ONE;

        /**
         * Contains the total number of combinations.
         */
        private final BigInteger numberOfCombinations;

        /**
         * Creates an iterator that iterates over the combinations of a large set (n).
         * @param n The set of elements.<br>
         *  The set must contain at least one element.
         * @param r The number of elements taken out of n each time.<br>
         *  r <= n and r >= 0
         */
        LargeSetCombinationsIterator(final T[] n, final int r) {
            assert n != null : NULL_SET_MESSAGE;
            assert n.length > 0 : "n=" + n.length + ", but should at least be 1";
            assert r > 0 && r <= n.length : String.format("r=%d, but should be in the range [%d,%d]", r, 1, n.length);

            this.n = n.clone();
            this.r = r;

            this.numberOfCombinations = Combinations.countNumberOfCombinations(n.length, r);

            // Each combination consists of r elements
            @SuppressWarnings("unchecked")
            final T[] tempCombination = (T[])Array.newInstance(n[0].getClass(), r);
            this.combination = tempCombination;

            /*
             * A pointer is needed for every r.
             * Initially pointer 0 points to element 0 and pointer 1 points to
             * element 1 and so on. Since the pointers point to the last
             * returned combination we initialize pointer r - 1 to r - 2.
             */
            this.indexes = new int[r];
            for (int i = 0; i < r; ++i) {
                this.indexes[i] = i;
            }
            this.indexes[r - 1] -= 1;
        }

        /**
         * Determines whether there are more combinations to get.
         * @return True when more combinations exist, otherwise false.
         */
        @Override
        public boolean hasNext() {
            return this.nextCombination.compareTo(this.numberOfCombinations) <= 0;
        }

        /**
         * Gets the next combination.
         * @return The next combination.<br>
         *  A combination is an array of objects containing r elements.
         * @throws NoSuchElementException When this method is called when there are no more combinations.
         */
        @Override
        public T[] next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            computeNextCombination(this.n, this.r, this.indexes, this.combination);

            this.nextCombination = this.nextCombination.add(BigInteger.ONE);
            return this.combination.clone();
        }

    }

    /**
     * Iterator suitable for iterating over all the combinations of the supplied set, provided that the set contains
     * {@link #MAX_SIZE_OF_SMALL_SET} elements or less.<br>
     * Note: This iterator won't return the empty combination.
     * @param <T> Type of single element of a combination.
     */
    private static class AllCombinationsIterator<T> implements Iterator<T[]> {

        /**
         * Set of elements.
         */
        private final T[] n;

        /**
         * Number of elements that are taken each time from n.
         */
        private int r = 1;

        /**
         * Iterator to which the actual iterating is delegated to.
         */
        private Iterator<T[]> iterator;

        /**
         * Creates the iterator.
         * @param n The set of elements.<br>
         *  The set must contain between 1 and {@link #MAX_SIZE_OF_SMALL_SET} elements.
         */
        AllCombinationsIterator(final T[] n) {
            assert n != null : NULL_SET_MESSAGE;
            assert n.length > 0 && n.length <= MAX_SIZE_OF_SMALL_SET
                    : String.format("n=%d, but should be in the range [1 .. %d]", n.length, MAX_SIZE_OF_SMALL_SET);

            this.n = n.clone();
            this.iterator = new SmallSetCombinationsIterator<>(n, 1);
        }

        /**
         * Determines whether there are more combinations to get.
         * @return True when more combinations exist, otherwise false.
         */
        @Override
        public boolean hasNext() {
            return this.r < this.n.length || (this.r == this.n.length && this.iterator.hasNext());
        }

        /**
         * Gets the next combination.
         * @return The next combination.<br>
         *  A combination is an array of objects containing r elements.
         * @throws NoSuchElementException When this method is called when there are no more combinations.
         */
        @Override
        public T[] next() {
            if (this.iterator.hasNext()) {
                return this.iterator.next();
            } else if (this.r < this.n.length) {
                this.iterator = new SmallSetCombinationsIterator<>(this.n, ++this.r);
                return this.iterator.next();
            } else {
                throw new NoSuchElementException();
            }
        }

    }

}
