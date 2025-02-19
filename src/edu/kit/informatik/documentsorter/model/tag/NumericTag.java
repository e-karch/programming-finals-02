package edu.kit.informatik.documentsorter.model.tag;

/**
 * This class represents a numeric tag, which can be assigned to a document.
 * A numeric tag is a tag which has an integer value.
 *
 * @see Tag
 * @see MultivalueTag
 * @author uexnb
 * @version 1.0
 */
public class NumericTag extends MultivalueTag {

    /**
     * Constructs a new tag with the given name and value.
     *
     * @param name the name of the tag
     * @param value the value of the tag
     */
    public NumericTag(String name, String value) {
        super(name, value);
    }

    @Override
    public Tag copy() {
        Tag newTag = new NumericTag(getName(), getValue());
        for (String value : this.getPossibleValues()) {
            newTag.addPossibleValue(value);
        }
        return newTag;
    }
}
