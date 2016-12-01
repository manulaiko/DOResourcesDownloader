package com.manulaiko.tabitha.utils;

import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Contains random methods and objects.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Tools
{
    /**
     * Scanner object.
     *
     * Used to read console's input.
     */
    public static Scanner in = new Scanner(System.in);

    /**
     * Random object.
     *
     * Used to generate random numbers.
     */
    public static Random r = new Random() {
        /**
         * Returns a random int between min and max.
         *
         * @param min Minimum random int value.
         * @param max Maximum random int value.
         *
         * @return Random int between min and max.
         */
        public int nextInt(int min, int max)
        {
            return ThreadLocalRandom.current()
                                    .nextInt(min, max + 1);
        }

        /**
         * Returns a random long between min and max.
         *
         * @param min Minimum random long value.
         * @param max Maximum random long value.
         *
         * @return Random long between min and max.
         */
        public long nextLong(long min, long max)
        {
            return ThreadLocalRandom.current()
                                    .nextLong(min, max + 1);
        }

        /**
         * Returns a random double between min and max.
         *
         * @param min Minimum random double value.
         * @param max Maximum random double value.
         *
         * @return Random double between min and max.
         */
        public double nextDouble(double min, double max)
        {
            return ThreadLocalRandom.current()
                                    .nextDouble(min, max + 1);
        }
    };
}
