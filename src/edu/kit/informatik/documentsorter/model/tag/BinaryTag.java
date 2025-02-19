package edu.kit.informatik.documentsorter.model.tag;

/**
 * This class represents a binary tag, which can be assigned to a document.
 * A binary tag is a tag which can be either defined or undefined.
 *
 * @see Tag
 * @author uexnb
 * @version 1.0
 */
public class BinaryTag extends Tag {
    private static final String TAG_DEFINED = "defined";
    /**
     * Constructs a new tag with the given name.
     *
     * @param name the name of the tag
     */
    public BinaryTag(String name) {
        super(name, TAG_DEFINED);
    }

    @Override
    public Tag copy() {
        Tag newTag = new BinaryTag(getName());
        for (String value : this.getPossibleValues()) {
            newTag.addPossibleValue(value);
        }
        return newTag;
    }
}
