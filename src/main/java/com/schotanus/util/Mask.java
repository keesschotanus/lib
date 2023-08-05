/*
 * Copyright (C) 2011 LeanApps
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

package com.schotanus.util;


import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * Mask utility class that aids in adding mask characters to a value and removing mask characters from a value.<br>
 * Consider for example the format of the Dutch zip code. It consists of 4 digits, a space and then two upper case letters.
 * Now you don't want the user to enter the space, nor want the user to type the two upper case letters. Here is where masking
 * comes in. In this particular example we would use the mask: "#### UU".<br>
 * This reads as follows: 4 digits, followed by a space, followed by two upper case letters. When you have a value without mask
 * characters like: "3931LA", you can get the mask characters in by calling:<br>
 * <code>Mask.fromUnmaskedValue("#### UU", "3931LA");</code>.
 * This will  return "3931 LA".<br>
 * When you have a value with mask characters like: "3931 la", you can remove the mask characters by calling:<br>
 * <code>Mask.fromMaskedValue("#### UU", "3931 la");</code>. This will return "3931LA".<br>
 * It is possible to use non mask characters in your mask.
 * For example:
 * <code>Mask.fromUnmaskedValue("(#### UU)", "3931LA");</code>.
 * This will  return "(3931 LA)".<br>
 * Note: This class only supports single masks of a fixed length. This is fine for Dutch zip codes but is unusable in the UK
 * where they have several different formats to make up a zip code. The {@link ZipCodeOfCountry} class handles this problem by
 * supporting different masks that are used in turn.<br>
 * Another shortcoming of this class is that there currently is no way to use any of the mask characters as literals in your
 * mask.
 * The following table shows all possible mask characters.
 * <table summary="">
 *   <thead>
 *     <tr><th>Mask character</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>#</td><td>A digit from 0 to 9</td></tr>
 *     <tr><td>A</td><td>Alphabetic character</td></tr>
 *     <tr><td>L</td><td>Alphabetic character, converted to lower case</td></tr>
 *     <tr><td>U</td><td>Alphabetic character, converted to upper case</td></tr>
 *     <tr><td>N</td><td>Alphanumeric character</td></tr>
 *     <tr><td>?</td><td>Any character</td></tr>
 *   </tbody>
 * </table>
 */
public final class Mask {

    /**
     * Key to lookup: Value is too long for the mask.<br>
     * Arguments:
     * <ul>
     *   <li>value</li>
     *   <li>mask</li>
     * </ul>
     */
     public static final String MSG_VALUE_TOO_LONG = "valueTooLongForMask";

    /**
     * Key to lookup: Value is too short for the mask.<br>
     * Arguments:
     * <ul>
     *   <li>value</li>
     *   <li>mask</li>
     * </ul>
     */
    public static final String MSG_VALUE_TOO_SHORT = "valueTooShortForMask";

    /**
     * Key to lookup: Character is not valid for mask character.<br>
     * Arguments:
     * <ul>
     *   <li>value character</li>
     *   <li>value</li>
     *   <li>mask character</li>
     * </ul>
     */
    public static final String MSG_VALUE_NOT_VALID_FOR_MASK_CHARACTER = "characterNotValidForMaskCharacter";

    /**
     * Enumeration of mask characters.
     */
    private enum MaskCharacter {

        /**
         * Numeric character.
         */
        NUMERIC('#') {
            /**
             * Check whether the supplied input character is valid.
             * @param input The input character to check.<br>
             *  A character is valid when it is a digit.
             * @return True when valid, otherwise false.
             */
            @Override
            public boolean isValid(final char input) {
                return Character.isDigit(input);
            }
        },

        /**
         * Alphabetic character.
         */
        ALPHABETIC('A') {
            /**
             * Check whether the supplied input character is valid.<br>
             * A character is valid when it is a letter.
             * @param input The input character to check.
             * @return True when valid, otherwise false.
             */
            @Override
            public boolean isValid(final char input) {
                return Character.isLetter(input);
            }
        },

        /**
         * Character that will be converted to upper case.
         */
        UPPER('U') {
            /**
             * Check whether the supplied input character is valid.<br>
             * A character is valid when it is a letter.
             * @param input The input character to check.
             * @return True when valid, otherwise false.
             */
            @Override
            public boolean isValid(final char input) {
                return Character.isLetter(input);
            }

            /**
             * Converts the supplied input character to upper case.
             * @param input The input character to convert.
             * @return The upper case version of the supplied input character.
             */
            @Override
            public char convert(final char input) {
                return Character.toUpperCase(input);
            }

        },

        /**
         * Character that will be converted to lower case.
         */
        LOWER('L') {
            /**
             * Check whether the supplied input character is valid.<br>
             * A character is valid when it is a letter.
             * @param input The input character to check.
             * @return True when valid, otherwise false.
             */
            @Override
            public boolean isValid(final char input) {
                return Character.isLetter(input);
            }

            /**
             * Converts the supplied input character to lower case.
             * @param input The input character to convert.
             * @return The lower case version of the supplied input character.
             */
            @Override
            public char convert(final char input) {
                return Character.toLowerCase(input);
            }
        },

        /**
         * Alphanumeric character.
         */
        ALPHANUMERIC('N') {
            /**
             * Check whether the supplied input character is valid.<br>
             * A character is valid when it is a letter or a digit.
             * @param input The input character to check.
             * @return True when valid, otherwise false.
             */
            @Override
            public boolean isValid(final char input) {
                return Character.isLetter(input) || Character.isDigit(input);
            }
        },

        /**
         * Any character.
         */
        ANY('?') {
            /**
             * Check whether the supplied input character is valid.
             * @param input The input character to check.
             * @return True, always.
             */
            @Override
            public boolean isValid(final char input) {
                return true;
            }
        };

        /**
         * Presentation of this MaskCharacter.
         */
        private final char presentation;

        /**
         * Constructs this MaskCharacter from the supplied presentation.
         * @param presentation The presentation of this MaskCharacter.
         */
        MaskCharacter(final char presentation) {
            this.presentation = presentation;
        }

        /**
         * Gets the presentation of this MaskCharacter.
         * @return The presentation of this MaskCharacter.
         */
        public char getPresentation() {
            return this.presentation;
        }

        /**
         * Determines whether the supplied input is valid with respect to this MaskCharacter.
         * @param input The input to check for validity.
         * @return True when valid, otherwise false is returned.
         */
        public abstract boolean isValid(char input);

        /**
         * Converts the supplied input character.
         * @param input The input to convert.
         * @return The converted input or when no conversion applies, the input, as-is.
         */
        public char convert(final char input) {
            return input;
        }

        /**
         * Determines whether the supplied input character is a valid mask character.
         * @param input The input character to check.
         * @return True when the supplied input character is a mask character, otherwise false is returned.
         */
        public static boolean isMaskCharacter(final char input) {
            return Arrays.stream(MaskCharacter.values()).anyMatch(maskCharacter ->  maskCharacter.getPresentation() == input);
        }

        /**
         * Gets the mask character corresponding to the supplied input character.
         * @param input The input character.
         * @return The MaskCharacter corresponding to the supplied input character.
         * @throws IllegalArgumentException When the supplied input character has no corresponding mask character.
         */
        public static MaskCharacter getMaskCharacter(final char input) {
            for (final MaskCharacter maskCharacter : MaskCharacter.values()) {
                if (maskCharacter.getPresentation() == input) {
                    return maskCharacter;
                }
            }

            throw new IllegalArgumentException("The character:" + input + ", is not a valid mask character!");
        }
    }

    /**
     * Resource bundle for this class.
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(Mask.class.getName());

    /**
     * Private constructor prevents creation of an instance outside this class.
     */
    private Mask() {

    }

    /**
     * Converts the supplied masked value to a value that does not contain mask characters.<br>
     * For example: When the mask="(AAA)" and the masked value="(123)", this method would return "123".<br>
     * Note: This method still works when in the above case, the masked value contains "123".
     * That is, that it contains no mask characters.
     * @param mask The mask.
     * @param maskedValue The masked value (the value possibly containing mask characters) to unmask.
     * @return The unmasked value of the supplied masked Value, using the supplied mask.
     * @throws NullPointerException When either the supplied mask or masked value is null.
     * @throws IllegalArgumentException When the supplied mask is empty or when the supplied masked value is longer than the
     *  supplied mask.
     */
    public static String fromMaskedValue(final String mask, final String maskedValue) {
        final StringBuilder result = new StringBuilder();
        int maskedValueIndex = 0;
        for (int maskIndex = 0; maskIndex < mask.length(); ++maskIndex) {
            if (MaskCharacter.isMaskCharacter(mask.charAt(maskIndex))) {
                // Mask character, get a character from the value and check for validity
                final char valueCharacter = getCharacterFromValue(mask, maskIndex, maskedValue, maskedValueIndex++);
                result.append(valueCharacter);
            } else {
                // Non mask character, check if the value has the same non mask character
                if (maskedValueIndex < maskedValue.length() && maskedValue.charAt(maskedValueIndex) == mask.charAt(maskIndex)) {
                    ++maskedValueIndex;
                }
            }
        }

        if (maskedValueIndex != maskedValue.length()) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_VALUE_TOO_LONG, maskedValue, mask));
        }

        return result.toString();
    }

    /**
     * Converts the supplied unmasked value to a value that contains mask characters.<br>
     * For example: When the mask="(AAA)" and the unmasked value="123", this method would return "(123)".
     * When the mask=(UUU) and the unmasked value="abc", this method would return "(ABC)".
     * @param mask The mask.
     * @param unmaskedValue The unmasked value.
     * @return The masked value of the supplied unmasked value, using the supplied mask.
     * @throws NullPointerException When either the supplied mask or the supplied unmasked value is null.
     * @throws IllegalArgumentException When the supplied mask is empty or when the unmasked value is shorter than may be
     *  expected from the mask.
     */
    public static String fromUnmaskedValue(final String mask, final String unmaskedValue) {
        final StringBuilder result = new StringBuilder();
        int unmaskedValueIndex = 0;
        for (int maskIndex = 0; maskIndex < mask.length(); ++maskIndex) {
            if (MaskCharacter.isMaskCharacter(mask.charAt(maskIndex))) {
                // Mask character, get a character from the value and check for validity
                final MaskCharacter maskCharacter = MaskCharacter.getMaskCharacter(mask.charAt(maskIndex));
                final char valueCharacter = getCharacterFromValue(mask, maskIndex, unmaskedValue, unmaskedValueIndex++);
                result.append(maskCharacter.convert(valueCharacter));
            } else {
                // Non mask character, add a character from the mask.
                result.append(mask.charAt(maskIndex));

                // Check if the value has the same non mask character
                if (unmaskedValueIndex < unmaskedValue.length()
                        && unmaskedValue.charAt(unmaskedValueIndex) == mask.charAt(maskIndex)) {
                    ++unmaskedValueIndex;
                }
            }
        }

        if (unmaskedValueIndex != unmaskedValue.length()) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_VALUE_TOO_LONG, unmaskedValue, mask));
        }

        return result.toString();
    }

    /**
     * Gets the character from the supplied value at the position specified by the supplied value index.<br>
     * Depending on the mask, more specifically, on the mask character at the supplied maskIndex position, the returned
     * character may be converted (for example to upper or lower case).
     * @param mask The mask.
     * @param maskIndex The current character position within the supplied mask.
     * @param value The value.
     * @param valueIndex The current character position within the supplied value.
     * @return The character from the supplied masked value at the position specified by the supplied value index.
     * @throws IllegalArgumentException When a character in the value, does not concur with a character in the mask or when the
     *  supplied masked value is too short for the supplied mask.
     */
    private static char getCharacterFromValue(
            final String mask, final int maskIndex, final String value, final int valueIndex) {
        if (valueIndex < value.length()) {
            final char valueCharacter = value.charAt(valueIndex);
            final MaskCharacter maskCharacter = MaskCharacter.getMaskCharacter(mask.charAt(maskIndex));
            if (maskCharacter.isValid(valueCharacter)) {
                return maskCharacter.convert(valueCharacter);
            } else {
                throw new IllegalArgumentException(
                        CResourceBundle.getLocalizedMessage(resourceBundle, MSG_VALUE_NOT_VALID_FOR_MASK_CHARACTER,
                                valueCharacter, value, maskCharacter.getPresentation()));
            }
        } else {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_VALUE_TOO_SHORT, value, mask));
        }
    }

}
