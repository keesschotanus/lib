/*
 * Copyright (C) 2011 Kees Schotanus
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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Abstraction of a Bank Identifier Code (BIC).<br>
 * BIC was previously known as SWIFT ID or SWIFT code.
 * For further information about BIC see:
 * <a href="http://en.wikipedia.org/wiki/ISO_9362">here</a> or
 * <a href="http://www.swift.com/products/bic_registration/bic_details">here</a>.<br>
 * Note: This class neither accepts unconnected BICs nor training BICs.
 */
public class BankIdentifierCode implements Serializable {

    /**
     * Key to lookup: BIC is null or empty.
     * <br>Arguments: None.
     */
    public static final String MSG_BIC_IS_NULL_OR_EMPTY = "bicIsNullOrEmpty";

    /**
     * Key to lookup: BIC has incorrect length.
     * <br>Arguments:
     * <ul><li>BIC</li></ul>.
     */
    public static final String MSG_BIC_HAS_INCORRECT_LENGTH = "bicHasIncorrectLength";

    /**
     * Key to lookup: BIC contains lower case letters.
     * <br>Arguments:
     * <ul><li>BIC</li></ul>.
     */
    public static final String MSG_BIC_HAS_LOWER_CASE = "bicHasLowerCaseLetters";

    /**
     * Key to lookup: BIC contains non-alphanumeric characters.
     * <br>Arguments:
     * <ul><li>BIC</li></ul>.
     */
    public static final String MSG_BIC_IS_NOT_ALPHA_NUMERIC = "bicIsNotAlphaNumeric";

    /**
     * Key to lookup: BIC contains non-alphabetic bank code.
     * <br>Arguments:
     * <ul>
     *   <li>BIC</li>
     *   <li>Bank code</li>
     * </ul>.
     */
    public static final String MSG_BIC_BANK_CODE_IS_NOT_ALPHABETIC = "bicBankCodeIsNotAlphabetic";

    /**
     * Key to lookup: BIC has incorrect country.
     * <br>Arguments:
     * <ul>
     *   <li>BIC</li>
     *   <li>Country code</li>
     * </ul>.
     */
    public static final String MSG_BIC_COUNTRY_DOES_NOT_EXIST = "bicCountryDoesNotExist";

    /**
     * Key to lookup: BIC is a test BIC.
     * <br>Arguments:
     * <ul><li>BIC</li></ul>.
     */
    public static final String MSG_BIC_IS_TEST_BIC = "bicIsTestBic";

    /**
     * Key to lookup: BIC is an unconnected BIC.
     * <br>Arguments:
     * <ul><li>BIC</li></ul>.
     */
    public static final String MSG_BIC_IS_UNCONNECTED_BIC = "bicIsUnconnectedBic";

    /**
     * Universal version identifier for this serializable class.
     */
    @Serial
    private static final long serialVersionUID = 7157315323514992382L;

    /**
     * Length of the bank code.
     */
    private static final int BANK_CODE_LENGTH = 4;

    /**
     * Length of the country code.
     */
    private static final int COUNTRY_CODE_LENGTH = 2;

    /**
     * Length of the location code.
     */
    private static final int LOCATION_CODE_LENGTH = 2;

    /**
     * The Bank Identifier Code (BIC).
     */
    private final String bic;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BankIdentifierCode.class.getName());

    /**
     * Constructs this BankIdentifierCode from the supplied bic.
     * @param bic The Bank Identifier Code (BIC).
     * @throws IllegalArgumentException When the supplied bic is not valid.
     *  <br>See: {@link #checkBankIdentifierCode(String)} for the definition of a valid BIC.
     */
    public BankIdentifierCode(final String bic) {
        checkBankIdentifierCode(bic);

        this.bic = bic;
    }

    /**
     * Checks whether the supplied bic is valid.
     * For details see: {@link #checkBankIdentifierCode(String)} .
     * @param bic The Bank Identifier Code to check.
     * @return True when the supplied bic is valid, otherwise false.
     */
    public static boolean isValidBankIdentifierCode(final String bic) {
        boolean result;
        try {
            checkBankIdentifierCode(bic);
            result = true;
        } catch (final IllegalArgumentException exception) {
            result = false;
        }

        return result;
    }

    /**
     * Checks whether the supplied bic is valid.<br>
     * A Bank Identifier Code is valid when it consists of:
     * <ol>
     *   <li>A bank code of 4 upper case letters</li>
     *   <li>An ISO-3166 country code of 2 upper case letters</li>
     *   <li>A location code of 2 alphanumeric upper case characters</li>
     * </ol>
     * Optionally a 3-letter/digit branch code may be added.
     * The absence of this code, or the presence of 'XXX' denotes the primary office.
     * @param bic The Bank Identifier Code to check.
     * @throws IllegalArgumentException When the supplied bic is not valid.
     */
    public static void checkBankIdentifierCode(final String bic) {
        checkBankIdentifierCodeLength(bic);

        // Whole BIC must be in upper case
        if (!bic.equals(bic.toUpperCase(Locale.getDefault()))) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_HAS_LOWER_CASE, bic));
        }

        // Whole BIC must be alphanumeric
        if (!CString.isAlphanumeric(bic)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_IS_NOT_ALPHA_NUMERIC, bic));
        }

        // The bank code must be alphabetic
        final String bankCode = bic.substring(0, BANK_CODE_LENGTH);
        if (!CString.is(bankCode, Character::isAlphabetic)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_BANK_CODE_IS_NOT_ALPHABETIC, bic, bankCode));
        }

        // Check for valid ISO 3166 country
        final String country = bic.substring(BANK_CODE_LENGTH, BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH);
        if (!Iso3166Country.isValidAlpha2Code(country)) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_COUNTRY_DOES_NOT_EXIST, bic, country));
        }

        checkLocationCode(bic);
    }

    /**
     * Checks the location code of the supplied bic.<br>
     * Most importantly, this method checks that the supplied bic is neither a training nor an unconnected BIC.<br>
     * It is assumed that the supplied bic has already been checked for improper length, improper case and improper characters.
     * @param bic The Bank Identifier Code with the location code to check.
     * @throws IllegalArgumentException When the supplied bic has an invalid location code.
     * @throws NullPointerException When the supplied bic is null.
     */
    private static void checkLocationCode(final String bic) {
        final String locationCode = Objects.requireNonNull(bic).substring(
                BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH, BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH + LOCATION_CODE_LENGTH);

        // Test BIC has location code ending with 0 (Test) or 1 (Unconnected).
        if ("0".equals(locationCode.substring(1))) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_IS_TEST_BIC, bic));
        }
        if ("1".equals(locationCode.substring(1))) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_IS_UNCONNECTED_BIC, bic));
        }
    }

    /**
     * Checks whether the supplied bic has a valid length.
     * @param bic The Bank Identifier Code to check for a valid length.
     * @throws IllegalArgumentException When the supplied bic is of invalid length (including null or empty).
     *  <br>A valid Bank Identifier Code either has a length of 8 or 11.
     */
    private static void checkBankIdentifierCodeLength(final String bic) {
        final int shortBankIdentifierCodeLength = 8;
        final int longBankIdentifierCodeLength = 11;

        if (bic == null || bic.trim().length() == 0) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_IS_NULL_OR_EMPTY));
        }
        if (bic.length() != shortBankIdentifierCodeLength && bic.length() != longBankIdentifierCodeLength) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_BIC_HAS_INCORRECT_LENGTH, bic));
        }
    }

    /**
     * Gets the bank code of this Bank Identifier Code.
     * @return The bank code.
     */
    public String getBankCode() {
        return this.bic.substring(0, BANK_CODE_LENGTH);
    }

    /**
     * Gets the country code of this Bank Identifier Code.
     * <br>A country is a 2-letter code (ISO-3166).
     * @return The 2-letter country code.
     */
    public String getCountryCode() {
        return this.bic.substring(BANK_CODE_LENGTH, BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH);
    }

    /**
     * Gets the location code of this Bank Identifier Code.
     * <br>A location code is a 2-letter numeric code.
     * @return The location code.
     */
    public String getLocationCode() {
        return this.bic.substring(
                BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH, BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH + LOCATION_CODE_LENGTH);
    }

    /**
     * Gets the optional branch code of this Bank Identifier Code.
     * @return The branch code or an empty String when no branch code is present.
     */
    public String getBranchCode() {
        return this.bic.length() == BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH + LOCATION_CODE_LENGTH
                ? "" : this.bic.substring(BANK_CODE_LENGTH + COUNTRY_CODE_LENGTH + LOCATION_CODE_LENGTH);
    }

    /**
     * Creates a String representation of this BIC, consisting of the BIC itself.
     * @return The Bank Identifier Code (BIC).
     */
    @Override
    public String toString() {
        return this.bic;
    }

}
