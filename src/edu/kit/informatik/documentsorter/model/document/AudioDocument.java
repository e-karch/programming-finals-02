package edu.kit.informatik.documentsorter.model.document;

import edu.kit.informatik.documentsorter.model.tag.MultivalueTag;
import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.List;
/**
 * This class represents an audio document in the file system.
 *
 * @see Document
 * @author uexnb
 * @version 1.0
 */
public class AudioDocument extends Document {
    /**
     * The name of an audio genre tag.
     */
    public static final String AUDIO_GENRE_TAG_NAME = "audiogenre";
    /**
     * The name of an audio length tag.
     */
    public static final String AUDIO_LENGTH_TAG_NAME = "audiolength";
    /**
     * The name of a length tag.
     */
    public static final String LENGTH_TAG_NAME = "length";
    /**
     * The name of a genre tag.
     */
    public static final String GENRE_TAG_NAME = "genre";
    /**
     * The specifier for a short length.
     */
    protected static final String SHORT_LENGTH = "short";
    /**
     * The specifier for a long length.
     */
    protected static final String LONG_LENGTH = "long";
    private static final int MAX_AUDIO_SAMPLE_LENGTH = 10;
    private static final int MAX_SHORT_AUDIO_LENGTH = 60;
    private static final int MAX_NORMAL_AUDIO_LENGTH = 300;
    private static final String[] DIFFERENT_AUDIO_LENGTH_NAMES = {"sample", SHORT_LENGTH, "normal", LONG_LENGTH};
    private static final  int MAX_AUDIO_LENGTH_INDEX = 3;

    /**
     * Constructs a new audio document with the given name, tags and number of hits.
     *
     * @param name the name of the document
     * @param tags the tags of the document
     * @param accessNumber the access number of the document
     */
    public AudioDocument(String name, List<Tag> tags, int accessNumber) {
        super(name, tags, accessNumber);
    }
    @Override
    protected List<Tag> modifyTag(List<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.getName().equals(LENGTH_TAG_NAME)) {
                int valueOfTag = Integer.parseInt(tag.getValue());
                //check depending on the length of the audio which tag value to set
                if (valueOfTag < MAX_AUDIO_SAMPLE_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(AUDIO_LENGTH_TAG_NAME,
                            DIFFERENT_AUDIO_LENGTH_NAMES[0]));
                } else if (valueOfTag < MAX_SHORT_AUDIO_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(AUDIO_LENGTH_TAG_NAME,
                            DIFFERENT_AUDIO_LENGTH_NAMES[1]));
                } else if (valueOfTag < MAX_NORMAL_AUDIO_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(AUDIO_LENGTH_TAG_NAME,
                            DIFFERENT_AUDIO_LENGTH_NAMES[2]));
                } else {
                    tags.set(tags.indexOf(tag), new MultivalueTag(AUDIO_LENGTH_TAG_NAME,
                            DIFFERENT_AUDIO_LENGTH_NAMES[MAX_AUDIO_LENGTH_INDEX]));
                }
            } else if (tag.getName().equals(GENRE_TAG_NAME)) {
                Tag newTag = new MultivalueTag(AUDIO_GENRE_TAG_NAME, tag.getValue());
                tags.set(tags.indexOf(tag), newTag);
            }
        }
        return tags;
    }

    @Override
    public Document copy() {
        return new AudioDocument(getName(), getTags(), getAccessNumber());
    }

}
