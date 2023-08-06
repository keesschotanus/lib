/*
 * Copyright (C) 2002 Kees Schotanus
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.schotanus.util;

import java.util.function.IntPredicate;


/**
 * Collection of String related methods.
 */
public class CString {

    /**
     * Private constructor prevents creation of an instance.
     */
    private CString() {
    }

    /**
     * Determines if the supplied input String adheres to the supplied predicate.
     * <br>For example:
     * <pre><code>
     *   // Determine if a String contains alphabetic characters only
     *   boolean result = CString.is("abc", Character::isAlphabetic)
     * </code></pre>
     * @param input String to check.
     * @param predicate Predicate to apply to each character of the input
     * @return True when the input String adheres to the supplied predicate, otherwise false is returned.
     *  True is also returned when the input String is empty.
     */
    public static boolean is(final String input, final IntPredicate predicate) {
        return input.chars().allMatch(predicate);
    }

    /**
     * Determines if the supplied input String is alphanumeric.
     * @param input String to check.
     * @return True when the input String is alphanumeric, otherwise false is returned.
     *  True is also returned when the input String is empty.
     */
    public static boolean isAlphanumeric(final String input) {
        return input.chars().allMatch(singleChar -> Character.isAlphabetic(singleChar) || Character.isDigit(singleChar));
    }

    /**
     * Checks that the supplied input has matching open and close characters, where opening and closing characters may not be
     * nested.<br>
     * Example: <code></code>CString.checkMatchingCharacters("[abc]def[12]", '[', ']'); // Has matching brackets.</code><br>
     * Example: <code></code>CString.checkMatchingCharacters("[abc]def[12]", '{', '}'); // No accolades, so no error.</code><br>
     * @param input Input to check for matching opening and closing characters.
     * @param open The opening character.
     * @param close The closing character.
     * @throws IllegalArgumentException When the open and close characters do not match.
     */
    public static void checkMatchingOpenAndCloseCharacters(final String input, final char open, final char close) {
        boolean unmatchedOpeningCharFound = false;
        for (int i = 0; i < input.length(); ++i) {
            final char currentChar = input.charAt(i);
            if (currentChar == open) {
                if (unmatchedOpeningCharFound) {
                    throw new IllegalArgumentException(
                            String.format("Input: %s, has missing close character: %c", input, close));
                }

                unmatchedOpeningCharFound = true;
            } else if (currentChar == close) {
                if (!unmatchedOpeningCharFound) {
                    throw new IllegalArgumentException(
                            String.format("Input: %s, has missing open character: %c", input, open));
                }

                unmatchedOpeningCharFound = false;
            }
        }

        if (unmatchedOpeningCharFound) {
            throw new IllegalArgumentException(
                    String.format("Input: %s, has missing close character: %c", input, close));
        }
    }
}
