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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * Utility class for Mathematical functions that operate on long values.
 */
public final class CLong {

    /**
     * The maximum number for which the factorial can be calculated using a long value.
     */
    public static final int MAX_FACTORIAL = 20;

    private  static final Long[] primeNumbers = {
        2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L, 79L, 83L, 89L, 97L,
        101L, 103L, 107L, 109L, 113L, 127L, 131L, 137L, 139L, 149L, 151L, 157L, 163L, 167L, 173L, 179L, 181L, 191L, 193L, 197L,
        199L, 211L, 223L, 227L, 229L, 233L, 239L, 241L, 251L, 257L, 263L, 269L, 271L, 277L, 281L, 283L, 293L, 307L, 311L, 313L,
        317L, 331L, 337L, 347L, 349L, 353L, 359L, 367L, 373L, 379L, 383L, 389L, 397L, 401L, 409L, 419L, 421L, 431L, 433L, 439L,
        443L, 449L, 457L, 461L, 463L, 467L, 479L, 487L, 491L, 499L, 503L, 509L, 521L, 523L, 541L, 547L, 557L, 563L, 569L, 571L,
        577L, 587L, 593L, 599L, 601L, 607L, 613L, 617L, 619L, 631L, 641L, 643L, 647L, 653L, 659L, 661L, 673L, 677L, 683L, 691L,
        701L, 709L, 719L, 727L, 733L, 739L, 743L, 751L, 757L, 761L, 769L, 773L, 787L, 797L, 809L, 811L, 821L, 823L, 827L, 829L,
        839L, 853L, 857L, 859L, 863L, 877L, 881L, 883L, 887L, 907L, 911L, 919L, 929L, 937L, 941L, 947L, 953L, 967L, 971L, 977L,
        983L, 991L, 997L, 1009L, 1013L, 1019L, 1021L, 1031L, 1033L, 1039L, 1049L, 1051L, 1061L, 1063L, 1069L, 1087L, 1091L,
        1093L, 1097L, 1103L, 1109L, 1117L, 1123L, 1129L, 1151L, 1153L, 1163L, 1171L, 1181L, 1187L, 1193L, 1201L, 1213L, 1217L,
        1223L, 1229L, 1231L, 1237L, 1249L, 1259L, 1277L, 1279L, 1283L, 1289L, 1291L, 1297L, 1301L, 1303L, 1307L, 1319L, 1321L,
        1327L, 1361L, 1367L, 1373L, 1381L, 1399L, 1409L, 1423L, 1427L, 1429L, 1433L, 1439L, 1447L, 1451L, 1453L, 1459L, 1471L,
        1481L, 1483L, 1487L, 1489L, 1493L, 1499L, 1511L, 1523L, 1531L, 1543L, 1549L, 1553L, 1559L, 1567L, 1571L, 1579L, 1583L,
        1597L, 1601L, 1607L, 1609L, 1613L, 1619L, 1621L, 1627L, 1637L, 1657L, 1663L, 1667L, 1669L, 1693L, 1697L, 1699L, 1709L,
        1721L, 1723L, 1733L, 1741L, 1747L, 1753L, 1759L, 1777L, 1783L, 1787L, 1789L, 1801L, 1811L, 1823L, 1831L, 1847L, 1861L,
        1867L, 1871L, 1873L, 1877L, 1879L, 1889L, 1901L, 1907L, 1913L, 1931L, 1933L, 1949L, 1951L, 1973L, 1979L, 1987L, 1993L,
        1997L, 1999L, 2003L, 2011L, 2017L, 2027L, 2029L, 2039L, 2053L, 2063L, 2069L, 2081L, 2083L, 2087L, 2089L, 2099L, 2111L,
        2113L, 2129L, 2131L, 2137L, 2141L, 2143L, 2153L, 2161L, 2179L, 2203L, 2207L, 2213L, 2221L, 2237L, 2239L, 2243L, 2251L,
        2267L, 2269L, 2273L, 2281L, 2287L, 2293L, 2297L, 2309L, 2311L, 2333L, 2339L, 2341L, 2347L, 2351L, 2357L, 2371L, 2377L,
        2381L, 2383L, 2389L, 2393L, 2399L, 2411L, 2417L, 2423L, 2437L, 2441L, 2447L, 2459L, 2467L, 2473L, 2477L, 2503L, 2521L,
        2531L, 2539L, 2543L, 2549L, 2551L, 2557L, 2579L, 2591L, 2593L, 2609L, 2617L, 2621L, 2633L, 2647L, 2657L, 2659L, 2663L,
        2671L, 2677L, 2683L, 2687L, 2689L, 2693L, 2699L, 2707L, 2711L, 2713L, 2719L, 2729L, 2731L, 2741L, 2749L, 2753L, 2767L,
        2777L, 2789L, 2791L, 2797L, 2801L, 2803L, 2819L, 2833L, 2837L, 2843L, 2851L, 2857L, 2861L, 2879L, 2887L, 2897L, 2903L,
        2909L, 2917L, 2927L, 2939L, 2953L, 2957L, 2963L, 2969L, 2971L, 2999L, 3001L, 3011L, 3019L, 3023L, 3037L, 3041L, 3049L,
        3061L, 3067L, 3079L, 3083L, 3089L, 3109L, 3119L, 3121L, 3137L, 3163L, 3167L, 3169L, 3181L, 3187L, 3191L, 3203L, 3209L,
        3217L, 3221L, 3229L, 3251L, 3253L, 3257L, 3259L, 3271L, 3299L, 3301L, 3307L, 3313L, 3319L, 3323L, 3329L, 3331L, 3343L,
        3347L, 3359L, 3361L, 3371L, 3373L, 3389L, 3391L, 3407L, 3413L, 3433L, 3449L, 3457L, 3461L, 3463L, 3467L, 3469L, 3491L,
        3499L, 3511L, 3517L, 3527L, 3529L, 3533L, 3539L, 3541L, 3547L, 3557L, 3559L, 3571L, 3581L, 3583L, 3593L, 3607L, 3613L,
        3617L, 3623L, 3631L, 3637L, 3643L, 3659L, 3671L, 3673L, 3677L, 3691L, 3697L, 3701L, 3709L, 3719L, 3727L, 3733L, 3739L,
        3761L, 3767L, 3769L, 3779L, 3793L, 3797L, 3803L, 3821L, 3823L, 3833L, 3847L, 3851L, 3853L, 3863L, 3877L, 3881L, 3889L,
        3907L, 3911L, 3917L, 3919L, 3923L, 3929L, 3931L, 3943L, 3947L, 3967L, 3989L, 4001L, 4003L, 4007L, 4013L, 4019L, 4021L,
        4027L, 4049L, 4051L, 4057L, 4073L, 4079L, 4091L, 4093L, 4099L, 4111L, 4127L, 4129L, 4133L, 4139L, 4153L, 4157L, 4159L,
        4177L, 4201L, 4211L, 4217L, 4219L, 4229L, 4231L, 4241L, 4243L, 4253L, 4259L, 4261L, 4271L, 4273L, 4283L, 4289L, 4297L,
        4327L, 4337L, 4339L, 4349L, 4357L, 4363L, 4373L, 4391L, 4397L, 4409L, 4421L, 4423L, 4441L, 4447L, 4451L, 4457L, 4463L,
        4481L, 4483L, 4493L, 4507L, 4513L, 4517L, 4519L, 4523L, 4547L, 4549L, 4561L, 4567L, 4583L, 4591L, 4597L, 4603L, 4621L,
        4637L, 4639L, 4643L, 4649L, 4651L, 4657L, 4663L, 4673L, 4679L, 4691L, 4703L, 4721L, 4723L, 4729L, 4733L, 4751L, 4759L,
        4783L, 4787L, 4789L, 4793L, 4799L, 4801L, 4813L, 4817L, 4831L, 4861L, 4871L, 4877L, 4889L, 4903L, 4909L, 4919L, 4931L,
        4933L, 4937L, 4943L, 4951L, 4957L, 4967L, 4969L, 4973L, 4987L, 4993L, 4999L, 5003L, 5009L, 5011L, 5021L, 5023L, 5039L,
        5051L, 5059L, 5077L, 5081L, 5087L, 5099L, 5101L, 5107L, 5113L, 5119L, 5147L, 5153L, 5167L, 5171L, 5179L, 5189L, 5197L,
        5209L, 5227L, 5231L, 5233L, 5237L, 5261L, 5273L, 5279L, 5281L, 5297L, 5303L, 5309L, 5323L, 5333L, 5347L, 5351L, 5381L,
        5387L, 5393L, 5399L, 5407L, 5413L, 5417L, 5419L, 5431L, 5437L, 5441L, 5443L, 5449L, 5471L, 5477L, 5479L, 5483L, 5501L,
        5503L, 5507L, 5519L, 5521L, 5527L, 5531L, 5557L, 5563L, 5569L, 5573L, 5581L, 5591L, 5623L, 5639L, 5641L, 5647L, 5651L,
        5653L, 5657L, 5659L, 5669L, 5683L, 5689L, 5693L, 5701L, 5711L, 5717L, 5737L, 5741L, 5743L, 5749L, 5779L, 5783L, 5791L,
        5801L, 5807L, 5813L, 5821L, 5827L, 5839L, 5843L, 5849L, 5851L, 5857L, 5861L, 5867L, 5869L, 5879L, 5881L, 5897L, 5903L,
        5923L, 5927L, 5939L, 5953L, 5981L, 5987L, 6007L, 6011L, 6029L, 6037L, 6043L, 6047L, 6053L, 6067L, 6073L, 6079L, 6089L,
        6091L, 6101L, 6113L, 6121L, 6131L, 6133L, 6143L, 6151L, 6163L, 6173L, 6197L, 6199L, 6203L, 6211L, 6217L, 6221L, 6229L,
        6247L, 6257L, 6263L, 6269L, 6271L, 6277L, 6287L, 6299L, 6301L, 6311L, 6317L, 6323L, 6329L, 6337L, 6343L, 6353L, 6359L,
        6361L, 6367L, 6373L, 6379L, 6389L, 6397L, 6421L, 6427L, 6449L, 6451L, 6469L, 6473L, 6481L, 6491L, 6521L, 6529L, 6547L,
        6551L, 6553L, 6563L, 6569L, 6571L, 6577L, 6581L, 6599L, 6607L, 6619L, 6637L, 6653L, 6659L, 6661L, 6673L, 6679L, 6689L,
        6691L, 6701L, 6703L, 6709L, 6719L, 6733L, 6737L, 6761L, 6763L, 6779L, 6781L, 6791L, 6793L, 6803L, 6823L, 6827L, 6829L,
        6833L, 6841L, 6857L, 6863L, 6869L, 6871L, 6883L, 6899L, 6907L, 6911L, 6917L, 6947L, 6949L, 6959L, 6961L, 6967L, 6971L,
        6977L, 6983L, 6991L, 6997L, 7001L, 7013L, 7019L, 7027L, 7039L, 7043L, 7057L, 7069L, 7079L, 7103L, 7109L, 7121L, 7127L,
        7129L, 7151L, 7159L, 7177L, 7187L, 7193L, 7207L, 7211L, 7213L, 7219L, 7229L, 7237L, 7243L, 7247L, 7253L, 7283L, 7297L,
        7307L, 7309L, 7321L, 7331L, 7333L, 7349L, 7351L, 7369L, 7393L, 7411L, 7417L, 7433L, 7451L, 7457L, 7459L, 7477L, 7481L,
        7487L, 7489L, 7499L, 7507L, 7517L, 7523L, 7529L, 7537L, 7541L, 7547L, 7549L, 7559L, 7561L, 7573L, 7577L, 7583L, 7589L,
        7591L, 7603L, 7607L, 7621L, 7639L, 7643L, 7649L, 7669L, 7673L, 7681L, 7687L, 7691L, 7699L, 7703L, 7717L, 7723L, 7727L,
        7741L, 7753L, 7757L, 7759L, 7789L, 7793L, 7817L, 7823L, 7829L, 7841L, 7853L, 7867L, 7873L, 7877L, 7879L, 7883L, 7901L,
        7907L, 7919L
    };

    public static final Set<Long> firstThousandPrimeNumbers = new TreeSet<>(Arrays.asList(primeNumbers));

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
     * Determines if the supplied number is a prime number.<br>
     * A number is a prime if the number can only be divided by 1 and by itself.
     * By definition 0 and 1 are not prime numbers.<br>
     * You can check whether a number is a prime by dividing the number by all values from 2 up to and including the square root
     * of the number.
     * If none of these divisions results in an integer the number is a prime number.<br>
     * For small numbers (less than or equal to the last number in {@link #primeNumbers}), the result is looked up.
     * For larger numbers every odd value from 3 is considered, which is not very efficient but gets the job done.<br>
     * Did you know that for every n &gt;= 2 at least one prime number exists between n and 2 times n?
     * @param number Value to check if it is a prime number or not.
     * @return True when the supplied number is a prime, false otherwise.
     */
    public static boolean isPrime(final long number) {
        if (number <= primeNumbers[primeNumbers.length -1]) {
            return firstThousandPrimeNumbers.contains(number);
        }

        if (number < 2 || number % 2 == 0) {
            return false;
        }

        final long three = 3;
        final long root = (long)Math.sqrt(number);
        for (long i = three; i <= root; i += 2) {
            if (number % i == 0) {
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

    /**
     * Factorizes the supplied long value into its prime factors.
     * @param value The value to factorize.
     * @return A list of prime factors.
     * @throws IllegalArgumentException When the supplied value is &lt; 2.
     * @throws NullPointerException When the supplied value is null.
     */
    public static List<PrimeFactor> factorize(final long value) {
        if (value <= 1) {
            throw new IllegalArgumentException("Factorization needs a value larger than one, but is:" + value);
        }

        /*
         * Try all the prime factors from our array of prime numbers
         */
        final List<PrimeFactor> primeFactors = new ArrayList<>();
        long remainder = value;
        for (int index = 0; remainder != 1 && index < primeNumbers.length; ++index) {
            remainder = factorize(primeFactors, remainder, primeNumbers[index]);
        }

        /*
         * If we are still not done, try every odd number after the last prime number in our list
         */
        if (remainder != 1) {
            final long lastFactor = (long)Math.sqrt(value);
            for (long factor = primeNumbers[primeNumbers.length - 1] + 2; remainder != 1 && factor < lastFactor; factor += 2) {
                remainder = factorize(primeFactors, remainder, factor);
            }
        }

        /*
         * If we still have a remainder, it must be prime.
         */
        if (remainder != 1) {
            primeFactors.add(new PrimeFactor(BigInteger.valueOf(remainder), 1));
        }
        return primeFactors;
    }

    /**
     * Determines if the supplied long factor is a (prime) factor of the supplied number.<br>
     * When the supplied factor is a factor of the supplied number, a prime factor is added to the list of prime factors.
     * @param primeFactors The list of prime factors found so far.
     * @param number The number to check.
     * @param factor The factor to check whether it is a prime factor of the supplied number.
     * @return When the supplied factor is no prime factor of the supplied number then the number is returned as is,
     *  otherwise the number that remains after factoring out the supplied factor is returned.<br>
     *  For example: number=45 and factor=2.
     *  Since 2 is no factor of 45, the number 45 is returned.
     *  When the number=45 and the factor=3 then the number 45 / 3 / 3 =&gt; 5 is returned.
     */
    private static long factorize(final List<PrimeFactor> primeFactors, final long number, final long factor) {
        int exponent = 0;

        long remainder = number;
        while (remainder % factor == 0) {
            ++exponent;
            remainder /= factor;
        }

        if (exponent != 0) {
            primeFactors.add(new PrimeFactor(BigInteger.valueOf(factor), exponent));
        }

        return remainder;
    }

}
