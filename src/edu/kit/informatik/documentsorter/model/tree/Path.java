package edu.kit.informatik.documentsorter.model.tree;


import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.Locale;
import java.util.Objects;

import static edu.kit.informatik.documentsorter.command.LoadCommand.TAG_UNDEFINED_VALUE;


/**
 * This class represents a (sub-)path in the file system.
 *
 * @author uexnb
 * @version 1.0
 */
public class Path implements Comparable<Path> {

    private static final String INFORMATION_GAIN_FORMAT = "%s=%.2f";
    private static final String TAG_FORMAT = "%s=%s/";
    private final String name;
    private final Tag currentTag;
    private final double informationGain;
    private final double likelihood;

    /**
     * Constructs a new path with the specified name and information gain.
     *
     * @param name the name of the path
     * @param informationGain the information gain of the path
     * @param likelihood the likelihood of the path
     * @param currentTag the tag of the path
     */
    public Path(String name, double informationGain, double likelihood, Tag currentTag) {
        this.name = name;
        this.informationGain = informationGain;
        this.likelihood = likelihood;
        this.currentTag = currentTag;
    }

    /**
     * Returns the name of the path.
     *
     * @return the name of the path
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the information gain of the path.
     *
     * @return the information gain of the path
     */
    public double getInformationGain() {
        return informationGain;
    }

    /**
     * Returns the likelihood of the path.
     *
     * @return the likelihood of the path
     */
    public double getLikelihood() {
        return likelihood;
    }


    /**
     * Returns the tag of the path.
     *
     * @return the tag of the path
     */
    public Tag getCurrentTag() {
        return currentTag;
    }

    /**
     * Returns the information gain of the path represented as a string with the correct format.
     *
     * @return the information gain of the path represented as a string with the correct format
     */
    public String getInformationGainAsString() {
        return String.format(Locale.US, INFORMATION_GAIN_FORMAT, name + currentTag.getName(), informationGain);
    }

    /**
     * Returns a copy of the path.
     *
     * @return a copy of the path
     */
    public Path copy() {
        return new Path(name, informationGain, likelihood, currentTag);
    }

    /**
     * Returns a string representation of the previous path and the current tag with the correct format.
     *
     * @return a string representation of the path
     */
    public String getTagAsString() {
        if (currentTag.getValue().equals(TAG_UNDEFINED_VALUE)) { //values should be lowercase
            return String.format(TAG_FORMAT, name + currentTag.getName(), currentTag.getValue().toLowerCase());
        }
        return String.format(TAG_FORMAT, name + currentTag.getName(), currentTag.getValue());
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Path other) {
        return this.getName().compareTo(other.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.getInformationGainAsString().equals(
                ((Path) obj).getInformationGainAsString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
