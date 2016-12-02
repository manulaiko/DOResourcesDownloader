package com.manulaiko.dord.launcher.downloader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.manulaiko.dord.launcher.Settings;
import com.manulaiko.tabitha.Console;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

        if(
            Settings.downloadAll ||
            Settings.downloadXML
        ) {
            this.xml();
        }

        if(
                Settings.downloadAll ||
                Settings.download2D
        ) {
            this.graphics2D();
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
     * Downloads XML files.
     */
    public void xml()
    {
        String[] files = new String[] {
                "/spacemap/xml/assets_loadingScreen.xml",
                "/spacemap/xml/game.xml",
                "/spacemap/xml/maps.php",
                "/spacemap/xml/profile.xml",
                "/spacemap/xml/resources.xml",
                "/spacemap/xml/resources_3d.xml"
        };

        Console.println("Downloading xml files...");
        long start = System.currentTimeMillis();
        long bytes = this._cd.download(files);
        long end   = System.currentTimeMillis();

        this.printStats(bytes, start, end);

        this._bytes += bytes;
    }

    /**
     * Downloads 2D graphic files.
     */
    public void graphics2D()
    {
        Document xml = this.loadXML("/spacemap/xml/resources.xml");
        if(xml == null) {
            Console.debug("Couldn't download '/spacemap/xml/resources.xml'!");

            return;
        }

        NodeList l = xml.getElementsByTagName("location");
        NodeList f = xml.getElementsByTagName("file");

        HashMap<String, String> locations = new HashMap<>();
        ArrayList<String>       files     = new ArrayList<>();

        for(int i = 0; i < l.getLength(); i++) {
            Node location = l.item(i);
            locations.put(
                    location.getAttributes().getNamedItem("id").getTextContent(),
                    location.getAttributes().getNamedItem("path").getTextContent()
            );

            Console.debug(
                    "Location: ",
                    location.getAttributes().getNamedItem("id").getTextContent(),
                    "\nPath: ",
                    location.getAttributes().getNamedItem("path").getTextContent()
            );
        }

        for(int i = 0; i < f.getLength(); i++) {
            Node file = f.item(i);

            String location  = locations.get(file.getAttributes().getNamedItem("location").getTextContent());
            String name      = file.getAttributes().getNamedItem("name").getTextContent();
            String extension = file.getAttributes().getNamedItem("type").getTextContent();

            files.add(
                    "/spacemap/"+ location + name +"."+ extension
            );
        }

        Console.println("Downloading 2D graphic files...");
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

    /**
     * Downloads a xml file and parses it.
     *
     * @param path Path to file.
     *
     * @return Parsed XML file.
     */
    public Document loadXML(String path)
    {
        File f = new File(this._path.getAbsolutePath() + (path.replaceAll("/", File.separator)));
        if(!f.exists()) {
            f = this._downloadXML(path);
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);

            doc.normalizeDocument();

            return doc;
        } catch(Exception e) {
            // Ignore
        }

        return null;
    }

    /**
     * Downloads a XML file.
     *
     * @param path Path to file.
     */
    private File _downloadXML(String path)
    {
        try {
            URL  url = new URL("http://" + Settings.host + path);
            File tmp = File.createTempFile(path.replaceAll("/", "-"), ".xml");

            this._cd.download(url, tmp.getAbsolutePath());

            return tmp;
        } catch(Exception e) {
            // Ignore
        }

        return null;
    }
}
