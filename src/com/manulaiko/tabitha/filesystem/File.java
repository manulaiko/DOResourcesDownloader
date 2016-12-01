package com.manulaiko.tabitha.filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;

import com.manulaiko.tabitha.Console;

/**
 * File object.
 *
 * This object can be used to read/write easily to a file.
 *
 * The constructor accepts as parameter a string that is the name of the
 * file to open.
 *
 * Example:
 *
 *     File file;
 *     try {
 *         file = File.make("/test");
 *     } catch(FileAlreadyExists e) {
 *         file = new File("/test");
 *     }
 *
 *     Console.println(file.getContent());
 *     file.append("Hello ");
 *     file.appendLine("world!");
 *     Console.println(file.getContent());
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class File
{
    ///////////////////////////////////
    // Static methods and properties //
    ///////////////////////////////////

    /**
     * Makes a file on the filesystem.
     *
     * @param path Path to file to create.
     *
     * @return Created file object.
     *
     * @throws FileAlreadyExistsException If path already exists.
     * @throws IOException                If something goes wrong.
     */
    public static File make(String path) throws FileAlreadyExistsException, IOException
    {
        path = Paths.get(path).toAbsolutePath().normalize().toString();

        java.io.File f = new java.io.File(path);

        if(
            f.exists() ||
            f.isDirectory()
        ) {
            throw new FileAlreadyExistsException(path);
        }

        if(f.createNewFile()) {
            try {
                return new File(path);
            } catch(FileNotFoundException e) {
                Console.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Checks whether a file exists or not.
     *
     * @param path Path to the file.
     *
     * @return Whether path exists and is a file.
     */
    public static boolean exists(String path)
    {
        path = Paths.get(path).toAbsolutePath().normalize().toString();

        java.io.File f = new java.io.File(path);

        return (
            f.exists() &&
            f.isFile()
        );
    }

    ///////////////////////////////////////
    // Non static methods and properties //
    ///////////////////////////////////////

    /**
     * File path.
     */
    public String path = "";

    /**
     * File name.
     */
    public String name = "";

    /**
     * File extension.
     */
    public String extension = "";

    /**
     * File lines.
     */
    public Vector<String> lines = null;

    /**
     * Reader object.
     */
    private BufferedReader _reader = null;

    /**
     * Writer object.
     */
    private BufferedWriter _writer = null;

    /**
     * File object.
     */
    private java.io.File _file = null;

    /**
     * Constructor.
     *
     * @param path Path to file.
     *
     * @throws FileNotFoundException If the file doesn't exist.
     * @throws IOException           If file is a directory.
     */
    public File(String path) throws FileNotFoundException, IOException
    {
        path = Paths.get(path).toAbsolutePath().normalize().toString();

        java.io.File file = new java.io.File(path);

        if(!file.exists()) {
            throw new FileNotFoundException(path);
        } else if(file.isDirectory()) {
            throw new IOException("`"+ path +"` is a directory!");
        }

        String fullName = "";
        int    idx      = path.replaceAll("\\\\", "/")
                              .lastIndexOf("/");

        if(idx >= 0) {
            fullName = path.substring(idx + 1);
        } else {
            fullName = path;
        }

        int i = fullName.lastIndexOf('.');
        int p = Math.max(fullName.lastIndexOf('/'), fullName.lastIndexOf('\\'));

        if(i > p) {
            this.extension = fullName.substring(i + 1);
            this.name = fullName.substring(0, i);
        } else {
            this.name = fullName;
        }

        this.path  = path;
        this._file = file;
    }

    /**
     * Returns file's directory object.
     *
     * @return Directory on which file is located.
     */
    public Directory getDirectory()
    {
        int i = this.path.lastIndexOf(this.name +"."+ this.extension);

        if(i <= 0) {
            try {
                return new Directory(Paths.get(".").toAbsolutePath().normalize().toString());
            } catch(IOException e) {
                Console.println(e.getMessage());
            }
        }

        String dir = this.path.substring(i);

        try {
            return new Directory(dir);
        } catch(IOException e) {
            Console.println(e.getMessage());
        }

        return null;
    }

    /**
     * Returns a single line of the file.
     *
     * @param line Line number.
     *
     * @return Line content.
     *
     * @throws IOException If couldn't read the file.
     */
    public String getLine(int line) throws IOException
    {
        String l = "";
        int    i = 0;

        while((l = this._reader.readLine()) != null) {
            if(i == line) {
                break;
            } else {
                i++;
            }
        }

        return l;
    }

    /**
     * Returns all lines of the file.
     *
     * @return File content.
     *
     * @throws IOException If couldn't read file.
     */
    public ArrayList<String> getAllLines() throws IOException
    {
        ArrayList<String> lines = new ArrayList<>();

        Files.lines(Paths.get(this._file.getAbsolutePath()))
             .forEach(lines::add);

        return lines;
    }
}
