package edu.kit.informatik.documentsorter.factory;

import edu.kit.informatik.documentsorter.exceptions.InitialisationException;
import edu.kit.informatik.documentsorter.model.document.Document;
import edu.kit.informatik.documentsorter.model.document.AudioDocument;
import edu.kit.informatik.documentsorter.model.document.ImageDocument;
import edu.kit.informatik.documentsorter.model.document.ProgramDocument;
import edu.kit.informatik.documentsorter.model.document.TextDocument;
import edu.kit.informatik.documentsorter.model.document.VideoDocument;
import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static edu.kit.informatik.documentsorter.factory.TagFactory.INFERRED_TAG_NAMES;

/**
 * This class is a factory for creating documents.
 *
 * @author uexnb
 * @version 1.0
 */
public class DocumentFactory implements Factory<Document> {
    /**
     * The error message for the case that the parsed number is not an integer.
     */
    public static final String NUMBER_NOT_INT_FAILURE = "The number is not an integer.";
    /**
     * The error message for the case that the access number is negative.
     */
    public static final String INVALID_ACCESS_NUMBER_MESSAGE = "The access number must be a positive integer.";

    private static final int START_INDEX_FOR_TAGS = 3;
    private static final String AUDIO_DOCUMENT = "audio";
    private static final String TEXT_DOCUMENT = "text";
    private static final String VIDEO_DOCUMENT = "video";
    private static final String IMAGE_DOCUMENT = "image";
    private static final String PROGRAM_DOCUMENT = "program";
    private static final String ILLEGAL_NAME_CHARACTER = " ";
    private static final String INVALID_DOCUMENT_NAME_MESSAGE = "The document name contains whitespace.";
    private static final String DUPLICATE_TAGS_MESSAGE = "There is at least one tag that is assigned more than once.";
    private static final String INFERRED_AND_MODIFIABLE_TAG_MESSAGE = "An inferred and modifiable tag cannot be defined on the same "
            + "document.";
    private static final String INVALID_DOCUMENT_TYPE_FORMAT = "The document type '%s' is invalid.";
    private static final String INVALID_NUMBER_OF_ARGUMENTS_MESSAGE = "The number of arguments for a document is too small.";
    private static final String[] TAGS_TO_MODIFY = {ImageDocument.SIZE_TAG_NAME, AudioDocument.LENGTH_TAG_NAME,
                                                    AudioDocument.GENRE_TAG_NAME, TextDocument.WORDS_TAG_NAME};
    @Override
    public Document create(String[] arguments) throws InitialisationException {
        //check if the number of arguments is at least three (name, type, access number)
        if (arguments.length < START_INDEX_FOR_TAGS) {
            throw new InitialisationException(INVALID_NUMBER_OF_ARGUMENTS_MESSAGE);
        }
        //extract the tags, hash set used to detect duplicate tags
        Set<Tag> tags = new HashSet<>();
        for (int i = START_INDEX_FOR_TAGS; i < arguments.length; i++) {
            Tag currentTag = new TagFactory().create(new String[] {arguments[i]});
            if (!tags.add(currentTag)) {
                throw new InitialisationException(DUPLICATE_TAGS_MESSAGE);
            }
            tags.add(new TagFactory().create(new String[] {arguments[i]}));
        }
        List<Tag> tagList = new ArrayList<>(tags);
        //check if the document contains an inferred tag and a modifiable tag
        if (!Collections.disjoint(Arrays.asList(TAGS_TO_MODIFY), tagList)
                && !Collections.disjoint(tagList, Arrays.asList(INFERRED_TAG_NAMES))) {
            throw new InitialisationException(INFERRED_AND_MODIFIABLE_TAG_MESSAGE);
        }
        //check if document name contains whitespace
        if (arguments[0].contains(ILLEGAL_NAME_CHARACTER)) {
            throw new InitialisationException(INVALID_DOCUMENT_NAME_MESSAGE);
        }
        int accessNumber;
        try {
            accessNumber = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            throw new InitialisationException(NUMBER_NOT_INT_FAILURE);
        }
        if (accessNumber < 0) {
            throw new InitialisationException(INVALID_ACCESS_NUMBER_MESSAGE);
        }
        return getDocument(arguments[0], arguments[1], accessNumber, tagList);
    }

    private static Document getDocument(String name, String documentType, int accessNumber, List<Tag> tagList)
            throws InitialisationException {
        return switch (documentType.toLowerCase()) {
            case AUDIO_DOCUMENT -> new AudioDocument(name, tagList, accessNumber);
            case TEXT_DOCUMENT -> new TextDocument(name, tagList, accessNumber);
            case VIDEO_DOCUMENT -> new VideoDocument(name, tagList, accessNumber);
            case IMAGE_DOCUMENT -> new ImageDocument(name, tagList, accessNumber);
            case PROGRAM_DOCUMENT -> new ProgramDocument(name, tagList, accessNumber);
            default -> throw new InitialisationException(String.format(INVALID_DOCUMENT_TYPE_FORMAT, documentType));
        };
    }
}
