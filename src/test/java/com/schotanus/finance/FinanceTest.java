/*
 * Copyright (C) 2010 Kees Schotanus
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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Unit tests for class {@link Finance}.
 */
final class FinanceTest {

    /**
     * Start capital.
     */
    private static final double START_CAPITAL = 100.0D;

    /**
     * Interest rate.
     */
    private static final double INTEREST_RATE = 10.0D;

    /**
     * Delta (margin for error when comparing double results).
     */
    private static final double DELTA = 0.000001D;

    /**
     * Decimal interest rate.
     */
    private static final double INTEREST_FACTOR = 1.0D + INTEREST_RATE / Finance.DECIMAL_INTEREST_RATE;

    /**
     * Number of years.
     */
    private static final int YEARS = 3;

    /**
     * Tests {link {@link Finance#futureValue(double, double, int)}}
     */
    @Test
    void testFutureValueWithAnnualInterest() {
        double expected = START_CAPITAL;
        for (int years = 1; years <= YEARS; ++years) {
            expected *= INTEREST_FACTOR;
        }

        assertEquals(expected, Finance.futureValue(START_CAPITAL, INTEREST_RATE, YEARS), DELTA);

        // Test with a zero interest rate.
        assertThrows(IllegalArgumentException.class, () -> Finance.futureValue(START_CAPITAL, 0, YEARS));
    }

    /**
     * Tests {link {@link Finance#futureValue(double, double, int, double)}
     */
    @Test
    void testFutureValueWithMultipleInterestPaymentsPerYear() {
        final double biAnnualInterestFactor = 1.0D + INTEREST_RATE / 2 / Finance.DECIMAL_INTEREST_RATE;

        double expected = START_CAPITAL;
        for (int years = 1; years <= YEARS * 2; ++years) {
            expected *= biAnnualInterestFactor;
        }

        assertEquals(expected, Finance.futureValue(START_CAPITAL, INTEREST_RATE, 2, YEARS), DELTA);

        // Test with a zero interest rate.
        assertThrows(IllegalArgumentException.class, () -> Finance.futureValue(START_CAPITAL, 0, 2, YEARS));
        // Test with a zero payment periods.
        assertThrows(IllegalArgumentException.class, () -> Finance.futureValue(START_CAPITAL, INTEREST_RATE, 0, YEARS));
    }

    /**
     * Tests {@link Finance#futureValue(double, double, int)}
     */
    @Test
    void testPresentValueWithAnnualInterest() {
        double expected = START_CAPITAL;
        for (int years = 1; years <= YEARS; ++years) {
            expected /= INTEREST_FACTOR;
        }

        assertEquals(expected, Finance.presentValue(START_CAPITAL, INTEREST_RATE, YEARS), DELTA);

        // Test with a zero interest rate.
        assertThrows(IllegalArgumentException.class, () -> Finance.presentValue(START_CAPITAL, 0, YEARS));
    }

    /**
     * Tests {@link Finance#presentValue(double, double, int, double)}
     */
    @Test
    void testPresentValueWithMultipleInterestPaymentsPerYear() {
        final double biAnnualInterestFactor = 1.0D + INTEREST_RATE / 2 / Finance.DECIMAL_INTEREST_RATE;

        double expected = START_CAPITAL;
        for (int years = 1; years <= YEARS * 2; ++years) {
            expected /= biAnnualInterestFactor;
        }

        assertEquals(expected, Finance.presentValue(START_CAPITAL, INTEREST_RATE, 2, YEARS), DELTA);

        // Test with a zero interest rate.
        assertThrows(IllegalArgumentException.class, () -> Finance.presentValue(START_CAPITAL, 0, 2, YEARS));
        // Test with a zero payment periods.
        assertThrows(IllegalArgumentException.class, () -> Finance.presentValue(START_CAPITAL, INTEREST_RATE, 0, YEARS));
    }

}
