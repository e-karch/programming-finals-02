package edu.kit.informatik.documentsorter.model.document;

import edu.kit.informatik.documentsorter.model.tag.BinaryTag;
import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.List;
/**
 * This class represents a program (document) in the file system.
 *
 * @see Document
 * @author uexnb
 * @version 1.0
 */
public class ProgramDocument extends Document {
    private static final String EXECUTABLE_TAG_NAME = "executable";
    /**
     * Constructs a new program (document) with the given name, tags and number of hits.
     *
     * @param name the name of the document
     * @param tags the tags of the document
     * @param accessNumber the access number of the document
     */
    public ProgramDocument(String name, List<Tag> tags, int accessNumber) {
        super(name, tags, accessNumber);
    }
    @Override
    protected List<Tag> modifyTag(List<Tag> tags) {
        tags.add(new BinaryTag(EXECUTABLE_TAG_NAME));
        return tags;
    }

    @Override
    public Document copy() {
        return new ProgramDocument(getName(), getTags(), getAccessNumber());
    }
}
