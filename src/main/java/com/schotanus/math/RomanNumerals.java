/*
 * Copyright (C) 2023 Kees Schotanus
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
 * Enumeration or Roman Numerals.
 */
public enum RomanNumerals {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);

    private final int value;

    /**
     * Constructs this RomanNumeral.
     * @param value The value of this RomanNumeral.
     */
    private RomanNumerals(final int value) {
        this.value = value;
    }

    /**
     * Gets the Arabic value of this RomanNumeral.
     * @return The Arabic value of this RomanNumeral.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the Arabic value of the supplied Roman numeral.
     * @return The Arabic value of the supplied Roman numeral.
     * @throws IllegalArgumentException When the supplied numeral is not a valid Roman numeral.
     */
    public static int getValue(final char numeral) {
        return valueOf(String.valueOf(numeral)).value;
    }
}
