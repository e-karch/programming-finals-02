package edu.kit.informatik.documentsorter.command;

import edu.kit.informatik.documentsorter.model.FilingFinesse;

import static edu.kit.informatik.documentsorter.command.ChangeCommand.INVALID_IDENTIFIER_MESSAGE;
import static edu.kit.informatik.documentsorter.factory.DocumentFactory.NUMBER_NOT_INT_FAILURE;

/**
 * This command generates the automatic folder structure on a specific loaded content of an input file.
 * The choice of file is controlled by the identifier, which is given to the
 * command as a parameter. If access numbers of any documents have been changed
 * since loading, this is taken into account.
 * The output then consists of the calculated directory tree.
 *
 * @author Programmieren-Team
 * @author uexnb
 * @version 1.0
 */
public final class RunCommand implements Command {
    private static final int NUMBER_OF_ARGUMENTS = 1;
    @Override
    public CommandResult execute(FilingFinesse model, String[] commandArguments) {
        //check if the identifier is valid
        int identifier;
        try {
            identifier = Integer.parseInt(commandArguments[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(CommandResultType.FAILURE, NUMBER_NOT_INT_FAILURE);
        }
        if (identifier < 0 || identifier >= model.getAllDocumentFiles().size()) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_IDENTIFIER_MESSAGE);
        }
        model.generateDirectoryTree(model.getAllDocumentFiles().get(identifier));
        return new CommandResult(CommandResultType.SUCCESS, model.getDirectoryTree().getOutput());
    }

    @Override
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
