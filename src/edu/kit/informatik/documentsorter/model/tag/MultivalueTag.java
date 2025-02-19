package edu.kit.informatik.documentsorter.model.tag;

/**
 * This class represents a multivalue tag, which can be assigned to a document.
 * A multivalue tag is a tag that can assume one of several explicitly specified values.
 *
 * @see Tag
 * @author uexnb
 * @version 1.0
 */
public class MultivalueTag extends Tag {

    /**
     * Constructs a new tag with the given name and value.
     *
     * @param name the name of the tag
     * @param value the value of the tag
     */
    public MultivalueTag(String name, String value) {
        super(name, value);
    }

    @Override
    public Tag copy() {
        Tag newTag = new MultivalueTag(getName(), getValue());
        for (String value : this.getPossibleValues()) {
            newTag.addPossibleValue(value);
        }
        return newTag;
    }
}
