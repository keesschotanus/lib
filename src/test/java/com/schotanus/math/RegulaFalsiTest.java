/*
 * Copyright (C) Jul 16, 2008 Kees Schotanus
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

import org.junit.jupiter.api.Test;

import java.util.function.DoubleFunction;

import static junit.framework.Assert.assertTrue;


/**
 * Unit tests for class {@link RegulaFalsi}.
 */
final class RegulaFalsiTest {

    /**
     * Function: x^2 - 2 = 0.<br>
     * Has a root at square root of 2.
     */
    private final DoubleFunction<Double> function = x -> x * x -2;

    /**
     * Tests {@link RegulaFalsi#findRootUsingAccuracy(FunctionInterval, double)}.
     */
    @Test
    void testComputeRootWithAccuracy() {
        final double acceptedAccuracy = 0.000001D;
        final double root = RegulaFalsi.findRootUsingAccuracy(new FunctionInterval(function, -1, 2), acceptedAccuracy);
        assertTrue(Math.abs(root - Math.sqrt(2)) <= acceptedAccuracy);
    }

    /**
     * Tests {@link RegulaFalsi#findRootUsingIterations(FunctionInterval, long)}.
     */
    @Test
    void testComputeRootWithIterations() {
        final double root = RegulaFalsi.findRootUsingIterations(new FunctionInterval(function, -1, 2), 10);
        assertTrue(Math.abs(root - Math.sqrt(2)) <= 0.000001D);
    }

}
