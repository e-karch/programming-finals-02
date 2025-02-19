package edu.kit.informatik.documentsorter.command;

import edu.kit.informatik.documentsorter.exceptions.InitialisationException;
import edu.kit.informatik.documentsorter.factory.DocumentFactory;
import edu.kit.informatik.documentsorter.model.FilingFinesse;
import edu.kit.informatik.documentsorter.model.document.Document;
import edu.kit.informatik.documentsorter.model.tag.Tag;
import edu.kit.informatik.documentsorter.model.DocumentFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This command reads in an input file containing all the required data for the file system
 * and saves its contents temporarily.
 * Several files can be read in, but are then treated separately.
 *
 * @author Programmieren-Team
 * @author uexnb
 * @version 1.0
 */
public final class LoadCommand implements Command {
    /**
     * The value of a tag that has not been set for a document.
     * Public because it is used in {@link Tag}.
     */
    public static final String TAG_UNDEFINED_VALUE = "UNDEFINED";
    private static final String INFORMATION_SEPARATOR = ",";
    private static final String INVALID_FILE_NAME_MESSAGE = "Document file with the specified name not found";
    private static final String INVALID_FILE_CONTENT_FORMAT = "File %s is empty";
    private static final String SUCCESS_MESSAGE_FORMAT = "Loaded %s with id: %d";
    private static final String INVALID_ACCESS_NUMBERS_MESSAGE = "The access number of at least one document must be greater than 0";
    private static final String AMBIGUOS_TAG_MESSAGE = "A tag is defined with different tag types";
    private static final int NUMBER_OF_ARGUMENTS = 1;
    @Override
    public CommandResult execute(FilingFinesse model, String[] commandArguments) {
        Scanner scanner;
        try { //try to open the file
            scanner = new Scanner(new File(commandArguments[0]));
        } catch (FileNotFoundException e) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_FILE_NAME_MESSAGE);
        }
        //retrieve the file content
        List<String> fileContent = new ArrayList<>();
        while (scanner.hasNextLine()) {
            fileContent.add(scanner.nextLine());
        }
        scanner.close();
        if (fileContent.isEmpty()) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_FILE_CONTENT_FORMAT.formatted(commandArguments[0]));
        }
        //create the documents using the factory
        List<Document> documents = new ArrayList<>();
        for (String line : fileContent) {
            String[] documentData = line.split(INFORMATION_SEPARATOR);
            try {
                documents.add(new DocumentFactory().create(documentData));
            } catch (InitialisationException e) {
                return new CommandResult(CommandResultType.FAILURE, e.getMessage());
            }
        }
        //extract the tags from the documents and add them to a list storing all tags of the file
        List<Tag> allTags;
        try {
            allTags = storeAllTags(documents);
        } catch (InitialisationException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
        int accumulatedAccessNumber = documents.stream()
                .mapToInt(Document::getAccessNumber)
                .sum();
        if (accumulatedAccessNumber == 0) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_ACCESS_NUMBERS_MESSAGE);
        }
        //check for every document which tag has not been set yet and add a tag with the value "undefined"
        documents.forEach(document ->
                allTags.stream()
                        .filter(tag -> !document.getTags().contains(tag))
                        .map(Tag::copy)
                        .peek(tag -> tag.setValue(TAG_UNDEFINED_VALUE))
                        .forEach(document::addTag)
        );
        //store all the information in a representation of the current file
        model.addFile(new DocumentFile(allTags, accumulatedAccessNumber, documents));
        return new CommandResult(CommandResultType.SUCCESS, determineResultMessage(model, commandArguments, fileContent));
    }

    private static List<Tag> storeAllTags(List<Document> documents) throws InitialisationException {
        List<Tag> allTags = new ArrayList<>();
        for (Document document : documents) {
            for (Tag tag : document.getTags()) {
                if (!allTags.contains(tag)) { //if the tag is not in the list, add it
                    allTags.add(tag.copy());
                } else { //if the tag is already in the list, add the value to the list of possible values
                    if (allTags.get(allTags.indexOf(tag)).getClass() != tag.getClass()) {
                        throw new InitialisationException(AMBIGUOS_TAG_MESSAGE);
                    }
                    allTags.get(allTags.indexOf(tag)).addPossibleValue(tag.getValue());
                }
            }
        }
        return allTags;
    }

    private String determineResultMessage(FilingFinesse model, String[] commandArguments, List<String> fileContent) {
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append(SUCCESS_MESSAGE_FORMAT.formatted(commandArguments[0],
                model.getAllDocumentFiles().get(model.getAllDocumentFiles().size() - 1).getIdentifier()));
        for (String line : fileContent) {
            resultMessage.append(System.lineSeparator());
            resultMessage.append(line);
        }
        return resultMessage.toString();
    }

    @Override
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
}
