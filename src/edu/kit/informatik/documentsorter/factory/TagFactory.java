package edu.kit.informatik.documentsorter.factory;

import edu.kit.informatik.documentsorter.exceptions.InitialisationException;
import edu.kit.informatik.documentsorter.model.document.AudioDocument;
import edu.kit.informatik.documentsorter.model.document.ImageDocument;
import edu.kit.informatik.documentsorter.model.document.TextDocument;
import edu.kit.informatik.documentsorter.model.document.VideoDocument;
import edu.kit.informatik.documentsorter.model.tag.Tag;
import edu.kit.informatik.documentsorter.model.tag.BinaryTag;
import edu.kit.informatik.documentsorter.model.tag.MultivalueTag;
import edu.kit.informatik.documentsorter.model.tag.NumericTag;

import java.util.Arrays;

import static edu.kit.informatik.documentsorter.factory.DocumentFactory.NUMBER_NOT_INT_FAILURE;

/**
 * This class is a factory for creating tags.
 *
 * @author uexnb
 * @version 1.0
 */
public class TagFactory implements Factory<Tag> {
    /**
     * The names of the inferred tags.
     */
    static final String[] INFERRED_TAG_NAMES = {AudioDocument.AUDIO_LENGTH_TAG_NAME, AudioDocument.AUDIO_GENRE_TAG_NAME,
                                                ImageDocument.IMAGE_SIZE_TAG_NAME, VideoDocument.VIDEO_GENRE_TAG_NAME,
                                                VideoDocument.VIDEO_LENGTH_TAG_NAME, TextDocument.TEXT_LENGTH_TAG_NAME,
                                                TextDocument.TEXT_GENRE_TAG_NAME};

    private static final String ASSIGNMENT_CHARACTER = "=";
    private static final String BINARY_TAG_REGEX = "^[a-zA-Z][a-zA-Z0-9]+$";
    private static final String MULTIVALUE_TAG_VALUE_REGEX = "[a-zA-Z][a-zA-Z0-9 ]+";
    private static final String NUMERIC_TAG_VALUE_REGEX = "\\d+";
    private static final String INVALID_TAG_FORMAT_MESSAGE = "The tag format is invalid.";
    private static final String INVALID_INFERRED_TAG_MESSAGE = "An inferred tag is defined with the wrong tag type.";

    @Override
    public Tag create(String[] arguments) throws InitialisationException {
        String tagInformation = arguments[0];
        Tag currentTag;
        String[] tagParts = tagInformation.split(ASSIGNMENT_CHARACTER);
        String tagName = tagParts[0];
        String tagValue = tagParts.length > 1 ? tagParts[1] : null;

        if (Arrays.stream(INFERRED_TAG_NAMES).anyMatch(tagName::equalsIgnoreCase)) {
            throw new InitialisationException(INVALID_INFERRED_TAG_MESSAGE);
        }

        if (tagValue == null) {
            if (!tagName.matches(BINARY_TAG_REGEX)) {
                throw new InitialisationException(INVALID_TAG_FORMAT_MESSAGE);
            }
            currentTag = new BinaryTag(tagName);
        } else {
            if (tagValue.matches(NUMERIC_TAG_VALUE_REGEX)) {
                try {
                    Integer.parseInt(tagValue);
                } catch (NumberFormatException e) {
                    throw new InitialisationException(NUMBER_NOT_INT_FAILURE);
                }
                currentTag = new NumericTag(tagName, tagValue);
            } else if (tagValue.matches(MULTIVALUE_TAG_VALUE_REGEX)) {
                currentTag = new MultivalueTag(tagName, tagValue);
            } else {
                throw new InitialisationException(INVALID_TAG_FORMAT_MESSAGE);
            }
        }

        return currentTag;
    }
}
