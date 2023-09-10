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


import java.util.function.DoubleFunction;


/**
 * Abstraction of an interval of a function.
 * An interval is described by a function and x values for both the left and the right side of the interval.
 */
public final class FunctionInterval {

    /**
     * The function corresponding to this interval.
     */
    private final DoubleFunction<Double> function;

    /**
     * Left side of this interval.
     */
    private double a;

    /**
     * f(a).
     */
    private double fa;

    /**
     * Right side of this interval.
     */
    private double b;

    /**
     * f(b).
     */
    private double fb;

    /**
     * Describes a point between a and b.
     */
    private double x;

    /**
     * f(x).
     */
    private double fx;

    /**
     * Creates this FunctionInterval from the supplied function and left and right sides of the interval.
     * @param function The function corresponding to this interval.
     * @param a The left side of the interval.
     * @param b The right side of the interval.
     */
    public FunctionInterval(final DoubleFunction<Double> function, final double a, final double b) {
        this.function = function;

        this.a = a;
        this.b = b;

        this.fa = function.apply(a);
        this.fb = function.apply(b);

        checkForRoot();
    }

    /**
     * Checks this interval for having a possible root so this interval can be used by a root finding method.<br>
     * This method checks that:
     * <ul>
     *   <li>a unequal to b</li>
     *   <li>fa and fb are actual numbers (not NaN)</li>
     *   <li>fa and fb have opposite signs</li>
     * </ul>
     * @throws IllegalArgumentException When this interval does not represent an interval as described by the rules above.
     */
    private  void checkForRoot() {
        if (this.a == this.b) {
            throw new IllegalArgumentException("Interval can't have zero size!");
        }

        if (Double.isNaN(this.fa)) {
            throw new IllegalArgumentException(String.format("f(%g) is Not a Number!", this.a));
        }
        if (Double.isNaN(this.fb)) {
            throw new IllegalArgumentException(String.format("f(%g) is Not a Number!", this.b));
        }

        if (Math.signum(this.fa) * Math.signum(this.fb) > 0) {
            throw new IllegalArgumentException(
                    "f(%g)=%g and f(%g)=%g. They should have opposite signs!".formatted(this.a, this.fa, this.b, this.fb));
        }
    }

    /**
     * Gets the left side of this interval.
     * @return The left side of this interval.
     */
    public double a() {
        return this.a;
    }

    /**
     * Sets the left side of this interval.
     * @param a The left side of this interval.
     */
    public void a(final double a) {
        this.a = a;
    }

    /**
     * Gets f(a).
     * @return f(a).
     */
    public double fa() {
        return this.fa;
    }

    /**
     * Sets f(a).
     * @param fa f(a).
     */
    public void fa(final double fa) {
        this.fa = fa;
    }

    /**
     * Gets the right side of this interval.
     * @return The right side of this interval.
     */
    public double b() {
        return this.b;
    }

    /**
     * Sets the right side of this interval.
     * @param b The right side of this interval.
     */
    public void b(final double b) {
        this.b = b;
    }

    /**
     * Gets f(b).
     * @return f(b).
     */
    public double fb() {
        return this.fb;
    }

    /**
     * Sets f(b).
     * @param fb f(b).
     */
    public void fb(final double fb) {
        this.fb = fb;
    }

    /**
     * Gets the point between a and b.
     * @return The point between a and b.
     */
    public double x() {
        return this.x;
    }

    /**
     * Sets a value x between a and b and calculates f(x).
     * @param x A value between a and b.
     * Note: No check is made whether x is actually between a and b.
     */
    public void x(final double x) {
        this.x = x;
        this.fx = function.apply(x);
    }

    /**
     * Gets f(x).
     * @return f(x).
     */
    public double fx() {
        return this.fx;
    }

    /**
     * Sets f(x).
     * @param fx f(x).
     */
    public void fx(final double fx) {
        this.fx = fx;
    }
}
