package edu.kit.informatik.documentsorter.command;

import edu.kit.informatik.documentsorter.model.DocumentFile;
import edu.kit.informatik.documentsorter.model.FilingFinesse;

import static edu.kit.informatik.documentsorter.factory.DocumentFactory.INVALID_ACCESS_NUMBER_MESSAGE;
import static edu.kit.informatik.documentsorter.factory.DocumentFactory.NUMBER_NOT_INT_FAILURE;

/**
 * This command allows you to change the access number for a specific file of a loaded input.
 * This means that the status of a loaded input is changed, but not the text file
 * from which the input was loaded.
 *
 * @author Programmieren-Team
 * @author uexnb
 * @version 1.0
 */
public final class ChangeCommand implements Command {
    /**
     * The error message for the case that the identifier is invalid.
     * Package-private because it is used in {@link RunCommand}.
     */
    static final String INVALID_IDENTIFIER_MESSAGE = "The file with the specified id does not exist.";
    private static final int NUMBER_OF_ARGUMENTS = 3;
    private static final String INVALID_DOCUMENT_NAME_MESSAGE = "The document with the specified name does not exist in the file.";
    private static final String SUCCESS_MESSAGE_FORMAT = "Change %d to %d for %s";
    @Override
    public CommandResult execute(FilingFinesse model, String[] commandArguments) {
        //try to parse the identifier and the new access number of the file
        int identifier;
        int accessNumber;
        try {
            identifier = Integer.parseInt(commandArguments[0]);
            accessNumber = Integer.parseInt(commandArguments[2]);
        } catch (NumberFormatException e) {
            return new CommandResult(CommandResultType.FAILURE, NUMBER_NOT_INT_FAILURE);
        }
        //check if the file exists
        if (identifier < 0 || identifier >= DocumentFile.getNumberOfFiles()) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_IDENTIFIER_MESSAGE);
        }
        DocumentFile currentFile = model.getAllDocumentFiles().get(identifier);
        //check if the document exists in the file
        if (currentFile.getDocuments().stream().noneMatch(document -> document.getName().equals(commandArguments[1]))) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_DOCUMENT_NAME_MESSAGE);
        }
        if (accessNumber < 0) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_ACCESS_NUMBER_MESSAGE);
        }
        //get the index of the referenced document
        int index = currentFile.getDocuments().indexOf(currentFile.getDocuments().stream()
                .filter(document -> document.getName().equals(commandArguments[1])).findFirst().get());
        int oldAccessNumber = currentFile.getDocuments().get(index).getAccessNumber();
        //change the access number of the document
        currentFile.getDocuments().get(index).setAccessNumber(accessNumber);
        return new CommandResult(CommandResultType.SUCCESS, String.format(SUCCESS_MESSAGE_FORMAT,
                oldAccessNumber, accessNumber, commandArguments[1]));
    }

    @Override
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
