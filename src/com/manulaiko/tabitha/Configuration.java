package com.manulaiko.tabitha;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.manulaiko.tabitha.configuration.IConfiguration;
import com.manulaiko.tabitha.configuration.IniConfiguration;
import com.manulaiko.tabitha.configuration.PropertiesConfiguration;
import com.manulaiko.tabitha.filesystem.File;

/**
 * Configuration class.
 *
 * This class offers helpers to read/write configuration files.
 *
 * Depending on the type of configuration file you'll need to call
 * a different load method:
 *  * `.ini` files: `loadIni(path)` returns `com.manulaiko.tabitha.configuration.IniConfiguration`.
 *  * `.properties` files: `loadProperties(path)` returns `com.manulaiko.tabitha.configuration.PropertiesConfiguration`.
 *
 * Alternatively you can use the method `load` which accepts as
 * parameter the name of the configuration file, the returning object is an instance of `com.manulaiko.tabitha.configuration.IConfiguration`
 * and is instanced depending on file extension.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Configuration
{
    /**
     * Loads a configuration file.
     *
     * @param path Path to the file.
     *
     * @return Configuration object instance.
     *
     * @throws FileNotFoundException If configuration file does not exists.
     * @throws Exception             If the configuration file has an unsupported extension.
     * @throws IOException           If couldn't read configuration file.
     */
    public static IConfiguration load(String path) throws FileNotFoundException, IOException, Exception
    {
        File f = new File(path);

        IConfiguration cfg;

        if(f.extension.equals("ini")) {
            cfg = new IniConfiguration();
        } else if(f.extension.equals("properties")) {
            cfg = new PropertiesConfiguration();
        } else {
            throw new Exception(path);
        }

        cfg.parse(path);

        return cfg;
    }

    /**
     * Loads a ini configuration file.
     *
     * @return Configuration object instance.
     *
     * @throws FileNotFoundException If configuration file does not exists.
     * @throws Exception             If the configuration file has an unsupported extension.
     * @throws IOException           If couldn't read configuration file.
     */
    public static IniConfiguration loadIni(String path) throws FileNotFoundException, IOException, Exception
    {
        return (IniConfiguration)Configuration.load(path);
    }

    /**
     * Loads a properties configuration file.
     *
     * @return Configuration object instance.
     *
     * @throws FileNotFoundException If configuration file does not exists.
     * @throws Exception             If the configuration file has an unsupported extension.
     * @throws IOException           If couldn't read configuration file.
     */
    public static PropertiesConfiguration loadProperties(String path) throws FileNotFoundException, IOException, Exception
    {
        return (PropertiesConfiguration)Configuration.load(path);
    }
}
