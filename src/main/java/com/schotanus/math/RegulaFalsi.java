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
 * Finds the roots of a function within a defined interval, using the Regula Falsi (False Position) method.<br>
 * To find the root of a function f, f should at least be continuous.<br>
 * More information on this topic can be found <a href="http://en.wikipedia.org/wiki/False_position">here</a>.
 */
public final class RegulaFalsi extends RootFinder {

    /**
     * Maximum number of iterations to perform when using accuracy, before bailing out.
     */
    public static final int MAX_ITERATIONS = 100;

    /**
     * Contains the sides of an interval.
     */
    private enum IntervalSide { LEFT, RIGHT }

    /**
     * Constructs this Root finding instance from the supplied function interval.
     * @param interval The function interval.
     */
    public RegulaFalsi(final FunctionInterval interval) {

        super(interval);
        // Determine the first point in the interval
        interval.x((interval.fb() * interval.a() - interval.fa() * interval.b()) / (interval.fb() - interval.fa()));
    }

    /**
     * Finds a root with an accuracy equal or better than the supplied accuracy.<br>
     * This method will not perform more than {@link #MAX_ITERATIONS} iterations to prevent this method from blocking,
     * for example when the supplied accuracy can never be reached.
     * @param accuracy The minimum accuracy.
     * @return A root of the function.<br>
     *  The returned root is guaranteed to be accurate to the supplied accuracy.
     * @throws ArithmeticException When more than {@link #MAX_ITERATIONS} are performed without finding a root.
     */
    public double findRootUsingAccuracy(final double accuracy) {
        // Used to keep track of which side of the interval was adjusted last
        IntervalSide adjustedIntervalSide = null;

        int currentIteration = 1;
        while (Math.abs(interval.fx()) >= accuracy) {
            adjustedIntervalSide = adjustInterval(interval, adjustedIntervalSide);

            if (currentIteration++ > MAX_ITERATIONS) {
                throw new ArithmeticException(
                        "Could not find root using Regula Falsi within: %d iterations".formatted(MAX_ITERATIONS));
            }
        }

        return interval.x();
    }

    /**
     * Finds a root within the supplied number of iterations.
     * @param iterations The maximum number of iterations.
     * @return A root of the function.
     */
    public double findRootUsingIterations(final long iterations) {
        // Used to keep track of which side of the interval was adjusted last
        IntervalSide adjustedIntervalSide = null;

        for (int i = 1; i <= iterations && interval.fx() != 0.0; ++i) {
            adjustedIntervalSide = adjustInterval(interval, adjustedIntervalSide);
        }

        return interval.x();
    }

    /**
     * Adjusts the supplied interval by making it smaller and then computing a new value for x.<br>
     * Determines whether the root is between a and x or between x and b and adjusts the interval accordingly.
     * The net result is a smaller interval.
     * @param interval The interval.
     * @param previouslyAdjustedSide The previously adjusted side (left or right) of the interval.
     * @return The side of the interval that has been adjusted.
     */
    private IntervalSide adjustInterval(final FunctionInterval interval, final IntervalSide previouslyAdjustedSide) {
        IntervalSide result;
        if (interval.fa() * interval.fx() > 0.0) {
            // Root is between x and b.
            interval.a(interval.x());
            interval.fa(interval.fx());
            if (previouslyAdjustedSide == IntervalSide.LEFT) {
                interval.fb(interval.fb() / 2.0);
            }
            result = IntervalSide.LEFT;
        } else {
            // Root is between a and x.
            interval.b(interval.x());
            interval.fb(interval.fx());
            if (previouslyAdjustedSide == IntervalSide.RIGHT) {
                interval.fa(interval.fa() / 2.0);
            }
            result = IntervalSide.RIGHT;
        }

        interval.x((interval.fb() * interval.a() - interval.fa() * interval.b())
                / (interval.fb() - interval.fa()));

        return result;
    }
}
