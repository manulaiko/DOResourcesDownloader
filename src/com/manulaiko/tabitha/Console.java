package com.manulaiko.tabitha;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Console class.
*
* This class is used to interact with the console.
* It has methods for printing strings and reading input.
*
* @author Manulaiko <manulaiko@gmail.com>
*/
public class Console
{
    ///////////////////////////////
    // Start Constant definition //
    ///////////////////////////////
    public static final String LINE_EQ         = "======================================================";
    public static final String LINE_MINUS      = "------------------------------------------------------";
    public static final String ANSI_RESET      = "\u001B[0m";
    public static final String ANSI_BLACK      = "\u001B[30m";
    public static final String ANSI_RED        = "\u001B[31m";
    public static final String ANSI_GREEN      = "\u001B[32m";
    public static final String ANSI_YELLOW     = "\u001B[33m";
    public static final String ANSI_BLUE       = "\u001B[34m";
    public static final String ANSI_PURPLE     = "\u001B[35m";
    public static final String ANSI_CYAN       = "\u001B[36m";
    public static final String ANSI_WHITE      = "\u001B[37m";
    public static final String ANSI_BOLD       = "\u001B[1m";
    public static final String ANSI_BOLD_RESET = "\u001B[21m";
    public static final String ANSI_BLINK      = "\u001B[5m";
    /////////////////////////////
    // End Constant definition //
    /////////////////////////////

    /**
     * Whether to print caller info or not.
     */
    public static boolean debug = false;

    /**
     * Prints debug messages.
     *
     * @param strings String(s) to print.
     */
    public static void debug(Object... strings)
    {
        if(!Console.debug) {
            return;
        }

        Console.println(strings);
    }

    /**
     * Short method for `Console.print`.
     *
     * @param strings String(s) to print.
     */
    public static void print(Object... strings)
    {
        String str = "";
        for(Object s : strings) {
            str += s.toString();
        }

        Console._print(str, Console.debug);
    }

    /**
     * Short method for `Console.println`.
     *
     * @param strings String(s) to print.
     */
    public static void println(Object... strings)
    {
        String str = "";
        for(Object s : strings) {
            str += s.toString();
        }

        Console._print(str+"\n", Console.debug);
    }

    /**
     * Prints a(some) string(s) to the console.
     *
     * Usage is fairly simple:
     *
     *     Console.print("Hello world!");
     *
     * You can also concatenate various string by sending them
     * as different parameters:
     *
     *     Console.print("Hello", " ", "world!");
     *
     * @param strings String(s) to print.
     */
    public static void print(boolean showInfo, String... strings)
    {
        String str = "";
        for(String s : strings) {
            str += s;
        }

        Console._print(str, showInfo);
    }

    /**
     * Prints a(some) string(s) to the console and sets cursor in new line.
     *
     * Usage is fairly simple:
     *
     *     Console.print("Hello world!");
     *
     * You can also concatenate various string by sending them
     * as different parameters:
     *
     *     Console.print("Hello", " ", "world!");
     *
     * @param strings String(s) to print.
     */
    public static void println(boolean showInfo, String... strings)
    {
        String str = "";

        for(String s : strings) {
            str += s;
        }

        Console._print(str +"\n", showInfo);
    }

    /**
     * Actually prints the string to the console.
     *
     * @param string   String to print.
     * @param showInfo Whether to show or not calling method info.
     */
    private static void _print(String string, boolean showInfo)
    {
        String message = "";
        if(showInfo) {
            message += Console._getCaller();
        }

        System.out.print(message + string);
    }

    /**
     * Returns caller class and method.
     *
     * @return Caller class and method.
     */
    private static String _getCaller()
    {
        String caller = "";
        StackTraceElement[] st = Thread.currentThread()
                                       .getStackTrace();
        for(StackTraceElement aSt : st) {
            String cls = aSt.getClassName();
            String mth = aSt.getMethodName();
            int    ln  = aSt.getLineNumber();

            if(
                !cls.equals("com.manulaiko.tabitha.Console") &&
                !cls.equals("java.lang.Thread")
            ) {
                caller = cls +"::"+ mth +" ("+ ln +")";

                break;
            }
        }

        //Build date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return (ANSI_BLACK +"["+ dateFormat.format(date) +"]"+ ANSI_YELLOW +" (" + caller + ")"+ ANSI_BLACK +": "+ ANSI_RESET);
    }

    /**
     * Returns a string from input.
     *
     * @return Input result.
     */
    public static String readLine()
    {
        return System.console().readLine();
    }

    /**
     * Returns a string from input without showing it.
     *
     * @return Input result.
     */
    public static String readPassword()
    {
        return new String(System.console().readPassword());
    }

    /**
     * Returns an integer from input.
     *
     * @return Input result.
     */
    public static int readInt()
    {
        try {
            return Integer.parseInt(System.console().readLine());
        } catch(Exception e) {
            return 0;
        }
    }

    /**
     * Returns a boolean from input.
     *
     * Possible `true` values:
     *  * `true`.
     *  * `yes`.
     *  * `1`.
     *
     * @return Input result.
     */
    public static boolean readBoolean()
    {
        String line = Console.readLine();

        if(
            line.equalsIgnoreCase("true") ||
            line.equalsIgnoreCase("yes")  ||
            line.equalsIgnoreCase("1")
        ) {
            return true;
        }

        return false;
    }
}
