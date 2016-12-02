package com.manulaiko.dord.launcher.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.manulaiko.dord.launcher.Settings;
import com.manulaiko.tabitha.Console;

/**
 * File downloader.
 *
 * Used to download the individual files.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class FileDownloader
{
    /**
     * Base host.
     */
    private String _host;

    /**
     * Path to save the files.
     */
    private File _path;

    /**
     * Constructor.
     *
     * @param host Base host.
     * @param path Path to save the files.
     */
    public FileDownloader(String host, File path)
    {
        this._host = host;
        this._path = path;
    }

    /**
     * Downloads a file.
     *
     * @param path Path on remote host to file.
     *
     * @return Downloaded bytes.
     */
    public long download(String path)
    {
        try {
            URL    url = new URL("http://" + this._host + path);
            String p   = this._path.getAbsolutePath() + (path.replaceAll("/", File.separator));

            Console.debug("Downloading "+ url +"...");
            return this.download(url, p);
        } catch(Exception e) {
            Console.println("Couldn't download "+ path +"!");
            Console.println(e.getMessage());

            if(Settings.debug) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * Downloads various files.
     *
     * @param files Files to download from remote host.
     *
     * @return Downloaded bytes.
     */
    public long download(String[] files)
    {
        long bytes = 0;

        for(String path : files) {
            bytes += this.download(path);
        }

        return bytes;
    }

    /**
     * Downloads various files.
     *
     * @param files Files to download from remote host.
     *
     * @return Downloaded bytes.
     */
    public long download(ArrayList<String> files)
    {
        long bytes = 0;

        for(String path : files) {
            bytes += this.download(path);
        }

        return bytes;
    }

    /**
     * Actually performs the download.
     *
     * *cough* copy-paste *cough*.
     *
     * @param url      URL to download.
     * @param savePath Path to save the file.
     *
     * @return Downloaded bytes.
     *
     * @link http://stackoverflow.com/a/14413945 SO is bae.
     */
    public long download(URL url, String savePath) throws IOException
    {
        InputStream      is    = null;
        FileOutputStream fos   = null;
        long             bytes = 0;
        File             p     = new File(savePath).getParentFile();

        if(
            !p.exists() &&
            !p.mkdirs()
        ) {
            Console.println("Couldn't make subdirectories for '"+ savePath +"'!");
            Console.println("Make sure write permissions are enabled!");

            return bytes;
        }

        try {
            URLConnection urlConn = url.openConnection(); // Connect

            is  = urlConn.getInputStream();       // Get connection input stream
            fos = new FileOutputStream(savePath); // Open output stream to local file

            byte[] buffer = new byte[4096]; // Declare 4KB buffer
            int len;

            // While we have available data, continue downloading and storing to local file
            while((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                bytes += len;
            }
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } finally {
                if(fos != null) {
                    fos.close();
                }
            }
        }

        return bytes;
    }
}
