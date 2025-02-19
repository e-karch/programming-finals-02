package edu.kit.informatik.documentsorter.model.document;

import edu.kit.informatik.documentsorter.model.tag.MultivalueTag;
import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.List;

/**
 * This class represents an image document in the file system.
 *
 * @see Document
 * @author uexnb
 * @version 1.0
 */
public class ImageDocument extends Document {
    /**
     * The name of an image size tag.
     */
    public static final String IMAGE_SIZE_TAG_NAME = "imagesize";
/**
     * The name of a size tag.
     */
    public static final String SIZE_TAG_NAME = "size";
    /**
     * The specifier for a medium length.
     */
    protected static final String MEDIUM_LENGTH = "medium";

    private static final int MAX_IMAGE_ICON_SIZE = 10000;
    private static final int MAX_SMALL_IMAGE_SIZE = 40000;
    private static final int MAX_MEDIUM_IMAGE_SIZE = 80000;
    private static final String[] DIFFERENT_IMAGE_SIZE_NAMES = {"icon", "small", MEDIUM_LENGTH, "large"};
    private static final int MAX_IMAGE_SIZE_INDEX = 3;

    /**
     * Constructs a new image document with the given name, tags and number of hits.
     *
     * @param name the name of the document
     * @param tags the tags of the document
     * @param accessNumber the access number of the document
     */
    public ImageDocument(String name, List<Tag> tags, int accessNumber) {
        super(name, tags, accessNumber);
    }

    @Override
    protected List<Tag> modifyTag(List<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.getName().equals(SIZE_TAG_NAME)) {
                int valueOfTag = Integer.parseInt(tag.getValue());
                //check depending on the size of the image which tag value to set
                if (valueOfTag < MAX_IMAGE_ICON_SIZE) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(IMAGE_SIZE_TAG_NAME,
                            DIFFERENT_IMAGE_SIZE_NAMES[0]));
                } else if (valueOfTag < MAX_SMALL_IMAGE_SIZE) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(IMAGE_SIZE_TAG_NAME,
                            DIFFERENT_IMAGE_SIZE_NAMES[1]));
                } else if (valueOfTag < MAX_MEDIUM_IMAGE_SIZE) {
                    tags.set(tags.indexOf(tag), new MultivalueTag(IMAGE_SIZE_TAG_NAME,
                            DIFFERENT_IMAGE_SIZE_NAMES[2]));
                } else {
                    tags.set(tags.indexOf(tag), new MultivalueTag(IMAGE_SIZE_TAG_NAME,
                            DIFFERENT_IMAGE_SIZE_NAMES[MAX_IMAGE_SIZE_INDEX]));
                }
            }
        }
        return tags;
    }

    @Override
    public Document copy() {
        return new ImageDocument(getName(), getTags(), getAccessNumber());
    }

}
