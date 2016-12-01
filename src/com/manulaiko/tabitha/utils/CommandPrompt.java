package com.manulaiko.tabitha.utils;

import com.manulaiko.tabitha.Console;

import java.util.ArrayList;

/**
 * Command Prompt class.
 *
 * Offers an easy way to interact with the application with the use of commands.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CommandPrompt
{
    /**
     * Commands list.
     */
    private ArrayList<ICommand> _commands = new ArrayList<>();

    /**
     * Adds a command to the list.
     *
     * @param command Command to add.
     */
    public void addCommand(ICommand command)
    {
        this._commands.add(command);
    }

    /**
     * Starts the infinite loop for reading commands.
     */
    public void start()
    {
        while(true) {
            Console.print("Enter a command to execute ('help' for a list of commands): ");
            String[] command = Console.readLine().split(" ");

            if(command[0].equalsIgnoreCase("help")) {
                this.printAvailableCommands();
            } else {
                this.execute(command);
            }
        }
    }

    /**
     * Prints available commands.
     */
    public void printAvailableCommands()
    {
        Console.println("Available commands:");
        Console.println();

        this._commands.forEach((ICommand c) -> {
            Console.println(c.getDescription());
            Console.println();
            Console.println(Console.LINE_MINUS);
            Console.println();
        });
    }

    /**
     * Executes an specific command.
     *
     * @param command Command to execute.
     */
    public void execute(String[] command)
    {
        this._commands.forEach((ICommand c) -> {
            if(c.canExecute(command[0])) {
                c.execute(command);
            }
        });
    }
}
