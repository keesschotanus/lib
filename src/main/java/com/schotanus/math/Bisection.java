/*
 * Copyright (C) 2008 Kees Schotanus
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
 * Finds the roots of a function within a defined interval, using the Bisection method.<br>
 * To find the root of a function f, f should at least be continuous.<br>
 * More information on this topic can be found on <a href="http://en.wikipedia.org/wiki/Bisection_method">Wikipedia</a> or on
 * <a href="http://mathworld.wolfram.com/Bisection.html">MathWorld</a>.
 */
public final class Bisection extends RootFinder {

    /**
     * Constructs this Root finding instance from the supplied function interval.
     * @param interval The function interval.
     */
    public Bisection(final FunctionInterval interval) {
        super(interval);

        // Make sure `a` is on the left and `b` is on the right side of the interval
        if (interval.b()< interval.a()) {
            final double a = interval.a();
            interval.a(interval.b());
            interval.b(a);
        }

        // Calculate the first value of x.
        interval.x((interval.a() + interval.b()) / 2.0);
    }

    /**
     * Finds a root with an accuracy equal or better than the supplied accuracy.<br>
     * This method calculates the number of iterations that have to be performed to reach the supplied accuracy so this method
     * will never 'hang'.
     * When the desired accuracy has been reached before the calculated number of iterations have been processed, this method
     * will bail out.
     * @return A root of the function.<br>
     *  The returned root is guaranteed to be accurate to the supplied accuracy.
     * @throws AssertionError When the supplied function is null.
     */
    public double findRootUsingAccuracy(final double accuracy) {
        final int calculatedIterations = 1 + (int)((Math.log(interval.b() - interval.a()) - Math.log(accuracy)) / Math.log(2));
        for (int i = 1; i <= calculatedIterations && Math.abs(interval.fx()) > accuracy; ++i) {
            adjustInterval();
        }

        return interval.x();
    }

    /**
     * Finds a root within the supplied number of iterations.
     * @param iterations The maximum number of iterations.
     * @return A root of the function.
     */
    public double findRootUsingIterations(final int iterations) {
        for (int i = 1; i < iterations && interval.fx() != 0.0; ++i) {
            adjustInterval();
        }

        return interval.x();
    }

    /**
     * Determines whether the root is between a and x or between x and b and adjusts the interval accordingly.
     * It then computes a new value for x.
     */
    private void adjustInterval() {
        if (interval.fa() * interval.fx() > 0.0) {
            // Root is between x and b.
            interval.a(interval.x());
            interval.fa(interval.fx());
        } else {
            // Root is between a and x.
            interval.b(interval.x());
            interval.fb(interval.fx());
        }

        // Compute a new value for x
        interval.x((interval.a() + interval.b()) / 2.0);
    }

}
