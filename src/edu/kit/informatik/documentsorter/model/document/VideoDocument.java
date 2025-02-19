package edu.kit.informatik.documentsorter.model.document;

import edu.kit.informatik.documentsorter.model.tag.MultivalueTag;
import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.List;

import static edu.kit.informatik.documentsorter.model.document.AudioDocument.GENRE_TAG_NAME;
import static edu.kit.informatik.documentsorter.model.document.AudioDocument.LENGTH_TAG_NAME;

/**
 * This class represents an audio document in the file system.
 *
 * @see Document
 * @author uexnb
 * @version 1.0
 */
public class VideoDocument extends Document {
    /**
     * The name of a video genre tag.
     */
    public static final String VIDEO_GENRE_TAG_NAME = "videogenre";
    /**
     * The name of a video length tag.
     */
    public static final String VIDEO_LENGTH_TAG_NAME = "videolength";
    private static final int MAX_VIDEO_CLIP_LENGTH = 300;
    private static final int MAX_SHORT_VIDEO_LENGTH = 3600;
    private static final int MAX_MOVIE_LENGTH = 7200;
    private static final String[] DIFFERENT_VIDEO_LENGTH_NAMES = {"clip", AudioDocument.SHORT_LENGTH, "movie", AudioDocument.LONG_LENGTH};
    private static final int MAX_VIDEO_LENGTH_INDEX = 3;
    /**
     * Constructs a new video document with the given name, tags and number of hits.
     *
     * @param name the name of the document
     * @param tags the tags of the document
     * @param accessNumber the access number of the document
     */
    public VideoDocument(String name, List<Tag> tags, int accessNumber) {
        super(name, tags, accessNumber);
    }
    @Override
    protected List<Tag> modifyTag(List<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.getName().equals(LENGTH_TAG_NAME)) {
                int valueOfTag = Integer.parseInt(tag.getValue());
                //check depending on the length of the video which tag value to set
                if (valueOfTag < MAX_VIDEO_CLIP_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(VIDEO_LENGTH_TAG_NAME,
                            DIFFERENT_VIDEO_LENGTH_NAMES[0]));
                } else if (valueOfTag < MAX_SHORT_VIDEO_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(VIDEO_LENGTH_TAG_NAME,
                            DIFFERENT_VIDEO_LENGTH_NAMES[1]));
                } else if (valueOfTag < MAX_MOVIE_LENGTH) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(VIDEO_LENGTH_TAG_NAME,
                            DIFFERENT_VIDEO_LENGTH_NAMES[2]));
                } else {
                    tags.set(tags.indexOf(tag), new MultivalueTag(VIDEO_LENGTH_TAG_NAME,
                            DIFFERENT_VIDEO_LENGTH_NAMES[MAX_VIDEO_LENGTH_INDEX]));
                }
            } else if (tag.getName().equals(GENRE_TAG_NAME)) {
                Tag newTag = new MultivalueTag(VIDEO_GENRE_TAG_NAME, tag.getValue());
                tags.set(tags.indexOf(tag), newTag);
            }
        }
        return tags;
    }

    @Override
    public Document copy() {
        return new VideoDocument(getName(), getTags(), getAccessNumber());
    }
}
