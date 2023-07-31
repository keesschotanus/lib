/*
 * Copyright (C) 2009 Kees Schotanus
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

package com.schotanus.finance;

import com.schotanus.util.CString;

import java.math.BigInteger;
import java.util.Objects;


/**
 * Utility class to perform checks based upon some kind of modulo algorithm.
 */
public final class ModuloCheck  {

    /**
     * Modulo 10.
     */
    public static final int MAX_SINGLE_DIGIT = 9;

    /**
     * Modulo 10.
     */
    public static final int MODULO_TEN = 10;

    /**
     * Modulo 11.
     */
    public static final int MODULO_ELEVEN = 11;

    /**
     * Modulo 97.
     */
    public static final BigInteger MODULO_97 = BigInteger.valueOf(97);

    /**
     * Minimum length of a Burger Service Nummer.
     */
    private static final int MINIMUM_BURGER_SERVICE_NUMMER_LENGTH = 8;

    /**
     * Maximum length of a Burger Service Nummer.
     */
    private static final int MAXIMUM_BURGER_SERVICE_NUMMER_LENGTH = 9;

    /**
     * Private constructor prevents creation of an instance outside this class.
     */
    private ModuloCheck() {
    }

    /**
     * Determines whether the supplied input is a valid modulo 10 number.
     * <br>Note: Credit cards and some social security numbers can be validated using this method.
     * In the UK, a combination of a Sort Code and Account Number can be validated using this algorithm.<br>
     * Process the digits from right to left.
     * For an odd position: add the value of the digit to the sum of all digits.
     * For even positions: multiply digit by 2, add this value modulo 9 to the sum of all digits.
     * The resulting sum should end with a 0 digit to be correct.
     * @param input The input to check.
     * @return True when the supplied number is a valid modulo-10 number, otherwise false.
     */
    public static boolean isModulo10(final String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }

        int sum = 0;
        boolean odd = true; // First digit is at an odd position
        for (int i = input.length() - 1; i >= 0; --i) {
            final int digit = Character.digit(input.charAt(i), 10);
            sum += odd ? digit : (digit * 2) % MAX_SINGLE_DIGIT;
            odd = !odd;
        }

        return sum % MODULO_TEN == 0;
    }

    /**
     * Determines whether the supplied number is a valid modulo 11 number.
     * @param number The number to check.
     * @return True when the supplied number is a valid modulo-11 number, otherwise false.
     * @see ModuloCheck#isModulo11(long)
     */
    public static boolean isModulo11(final String number) {
        try {
            return isModulo11(Long.parseLong(number));
        } catch (final NumberFormatException exception) {
            return false;
        }
    }

    /**
     * Determines whether the supplied number is a valid modulo 11 number.<br>
     * Note: Dutch bank account numbers can be validated using this method provided that the input is 9 or 10 digits long.<br>
     * The check is performed as follows:<br>
     * Compute the sum of: every digit multiplied with its position. The result divided by 11 should be an integer &gt; 0.<br>
     * For example: 736160221<br>
     * 1*1 + 2*2 + 2*3 + 0*4 + 6*5 +1*6 +6*7 + 3*8 + 7*9 = 176<br>
     * 176 / 11 = 16 and hence is a valid modulo-11 number.
     * @param number The number to check.
     * @return True when the supplied number is a valid modulo-11 number, otherwise false.
     */
    public static boolean isModulo11(final long number) {
        long sum = 0;
        long position = 0;
        long remainingNumber = number;

        while (remainingNumber > 0) {
            final long digit = remainingNumber % 10;
            sum += ++position * digit;
            remainingNumber /= 10;
        }

        return sum > 0 && sum % MODULO_ELEVEN == 0;
    }

    /**
     * Determines whether the supplied number is a valid Dutch "burger service nummer" or BSN.<br>
     * A BSN consists of 8 or 9 digits and conforms to a kind of modulo-11 check.
     * It would be modulo-11, except for the right most digit which is subtracted from the sum, instead of being added to it.
     * This has the effect that Dutch Bank Account numbers are not valid burger service nummers.
     * @param bsn The "Burger Service Nummer" to check.
     * @return True when the supplied bsn is a valid "burger service nummer", otherwise false.
     */
    public static boolean isBurgerServiceNummer(final String bsn) {
        Objects.requireNonNull(bsn);

        boolean result = bsn.length() >= MINIMUM_BURGER_SERVICE_NUMMER_LENGTH
                && bsn.length() <= MAXIMUM_BURGER_SERVICE_NUMMER_LENGTH
                && CString.is(bsn, Character::isDigit);
        if (result) {
            long sum = 0;
            for (int i = 0; i < bsn.length() - 1; i++) {
                sum += (long)Character.digit(bsn.charAt(i), 10) * (bsn.length() - i);
            }
            sum -= Character.digit(bsn.charAt(bsn.length() - 1), 10);

            result = sum > 0 && sum % MODULO_ELEVEN == 0;
        }

        return result;
    }

    /**
     * Checks whether the supplied input adheres to the MOD 97-10 check.<br>
     * See: ISO 7064.
     * @param input The input to check for modulo-97 adherence.
     * @return True when the supplied input adheres to the Modulo-97 check, false otherwise.
     * @throws NullPointerException When the supplied input is null.
     */
    public static boolean isModulo97(final String input) {
        final BigInteger number = new BigInteger(ModuloCheck.modulo97ToNumeric(Objects.requireNonNull(input)));
        return BigInteger.ONE.equals(number.mod(MODULO_97));
    }

    /**
     * Converts letters and numbers to numbers only.<br>
     * The letter A is converted to 10, B to 11 and so on.
     * This conversion is necessary for iban (International Bank Account Numbers).
     * @param input The input to be converted to numbers.
     * @return The supplied input where all letters have been converted to numbers.
     * @throws NullPointerException When the supplied input is null.
     * @throws IllegalArgumentException When the supplied input contains other characters than: [0-9][A-Z].
     */
    public static String modulo97ToNumeric(final String input) {

        if (!Objects.requireNonNull(input).matches("([0-9A-Z])*")) {
            throw new IllegalArgumentException("Only digits and letters from A-Z are allowed but input is:" + input);
        }
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            if (Character.isLetter(input.charAt(i))) {
                result.append(input.charAt(i) - 'A' + 10);
            } else {
                result.append(input.charAt(i));
            }
        }

        return result.toString();
    }
}
