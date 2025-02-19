package edu.kit.informatik.documentsorter.model.document;

import edu.kit.informatik.documentsorter.model.tag.MultivalueTag;
import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.List;

import static edu.kit.informatik.documentsorter.model.document.AudioDocument.GENRE_TAG_NAME;

/**
 * This class represents an audio document in the file system.
 *
 * @see Document
 * @author uexnb
 * @version 1.0
 */
public class TextDocument extends Document {
/**
     * The name of a text genre tag.
     */
    public static final String TEXT_GENRE_TAG_NAME = "textgenre";
    /**
     * The name of a text length tag.
     */
    public static final String TEXT_LENGTH_TAG_NAME = "textlength";
    /**
     * The name of a words tag.
     */
    public static final String WORDS_TAG_NAME = "words";
    private static final int MAX_SHORT_TEXT_LENGTH = 100;
    private static final int MAX_NORMAL_TEXT_LENGTH = 1000;
    private static final String[] DIFFERENT_TEXT_LENGTH_NAMES = {AudioDocument.SHORT_LENGTH,
                                                                 ImageDocument.MEDIUM_LENGTH,
                                                                 AudioDocument.LONG_LENGTH};

    /**
     * Constructs a new text document with the given name, tags and number of hits.
     *
     * @param name the name of the document
     * @param tags the tags of the document
     * @param accessNumber the access number of the document
     */
    public TextDocument(String name, List<Tag> tags, int accessNumber) {
        super(name, tags, accessNumber);
    }
    @Override
    protected List<Tag> modifyTag(List<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.getName().equals(WORDS_TAG_NAME)) {
                int valueOfTag = Integer.parseInt(tag.getValue());
                //check depending on the length of the text which tag value to set
                if (valueOfTag < MAX_SHORT_TEXT_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(TEXT_LENGTH_TAG_NAME,
                            DIFFERENT_TEXT_LENGTH_NAMES[0]));
                } else if (valueOfTag < MAX_NORMAL_TEXT_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(TEXT_LENGTH_TAG_NAME,
                            DIFFERENT_TEXT_LENGTH_NAMES[1]));
                } else {
                    tags.set(tags.indexOf(tag), new MultivalueTag(TEXT_LENGTH_TAG_NAME,
                            DIFFERENT_TEXT_LENGTH_NAMES[2]));
                }
            } else if (tag.getName().equals(GENRE_TAG_NAME)) {
                Tag newTag = new MultivalueTag(TEXT_GENRE_TAG_NAME, tag.getValue());
                tags.set(tags.indexOf(tag), newTag);
            }
        }
        return tags;
    }

    @Override
    public Document copy() {
        return new TextDocument(getName(), getTags(), getAccessNumber());
    }
}
