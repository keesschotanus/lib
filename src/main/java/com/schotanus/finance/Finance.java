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


import com.schotanus.util.CResourceBundle;

import java.util.ResourceBundle;


/**
 * Utility class for financial calculations.
 */
public final class Finance {

    /**
     * Constant to compute the decimal interest rate (perunage in Dutch) from a percentage.<br>
     * For example, when the interest rate is 5% then the decimal interest  rate is: 5 / 100 =&gt; 0.05
     */
    public static final double DECIMAL_INTEREST_RATE = 100.0D;

    /**
     * Key to lookup: Number of payment periods must be positive
     * <br>Arguments: None.
     */
    public static final String MSG_INTEREST_PAYMENT_PERIODS_MUST_BE_POSITIVE = "numberOfInterestPaymentPeriodsMustBePositive";

    /**
     * Key to lookup: Interest rate is zero
     * <br>Arguments: None.
     */
    public static final String MSG_INTEREST_RATE_IS_ZERO = "interestRateIsZero";

    /**
     * Resource bundle for this class.
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(Finance.class.getName());

    /**
     * Private constructor prevents creation of an instance outside this class.
     */
    private Finance() {

    }

    /**
     * Computes the future value of a starting capital (present value) for which compound interest will be received annually.
     * <br>For example: A present value of $1000.- and an interest rate of 7% will result in a future value of $1007.-
     * after one year and after ten years the future value is roughly twice the present value ($1967.15).<br>
     * The mathematical formula for calculating the future value is:<br>
     * fv = pv(1 + i / 100)<sup>years</sup><br>
     * Where fv=Future Value, pv=Present Value and i=interest rate.
     * @param presentValue The present value.
     * @param interest The interest percentage per year.<br>
     *  Supply 5.0 for an interest rate of 5%.
     * @param years The number of years.
     * @return The future value.
     * @throws IllegalArgumentException When the interest rate is zero.
     */
    public static double futureValue(final double presentValue, final double interest, final int years) {
        if (interest == 0) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_INTEREST_RATE_IS_ZERO));
        }
        return futureValue(presentValue, interest, 1, years);
    }

    /**
     * Computes the future value of a starting capital (present value) for which compound interest will be received for the
     * supplied number of interest payments per period.<br>
     * For example: What is the future value of a present value of $1000.- with a quarterly interest rate of 7%, after a year
     * and a half?
     * <pre><code>
     *   Financial.futureValue(1000.0D, 7.0D, 4, 1.5);
     * </code></pre>
     * The result is approximately: $1109.70<br>
     * The mathematical formula for calculating the future value is:<br>
     * fv = pv(1 + i / 100 / n)<sup>periods*n</sup><br>
     * Where fv=Future Value, pv=Present Value, i=interest rate and n=number of interest payments per period.
     * @param presentValue The present value.
     * @param interest The interest rate per period.<br>
     *  Supply 5.0 for an interest rate of 5%.
     * @param interestPaymentsPerPeriod The number of times interest is paid per period.<br>
     *  Interest is always assumed to be paid at the end of the interest period.
     *  Note: In a period of a year the interest may be paid quarterly for example.
     *  This makes each interest period a quarter.
     * @param periods The number of periods.
     * @return The future value.
     * @throws IllegalArgumentException When the interest rate is zero or when the number of periods is less than one.
     */
    public static double futureValue(
            final double presentValue,
            final double interest,
            final int interestPaymentsPerPeriod,
            final double periods) {
        if (interest == 0) {
            throw new IllegalArgumentException(CResourceBundle.getLocalizedMessage(resourceBundle, MSG_INTEREST_RATE_IS_ZERO));
        }
        if (interestPaymentsPerPeriod < 1) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_INTEREST_PAYMENT_PERIODS_MUST_BE_POSITIVE));
        }

        return presentValue * Math.pow(1 + interest / DECIMAL_INTEREST_RATE / interestPaymentsPerPeriod,
                interestPaymentsPerPeriod * periods);
    }

    /**
     * Computes the present value of a future capital (future value) for which compound interest will be received annually.<br>
     * For example: A value of $1007.-, one year in the future and an interest rate of 7% will result in a present value of
     * $1000.-.
     * Ten years in the future, the present value is roughly half the future value ($508.35).<br>
     * The mathematical formula for calculating the present value is:<br>
     * pv = fv / (1 + i / 100)<sup>years</sup><br>
     * Where pv=Present Value, fv=Future Value and i=interest rate.
     * @param futureValue The future value.
     * @param interest The interest rate per year.
     *  <br>Supply 5.0 for an interest rate of 5%.
     * @param years The number of years.
     * @return The present value.
     * @throws IllegalArgumentException When the interest rate is zero.
     */
    public static double presentValue(final double futureValue, final double interest, final int years) {
        if (interest == 0) {
            throw new IllegalArgumentException(CResourceBundle.getLocalizedMessage(resourceBundle, MSG_INTEREST_RATE_IS_ZERO));
        }
        return presentValue(futureValue, interest, 1, years);
    }

    /**
     * Computes the present value of a future capital (future value) for which
     * compound interest will be received interestPaymentsPerPeriod times per
     * period for the duration of the supplied periods.
     * <br>For example: What is the present value of a value of $1000.-, one
     * year and a half in the future when the interest rate is 7%.
     * <pre><code>
     *   Financial.presentValue(1000.0D, 7.0D, 4, 1.5);
     * </code></pre>Which is approximately: $901.14<br>
     * <br>The mathematical formula for calculating the present value is:<br>
     * pv = fv / (1 + i / 100 / n)<sup>periods*n</sup><br>
     * Where pv=Present Value, fv=Future Value, i=interest rate and n=number of interest payments per period.
     * @param futureValue The future value.
     * @param interest The interest percentage per period.<br>
     *  Supply 5.0 for an interest rate of 5%.
     * @param interestPaymentsPerPeriod The number of times interest is paid per period.<br>
     *  Interest is always assumed to be paid at the end of the interest period.
     *  Note: In a period of a year the interest may be paid quarterly for example.
     *  This makes the interest period a quarter.
     * @param periods The number of periods.
     * @return The future value.
     * @throws IllegalArgumentException When the interest rate is zero or when the number of periods is less than one.
     */
    public static double presentValue(
            final double futureValue,
            final double interest, final int interestPaymentsPerPeriod,
            final double periods) {
        if (interest == 0) {
            throw new IllegalArgumentException(CResourceBundle.getLocalizedMessage(resourceBundle, MSG_INTEREST_RATE_IS_ZERO));
        }
        if (interestPaymentsPerPeriod < 1) {
            throw new IllegalArgumentException(
                    CResourceBundle.getLocalizedMessage(resourceBundle, MSG_INTEREST_PAYMENT_PERIODS_MUST_BE_POSITIVE));
        }

        return futureValue / Math.pow(1 + interest / DECIMAL_INTEREST_RATE / interestPaymentsPerPeriod,
                periods * interestPaymentsPerPeriod);
    }

}
