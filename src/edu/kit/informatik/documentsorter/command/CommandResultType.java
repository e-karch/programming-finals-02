package edu.kit.informatik.documentsorter.command;
/**
 * This enum represents the types that a result of a command can be.
 *
 * @author Programmieren-Team
 * @author uexnb
 * @version 1.0
 */
public enum CommandResultType {
    /**
     * The command was executed successfully.
     */
    SUCCESS,
    /**
     * An error occured during processing the command.
     */
    FAILURE;
}
