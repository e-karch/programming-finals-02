package edu.kit.informatik.documentsorter.model.document;

import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a document in the file system.
 *
 * @author uexnb
 * @version 1.0
 */
public abstract class Document implements Comparable<Document> {
    private final String name;
    private final List<Tag> tags;
    private int accessNumber;
    private double currentProbability;
    /**
     * Constructs a new document with the given name, tags and number of hits.
     *
     * @param name the name of the document
     * @param tags the tags of the document
     * @param accessNumber the access number of the document
     */
    protected Document(String name, List<Tag> tags, int accessNumber) {
        this.name = name;
        this.tags = modifyTag(tags);
        this.accessNumber = accessNumber;
        this.currentProbability = 0;
    }
    /**
     * Modifies specific tags depending on the document type.
     *
     * @param tags the tags which need to be checked if a modification is necessary
     * @return the modified tags
     */
    protected abstract List<Tag> modifyTag(List<Tag> tags);

    /**
     * Returns the name of the document.
     *
     * @return the name of the document
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the tags of the document.
     *
     * @return the tags of the document
     */
    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

    /**
     * Adds a tag to the list of tags of the document.
     *
     * @param tag the tag to add
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }
    /**
     * Returns the access number of the document.
     *
     * @return the access number of the document
     */
    public int getAccessNumber() {
        return accessNumber;
    }
    /**
     * Sets the access number of the document.
     *
     * @param accessNumber the access number of the document
     */
    public void setAccessNumber(int accessNumber) {
        this.accessNumber = accessNumber;
    }
    /**
     * Returns the current probability of choosing this document.
     *
     * @return the current probability of the document
     */
    public double getCurrentProbability() {
        return currentProbability;
    }
    /**
     * Sets the current probability of choosing this document.
     *
     * @param currentProbability the current probability of the document
     */
    public void setCurrentProbability(double currentProbability) {
        this.currentProbability = currentProbability;
    }

    /**
     * Returns a copy of the document.
     *
     * @return a copy of the document
     */
    public abstract Document copy();

    @Override
    public int compareTo(Document other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object obj) {
        //instanceof is used because otherwise there could be different document types whith the same name
        return obj instanceof Document
                && compareTo((Document) obj) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
