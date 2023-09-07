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
 * Utility class for the conversion of Roman numbers to Arabic numbers and vice versa.<br>
 * Romans used the following numerals with corresponding values:<br>
 * I=1, V=5, X=10, L=50, C=100, D=500, M=1000.<br>
 * All Roman numbers should conform to the following rules:<br>
 * 1 ) Roman numbers should be read and written from left to right.<br>
 * 2 ) Numerals with a value equal to a power of ten may be repeated up to three times.<br>
 * 3 ) Numerals with a value unequal to a power of ten may occur only once.<br>
 * 4 ) Unless all the conditions under sub 5 are met, numerals with a lower value follow numerals with higher values.
 *     Conversion to Arabic numbers in this case is simple, just add the value of all Roman numerals.<br>
 * 5 ) Succeeding a numeral x, with a numeral y with a higher value than the value of x is legal when all the conditions under
 *     5a/5d are met.<br>
 *     Conversion to Arabic is easy, just subtract the value of x from y.
 *     Example: IX =&gt; 10 - 1 = 9.<br>
 * 5a) The value of x must be a power of ten (I, X, C, M).<br>
 * 5b) The value of x must be 1/5 or 1/10 the value of y.<br>
 * 5c) If another numeral z follows numeral y, then the value of z must be smaller than the value of x.<br>
 * 5d) x must either be the first numeral in the expression, or x must be preceded by a numeral of at least ten times the value
 *     of x.<br><br>
 * The following Roman numbers are illegal.<br>
 * IIX  Violates rule 5d, use VIII to specify 8<br>
 * VM   Violates rules 5a and 5b, use CMXCV to specify 995<br>
 * LXL  Violates rules 3 and 5b, use XC to specify 90<br>
 */
public final class RomanNumbers {

    /**
     * The largest number the Romans could represent (or I can represent with this code).
     */
    public static final int LARGEST_ROMAN_NUMBER = 3999;


    /**
     * Conversion table to convert an Arabic digit raised to a power of 10, to a Roman numeral.<br>
     * To convert the Arabic digit 7 * 10^0 use romanNumerals[0][7].<br>
     * To convert the Arabic digit 8 * 10^2 use romanNumerals[2][8].<br>
     * Note that the Romans did not have a zero value, hence the empty Strings.
     */
    private static final String[][] ROMAN_NUMERALS = {
            {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
            {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
            {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
            {"", "M", "MM", "MMM"}
    };

    /**
     * Private constructor prevents the creation of an instance outside this class.
     */
    private RomanNumbers() {
    }

    /**
     * Converts an Arabic number to a Roman number.<br>
     * Converting from Arabic to Roman is a matter of looking up and concatenating the Roman numerals for each Arabic digit.
     * @param number Arabic number to convert.
     * @return Roman number.
     * @throws IllegalArgumentException When the Arabic number is not in the range:
     *  0 &lt;= number &lt;= {@link #LARGEST_ROMAN_NUMBER}.
     */
    public static String toRoman(final int number) {
        if (number < 0 || number > LARGEST_ROMAN_NUMBER) {
            throw new IllegalArgumentException(
                    "number is:%d, but should be in the range [0,%d]".formatted(number, LARGEST_ROMAN_NUMBER));
        }

        final String input = String.valueOf(number);
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            final int powerOfTen = input.length() - i - 1;
            final int digitValue = input.charAt(i) - '0';
            result.append(ROMAN_NUMERALS[powerOfTen][digitValue]);
        }

        return result.toString();
    }

    /**
     * Converts a Roman number to an Arabic number.<br>
     * Conversion from Roman to Arabic is simple, just add the value of each Roman numeral to the result.
     * There is one exception to this rule: if the value of the current Roman numeral is less than the value of the next
     * Roman numeral, then the value of the current Roman numeral should be subtracted from the result.
     * @param number Roman number to convert.
     * @return Arabic number.
     * @throws IllegalArgumentException When the Roman number contains numerals that are not legal Roman numerals.
     * @throws NullPointerException When the supplied number is null.
     */
    public static int toArabic(final String number) {
        int result = 0;
        for (int i = 0; i < number.length(); i++) {
            final int value = RomanNumerals.getValue(number.charAt(i));
            if (i != number.length() - 1) {
                /*
                 * The current symbol is not the last symbol.
                 * Get the value of the next Roman symbol to determine if we should add to, or subtract from, the result.
                 */
                final int nextValue = RomanNumerals.getValue(number.charAt(i + 1));
                if (value < nextValue) {
                    result -= value;
                } else {
                    result += value;
                }
            } else {
                // Add value of last Roman numeral.
                result += value;
            }
        }

        return result;
    }

    /**
     * Determines whether a Roman number is a valid Roman number or not.<br>
     * Checking whether a Roman number is valid or not can be quite tedious when you look at all the rules a Roman number should
     * adhere to.
     * Fortunately there is an easy way which automatically takes all these rules into account.
     * Convert the supplied Roman number to an Arabic number, then convert this number back into a Roman number and verify the
     * result against the passed in roman number.
     * If the values match, a legal Roman number was supplied!
     * @param romanNumber Valid or invalid Roman number.
     * @return True when the supplied Roman number is valid, false otherwise.
     * @throws NullPointerException When the supplied romanNumber is null.
     */
    public static boolean isValid(final String romanNumber) {
        boolean isValid;
        try {
            isValid = romanNumber.toUpperCase().equals(toRoman(toArabic(romanNumber)));
        } catch (final IllegalArgumentException illegalArgumentException) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Command line version of converting Roman numbers to Arabic numbers and v.v.<br>
     * Usage: RomanNumbers [Arabic_number|Roman_number]<br>
     * @param args Exactly one argument should be supplied.
     *  It should either be an Arabic number or a Roman number.
     */
    public static void main(final String[] args) {
        if (args.length == 1) {
            try {
                // Assume Arabic number has been entered.
                final int arabic = Integer.parseInt(args[0]);
                System.out.println(toRoman(arabic));
            } catch (final NumberFormatException numberFormatException) {
                // Assume Roman number has been entered.
                try {
                    System.out.println(toArabic(args[0]));
                    if (!isValid(args[0])) {
                        System.out.printf("%s is not a valid Roman number, did you mean %s?%n",
                                args[0], toRoman(toArabic(args[0])));
                    }
                } catch (final IllegalArgumentException illegalArgumentException) {
                    System.out.println(args[0] + " is neither a Roman nor an Arabic number!");
                }
            }
        } else {
            System.out.println("Usage: RomanNumbers [Arabic|Roman]");
        }
    }

}
