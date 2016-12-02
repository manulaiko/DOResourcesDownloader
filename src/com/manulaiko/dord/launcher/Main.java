package com.manulaiko.dord.launcher;

import java.io.File;
import java.util.regex.Pattern;

import com.manulaiko.dord.launcher.downloader.Downloader;
import com.manulaiko.tabitha.Console;

/**
 * Main class.
 *
 * Application's entry point.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Main
{
    /**
     * Application version.
     */
    public static final String version = "1.0.1";

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
        Main._parseArguments(args);

        Console.println("DarkOrbit Resources Downloader "+ version +" by Manulaiko");
        Console.println(Console.LINE_EQ);

        Main.printDebugInfo();

        Downloader d = new Downloader(Settings.path);
        d.start();
    }

    /**
     * Parses command line arguments.
     *
     * @param args Command line arguments.
     */
    private static void _parseArguments(String[] args)
    {
        boolean nextArgIsHost = false;

        for(String arg : args) {
            if(nextArgIsHost) {
                nextArgIsHost = false;

                if(!arg.startsWith("-")) {
                    Settings.host = arg;
                }

                continue;
            }

            switch(arg.toLowerCase())
            {
                case "-d":
                case "--debug":
                    Settings.debug = true;
                    Console.debug  = true;
                    Console.debug("Running in debug mode!");

                    break;

                case "-a":
                case "--all":
                    Settings.downloadAll = true;

                    break;

                case "-x":
                case "--xml":
                    Settings.downloadXML = true;

                    break;

                case "-s":
                case "--swf":
                    Settings.downloadSWF = true;

                    break;

                case "-2":
                case "--2d":
                    Settings.download2D = true;

                    break;

                case "-3":
                case "--3d":
                    Settings.download3D = true;

                    break;

                case "-i":
                case "--img":
                    Settings.downloadImages = true;

                    break;

                case "-l":
                case "--loadingScreenAssets":
                    Settings.downloadLoadingScreenAssets = true;

                    break;

                case "-h":
                case "--host":
                    nextArgIsHost = true;

                    break;

                case "-o":
                case "--overwrite":
                    Settings.overwrite = true;

                    break;

                default:
                    Main._setProxyOrPath(arg);

                    break;
            }
        }

        if(
            !Settings.downloadSWF &&
            !Settings.downloadXML &&
            !Settings.downloadImages &&
            !Settings.download2D &&
            !Settings.download3D
        ) {
            Settings.downloadAll = true;
        }
    }

    /**
     * Sets proxy or path information.
     *
     * @param arg Command line argument.
     */
    private static void _setProxyOrPath(String arg)
    {
        String[] proxyInfo = arg.split(":");
        if(Main._isIP(proxyInfo[0])) {
            try {
                Settings.proxyPort = Integer.parseUnsignedInt(proxyInfo[1]);
                if(Settings.proxyPort >= 65535) {
                    Console.println(">tfw you don't know what `host:port` means.");

                    return; // Let's try to be fool proof.
                }

                Settings.proxyHost = proxyInfo[0];
            } catch(Exception e) {
                // Ignore
            }

            return;
        }

        File path = new File(arg);
        if(path.exists()) {
            if(
                path.isDirectory() &&
                path.canWrite()
            ) {
                Settings.path = path;

                return;
            }

            Console.println("Please, make sure that "+ arg +" is a directory and has write permissions!");

            return;
        }

        if(!path.mkdirs()) {
            Console.println("Couldn't create save directory, make sure '"+ arg +"' is valid and has write permissions!");

            return;
        }

        Settings.path = path;
    }

    /**
     * Checks whether a string is an IP or not.
     *
     * @param ip IP to check.
     *
     * @return `true` if `ip` is a valid IPv4, `false` if not.
     */
    private static boolean _isIP(String ip)
    {
        Pattern p = Pattern.compile(
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."  +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."  +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
        );

        return p.matcher(ip).matches();
    }

    /**
     * Prints startup debug info.
     */
    public static void printDebugInfo()
    {
        Console.debug("Save path: "+ Settings.path.getAbsolutePath());

        if(!Settings.proxyHost.isEmpty()) {
            Console.debug("Proxy: "+ Settings.proxyHost +":"+ Settings.proxyPort);
        } else {
            Console.debug("No proxy will be used.");
        }

        if(!Settings.downloadAll) {
            Console.debug("Download SWF files: "+ Settings.downloadSWF);
            Console.debug("Download XML files: "+ Settings.downloadXML);
            Console.debug("Download LoadingScreen assets: "+ Settings.downloadLoadingScreenAssets);
            Console.debug("Download 2D client graphics: "+ Settings.download2D);
            Console.debug("Download 3D client graphics: "+ Settings.download3D);
            Console.debug("Download images: "+ Settings.downloadImages);
        } else {
            Console.debug("All resources will be downloaded!");
        }
    }

    /**
     * Winshit's path parser.
     *
     * @param path       Base path.
     * @param additional Additional path info.
     *
     * @return `path` + `additional`.
     */
    public static String getPath(File path, String additional)
    {
        String base = path.getAbsolutePath();

        if(System.getProperty("os.name").contains("Windows")) {
            additional = additional.replaceAll("/", "\\");
        }

        return base + additional;
    }
}
