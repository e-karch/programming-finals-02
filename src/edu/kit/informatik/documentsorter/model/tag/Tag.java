package edu.kit.informatik.documentsorter.model.tag;

import edu.kit.informatik.documentsorter.command.LoadCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * This class represents a tag, which can be assigned to a document.
 *
 * @author uexnb
 * @version 1.0
 */
public abstract class Tag implements Comparable<Tag> {
    private String name;
    private String value;
    private final List<String> possibleValues;
    /**
     * Constructs a new generic tag with the given name and value.
     *
     * @param name the name of the tag
     * @param value the value of the tag
     */
    protected Tag(String name, String value) {
        this.name = name.toLowerCase();
        this.value = value;
        this.possibleValues = new ArrayList<>();
        possibleValues.add(value);
        possibleValues.add(LoadCommand.TAG_UNDEFINED_VALUE);
    }

    /**
     * Returns the name of the tag.
     *
     * @return the name of the tag
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tag.
     *
     * @param name the new name of the tag
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the value of the tag.
     *
     * @return the value of the tag
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the tag.
     *
     * @param value the new value of the tag
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * Returns the possible values of a tag with this name.
     *
     * @return the possible values of a tag with this name
     */
    public List<String> getPossibleValues() {
        return Collections.unmodifiableList(possibleValues);
    }
    /**
     * Adds a possible value to the tag.
     *
     * @param value the value to add
     */
    public void addPossibleValue(String value) {
        if (!possibleValues.contains(value)) {
            possibleValues.add(value);
        }
    }
    /**
     * Returns a copy of the tag.
     *
     * @return a copy of the tag
     */
    public abstract Tag copy();
    @Override
    public int compareTo(Tag other) {
        return this.getName().compareTo(other.getName());
    }
    @Override
    public boolean equals(Object other) {
        //instanceof is used because otherwise there could be different tag types with the same name
        return  other instanceof Tag
                && this.getName().equals(((Tag) other).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
