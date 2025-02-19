package edu.kit.informatik.documentsorter.command;

import edu.kit.informatik.documentsorter.model.FilingFinesse;

/**
 * This interface represents an executable command.
 *
 * @author Programmieren-Team
 * @author uexnb
 * @version 1.0
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @param model            the model to execute the command on
     * @param commandArguments the arguments of the command
     * @return the result of the command
     */
    CommandResult execute(FilingFinesse model, String[] commandArguments);

    /**
     * Returns the number of arguments that the command expects.
     *
     * @return the number of arguments that the command expects
     */
    int getNumberOfArguments();
}
