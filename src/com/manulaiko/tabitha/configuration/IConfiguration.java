package com.manulaiko.tabitha.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Configuration interface.
 *
 * Serves as interface for all configuration object.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface IConfiguration
{
    /**
     * Parses the configuration file.
     *
     * @param path Path to the configuration file.
     *
     * @throws FileNotFoundException If configuration file does not exists.
     * @throws IOException           If couldn't read configuration file.
     */
    void parse(String path) throws FileNotFoundException, IOException;

    /**
     * Returns given configuration parameter as a short.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    short getShort(String name);

    /**
     * Returns given configuration parameter as an int.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    int getInt(String name);

    /**
     * Returns given configuration parameter as a long.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    long getLong(String name);

    /**
     * Returns given configuration parameter as a string.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    String getString(String name);

    /**
     * Returns given configuration parameter as a boolean.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    boolean getBoolean(String name);

    /**
     * Returns given configuration parameter as a float.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    float getFloat(String name);

    /**
     * Returns given configuration parameter as a double.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    double getDouble(String name);

    /**
     * Returns given configuration parameter as a byte.
     *
     * @param name Configuration parameter.
     *
     * @return Given configuration parameter value.
     */
    byte getByte(String name);
}
