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

package com.schotanus.finance;

import com.schotanus.util.CResourceBundle;
import com.schotanus.util.CString;
import com.schotanus.util.Iso3166Country;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Abstraction of an IBAN (International Bank Account Number).<br>
 * For further information about IBAN see: <a href="http://www.ecbs.org/iban.htm">ECBS</a>.
 */
public class InternationalBankAccountNumber implements Serializable {

    /**
     * Key to lookup: The BBAN is mandatory.<br>
     * Arguments: <ul><li>BBAN</li></ul>.
     */
    public static final String MSG_BBAN_IS_MANDATORY = "bbanIsMandatory";

    /**
     * Key to lookup: The length of the BBAN is too long.<br>
     * Arguments: <ul><li>BBAN</li></ul>.
     */
    public static final String MSG_BBAN_IS_TOO_LONG = "bbanIsTooLong";

    /**
     * Key to lookup: The BBAN's format is incorrect, should contain alphanumeric characters only.<br>
     * Arguments: <ul><li>BBAN</li></ul>.
     */
    public static final String MSG_BBAN_FORMAT_IS_INCORRECT = "bbanFormatIsIncorrect";

    /**
     * Key to lookup: The country is mandatory.<br>
     * Arguments: <ul><li>Country</li></ul>.
     */
    public static final String MSG_COUNTRY_IS_MANDATORY = "countryIsMandatory";

    /**
     * Key to lookup: The country's length is incorrect.<br>
     * Arguments: <ul><li>Country</li></ul>.
     */
    public static final String MSG_COUNTRY_LENGTH_IS_INCORRECT = "countryLengthIsIncorrect";

    /**
     * Key to lookup: The country's format (2 capital letters) is incorrect.<br>
     * Arguments: <ul><li>Country</li></ul>.
     */
    public static final String MSG_COUNTRY_FORMAT_IS_INCORRECT = "countryFormatIsIncorrect";

    /**
     * Key to lookup: The country does not exist.<br>
     * Arguments: <ul><li>Country</li></ul>.
     */
    public static final String MSG_COUNTRY_DOES_NOT_EXIST = "countryDoesNotExist";


    /**
     * Key to lookup: IBAN is null or empty.<br>
     * Arguments: None.
     */
    public static final String MSG_IBAN_IS_NULL_OR_EMPTY = "ibanIsNullOrEmpty";

    /**
     * Key to lookup: IBAN is too short.<br>
     * Arguments: <ul><li>IBAN</li></ul>.
     */
    public static final String MSG_IBAN_IS_TOO_SHORT = "ibanIsTooShort";

    /**
     * Key to lookup: IBAN is too long.<br>
     * Arguments: <ul><li>IBAN</li></ul>.
     */
    public static final String MSG_IBAN_IS_TOO_LONG = "ibanIsTooLong";

    /**
     * Key to lookup: IBAN contains lower case letters.<br>
     * Arguments: <ul><li>IBAN</li></ul>.
     */
    public static final String MSG_IBAN_HAS_LOWER_CASE = "ibanHasLowerCaseLetters";

    /**
     * Key to lookup: IBAN contains non-alphanumeric characters.<br>
     * Arguments: <ul><li>IBAN</li></ul>.
     */
    public static final String MSG_IBAN_IS_NOT_ALPHA_NUMERIC = "ibanIsNotAlphaNumeric";

    /**
     * Key to lookup: IBAN has incorrect country.<br>
     * Arguments:
     * <ul>
     *   <li>IBAN</li>
     *   <li>Country</li>
     * </ul>.
     */
    public static final String MSG_IBAN_COUNTRY_DOES_NOT_EXIST = "ibanCountryDoesNotExist";

    /**
     * Key to lookup: IBAN has non-numeric check digits.<br>
     * Arguments:
     * <ul>
     *   <li>IBAN</li>
     *   <li>Check digits</li>
     * </ul>.
     */
    public static final String MSG_IBAN_CHECK_DIGITS_ARE_NOT_NUMERIC = "ibanCheckDigitsAreNotNumeric";

    /**
     * Key to lookup: IBAN has illegal check digits.<br>
     * Due to Modula-97 check the check digits can't ever be 00, 01 or 99.<br>
     * Arguments:
     * <ul>
     *   <li>IBAN</li>
     *   <li>Check digits</li>
     * </ul>.
     */
    public static final String MSG_IBAN_CHECK_DIGITS_ARE_ILLEGAL = "ibanCheckDigitsAreIllegal";

    /**
     * Key to lookup: IBAN does not pass modulo 97 check.<br>
     * Arguments: <ul><li>IBAN</li></ul>.
     */
    public static final String MSG_IBAN_NOT_MODULO_97 = "ibanNotModulo97";

    /**
     * Universal version identifier for this serializable class.
     */
    @Serial
    private static final long serialVersionUID = 3320663346072737379L;

    /**
     * To perform modulo 97 check.
     */
    private static final BigInteger NINETY_EIGHT = BigInteger.valueOf(98L);

    /**
     * Resource bundle for this class.
     */
    private static final ResourceBundle resourceBundle =
            ResourceBundle.getBundle(InternationalBankAccountNumber.class.getName());


    /**
     * The IBAN (International Bank Account Number).
     */
    private final String iban;


    /**
     * Constructs an IBAN object from the supplied iban.
     * @param iban An International Bank Account Number.<br>
     *  The iban should be in "electronic form", that is, it should not contain any separators.
     * @throws IllegalArgumentException When the supplied iban is invalid.<br>
     *  See: {@link #checkIban(String)} for the definition of a valid IBAN.
     */
    public InternationalBankAccountNumber(final String iban) {
        checkIban(iban);

        this.iban = iban;
    }

    /**
     * Constructs an IBAN object from the supplied country and bban (Basic Bank Account Number).<br>
     * This constructor automatically computes the check digits.
     * @param country Two letter ISO-3166 country code.
     * @param bban Basic Bank Account Number.
     * @throws IllegalArgumentException When the supplied country or the supplied bban are not valid,
     *  or when the combination does not form a valid IBAN.<br>
     *  See: {@link #checkIban(String)} for the definition of a valid IBAN.
     */
    public InternationalBankAccountNumber(final String country, final String bban) {
        checkCountry(country);
        checkBasicBankAccountNumber(bban);

        // Calculate check digits
        BigInteger checkDigits =  new BigInteger(ModuloCheck.modulo97ToNumeric(bban + country + "00"));
        checkDigits = NINETY_EIGHT.subtract(checkDigits.mod(ModuloCheck.MODULO_97));
        String checkDigitsString = checkDigits.toString();
        if (checkDigitsString.length() < 2) {
            checkDigitsString = "0" + checkDigitsString;
        }

        this.iban = country + checkDigitsString + bban;
    }


    /**
     * Gets the country of this IBAN.<br>
     * A country is a 2-letter code (ISO-3166).
     * @return The country.
     */
    public String getCountry() {
        return this.iban.substring(0, 2);
    }

    /**
     * Gets the check digits of this IBAN.<br>
     * The check digits consist of a string containing 2 digits.
     * @return The check digits.
     */
    public String getCheckDigits() {
        final int startPosition = 2;
        final int endPosition = 4;

        return this.iban.substring(startPosition, endPosition);
    }

    /**
     * Gets the Basic Bank Account Number (BBAN) of this IBAN.<br>
     * The BBAN's format is different from country to country.
     * The length of the BBAN per country however is fixed.
     * In the Netherlands the BBAN consists of 4 letters for the bank and 9 digits (zero filled) for the bank account.
     * @return The Basic Bank Account Number.
     */
    public String getBasicBankAccountNumber() {
        final int startPosition = 4;

        return this.iban.substring(startPosition);
    }

    /**
     * Checks whether the supplied iban is valid.<br>
     * For details see: {@link #checkIban(String)}.
     * @param iban The iban to check.
     * @return True when the supplied iban is valid, otherwise false.
     */
    public static boolean isValidIban(final String iban) {

        try {
            checkIban(iban);
            return true;
        } catch (final IllegalArgumentException exception) {
            return false;
        }

    }

    /**
     * Checks whether the supplied iban is valid.<br>
     * An iban (International Bank Account Number) consists of:<br>
     * ISO-3166 2-letter (upper case) country code<br>
     * 2 numeric check digits<br>
     * Up to 30 alphanumeric bban (Basic Bank Account Number) which has a fixed length that differs per country<br>
     * Currently all possible validations but the check on bban length are being performed on the supplied iban.
     * @param iban The iban to check.
     * @throws IllegalArgumentException When the supplied iban is not valid.
     */
    public static void checkIban(final String iban) {
        checkIbanLength(iban);

        if (!iban.equals(iban.toUpperCase(Locale.getDefault()))) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_HAS_LOWER_CASE, iban));
        }
        if (!CString.isAlphanumeric(iban)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_IS_NOT_ALPHA_NUMERIC, iban));
        }

        // Check for valid ISO-3166 country
        final String country = iban.substring(0, 2);
        if (!Iso3166Country.isValidAlpha2Code(country)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_COUNTRY_DOES_NOT_EXIST, iban, country));
        }

        // Check for numeric and valid check digits
        final String checkDigits = iban.substring(2, 4);
        if (!CString.is(checkDigits, Character::isDigit)) {
            throw new IllegalArgumentException(CResourceBundle.getLocalizedMessage(
                    resourceBundle, MSG_IBAN_CHECK_DIGITS_ARE_NOT_NUMERIC, iban, checkDigits));
        }
        if ("00".equals(checkDigits) || "01".equals(checkDigits) || "99".equals(checkDigits)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_CHECK_DIGITS_ARE_ILLEGAL, iban, checkDigits));
        }

        // Check for MOD 97-10 check
        final String basicBankAccountNumber = iban.substring(4);
        if (!ModuloCheck.isModulo97(basicBankAccountNumber + country + checkDigits)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_NOT_MODULO_97, iban));
        }
    }

    /**
     * Checks whether the supplied iban has a valid length.
     * @param iban The iban to check for a valid length.
     * @throws IllegalArgumentException When the supplied iban is of invalid length (including null or empty).<br>
     *  A valid iban has a length between 6 and 34 (inclusive).
     */
    private static void checkIbanLength(final String iban) {
        final int minIbanLength = 6;
        final int maxIbanLength = 34;

        if (iban == null || iban.trim().length() == 0) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_IS_NULL_OR_EMPTY));
        }
        if (iban.length() < minIbanLength) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_IS_TOO_SHORT));
        }
        if (iban.length() > maxIbanLength) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_IBAN_IS_TOO_LONG));
        }
    }

    /**
     * Checks that the supplied country is valid.<br>
     * A country is valid when it is a valid ISO-3166 country code, which consists of 2 upper-case letters.
     * @param country The country to check.
     * @throws IllegalArgumentException When the supplied country is not a valid ISO-3166 country code.
     */
    public static void checkCountry(final String country) {
        if (country == null || country.trim().length() == 0) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_COUNTRY_IS_MANDATORY, country));
        } else if (country.length() != 2) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_COUNTRY_LENGTH_IS_INCORRECT, country));
        } else if (!CString.is(country, Character::isAlphabetic) || !country.equals(country.toUpperCase(Locale.getDefault()))) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_COUNTRY_FORMAT_IS_INCORRECT, country));
        } else if (!Iso3166Country.isValidAlpha2Code(country)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_COUNTRY_DOES_NOT_EXIST, country));
        }
    }

    /**
     * Checks that the supplied bban (Basic Bank Account Number) is valid.<br>
     * A bban is valid when the following conditions are met:
     * <ul>
     *   <li>non null</li>
     *   <li>non empty</li>
     *   <li>alpha-numeric</li>
     *   <li>Not longer than 30 characters</li>
     * </ul>
     * @param bban The Basic Bank Account Number to check.
     * @throws IllegalArgumentException When the supplied bban is invalid.
     */
    public static void checkBasicBankAccountNumber(final String bban) {
        final int maxBbanLength = 30;

        if (bban == null || bban.trim().length() == 0) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BBAN_IS_MANDATORY, bban));
        } else if (bban.length() > maxBbanLength) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BBAN_IS_TOO_LONG, bban));
        } else if (!CString.isAlphanumeric(bban) || !bban.equals(bban.toUpperCase(Locale.getDefault()))) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BBAN_FORMAT_IS_INCORRECT, bban));
        }
    }

    /**
     * Creates the paper format of this IBAN.<br>
     * The paper format is created by adding a space to every fourth character of the electronic form.
     * @return Paper format of this IBAN.
     */
    public String toPaperFormat() {
        final int spaceAfter = 4; // Space after four characters

        final StringBuilder paperFormat = new StringBuilder(this.iban);
        for (int i = spaceAfter; i < paperFormat.length(); i += spaceAfter + 1) {
            paperFormat.insert(i, ' ');
        }

        return paperFormat.toString();
    }

    /**
     * Creates a String representation of this IBAN consisting of the IBAN itself.
     * @return The IBAN.
     */
    @Override
    public String toString() {
        return this.iban;
    }
}
