package com.manulaiko.dord.launcher;

import java.io.File;

/**
 * Settings class.
 *
 * Contains the configuration for the application.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Settings
{
    /**
     * Whether we're running on debug mode or not.
     */
    public static boolean debug = false;

    /**
     * Whether we're downloading all files or not.
     */
    public static boolean downloadAll = false;

    /**
     * Whether we're just downloading XML files or not.
     */
    public static boolean downloadXML = false;

    /**
     * Whether we're just downloading SWF files or not.
     */
    public static boolean downloadSWF = false;

    /**
     * Whether we're just downloading 2D files or not.
     */
    public static boolean download2D = false;

    /**
     * Whether we're just downloading 3D files or not.
     */
    public static boolean download3D = false;

    /**
     * Whether we're just downloading image files or not.
     */
    public static boolean downloadImages = false;

    /**
     * Whether we're just downloading loadingScreen assets files or not.
     */
    public static boolean downloadLoadingScreenAssets = false;

    /**
     * Where to download the resources.
     */
    public static File path = new File("./");

    /**
     * Proxy host.
     */
    public static String proxyHost = "";

    /**
     * Proxy port.
     */
    public static int proxyPort = 0;

    /**
     * Host to download the files from.
     */
    public static String host = "test2.darkorbit.bigpoint.com";

    /**
     * Whether already downloaded files should be over writed or not (skipped).
     */
    public static boolean overwrite = false;
}
