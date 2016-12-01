package com.manulaiko.tabitha.utils;

/**
 * Command interface.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface ICommand
{
    /**
     * Executes the command.
     *
     * @param command Command arguments.
     */
    void execute(String[] command);

    /**
     * Checks whether this command can execute `name` command.
     *
     * @param name Command name to check.
     *
     * @return Whether this command can execute `name`.
     */
    boolean canExecute(String name);

    /**
     * Returns command name.
     *
     * @return Command name.
     */
    String getName();

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    String getDescription();
}
