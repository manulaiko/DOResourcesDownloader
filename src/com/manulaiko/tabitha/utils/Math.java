package com.manulaiko.tabitha.utils;

/**
 * Math utils collection.
 *
 * Collection of different math helpers.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Math
{
    /**
     * Calculates and returns the square of value.
     *
     * @param value Value to calculate.
     *
     * @return Squared value.
     */
    public static double sqr(double value)
    {
        return java.lang.Math.pow(value, 2);
    }

    /**
     * Calculates and returns the hypotenuse of `x` and `y`.
     *
     * @param x X Value.
     * @param y Y Value.
     *
     * @return Hypotenuse of `x` and `y`.
     */
    public static double hypotenuse(double x, double y)
    {
        return java.lang.Math.sqrt(Math.sqr(x) + Math.sqr(y));
    }
}
