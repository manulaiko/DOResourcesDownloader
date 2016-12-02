package com.manulaiko.dord.launcher.downloader;

import java.io.File;

import com.manulaiko.dord.launcher.Settings;
import com.manulaiko.tabitha.Console;

/**
 * Downloads the files.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Downloader
{
    /**
     * Category downloader.
     */
    private FileDownloader _cd;

    /**
     * Path to download the files.
     */
    private File _path;

    /**
     * Download start time.
     */
    private long _startTime;

    /**
     * Download end time.
     */
    private long _endTime;

    /**
     * Downloaded bytes.
     */
    private long _bytes;

    /**
     * Constructor.
     *
     * @param path Path to download the files.
     */
    public Downloader(File path)
    {
        this._path = path;
        this._cd   = new FileDownloader(Settings.host, path);
    }

    /**
     * Starts the download.
     */
    public void start()
    {
        this._startTime = System.currentTimeMillis();

        if(
            Settings.downloadAll ||
            Settings.downloadSWF
        ) {
            this.swf();
        }

        this._endTime = System.currentTimeMillis();

        this.printStats(this._bytes, this._startTime, this._endTime);
    }

    /**
     * Downloads SWF files.
     */
    public void swf()
    {
        String[] files = new String[] {
            "/spacemap/main.swf",
            "/spacemap/loadingscreen.swf",
            "/spacemap/preloader.swf"
        };

        Console.println("Downloading swf files...");
        long start = System.currentTimeMillis();
        long bytes = this._cd.download(files);
        long end   = System.currentTimeMillis();

        this.printStats(bytes, start, end);

        this._bytes += bytes;
    }

    /**
     * Prints download stats.
     *
     * IDK if I'm a good coder or a good copy-paster...
     *
     * @param bytes Downloaded bytes.
     * @param start Download start time.
     * @param end   Download end time.
     *
     * @link http://stackoverflow.com/a/16520928 Time formatting.
     * @link http://stackoverflow.com/a/3758880  Byte formatting.
     */
    public void printStats(long bytes, long start, long end)
    {
        String downloadedBytes = "undefined";
        String elapsedTime     = "undefined";
        long   millis          = end - start;
        long   kbs             = 0;

        int unit = 1024;
        if(bytes >= unit) {
            int exp = (int)(Math.log(bytes) / Math.log(unit));
            String pre = "KMGTPE".charAt(exp-1) +"i";

            downloadedBytes = String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
        } else {
            downloadedBytes = bytes +" B";
        }

        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour   = (millis / (1000 * 60 * 60)) % 24;

        if(hour > 0) {
            elapsedTime = String.format("%02d hours %02d minutes and %02d seconds", hour, minute, second);
        } else {
            elapsedTime = String.format("%02d minutes and %02d seconds", minute, second);
        }

        kbs = (bytes / unit) / (millis / 1000);

        Console.println("Downloaded "+ downloadedBytes +" in "+ elapsedTime +" at "+ kbs +" KiB/s");
    }
}
