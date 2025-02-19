package edu.kit.informatik.documentsorter.model;

import edu.kit.informatik.documentsorter.model.document.Document;
import edu.kit.informatik.documentsorter.model.tag.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a file containing a list of names
 * of documents and information about the documents.
 *
 * @author uexnb
 * @version 1.0
 */
public class DocumentFile {
    private static int numberOfFiles = 0;
    private final int identifier;
    private final List<Tag> allTags;
    private final int accessNumber;
    private final List<Document> documents;

    /**
     * Constructs a new file with the given tags, documents and access number.
     *
     * @param allTags the tags of the file
     * @param accessNumber the access number of the file
     * @param documents the documents in the file
     */
    public DocumentFile(List<Tag> allTags, int accessNumber, List<Document> documents) {
        this.identifier = numberOfFiles;
        this.allTags = new ArrayList<>(allTags);
        this.accessNumber = accessNumber;
        this.documents = new ArrayList<>(documents);
        numberOfFiles++;
        setProbabilitiesOfDocuments();
    }
    private void setProbabilitiesOfDocuments() {
        for (Document document : documents) {
            document.setCurrentProbability(((double) document.getAccessNumber()) / accessNumber);
        }
    }
    /**
     * Returns the identifier of the file.
     *
     * @return the identifier of the file
     */
    public int getIdentifier() {
        return identifier;
    }
    /**
     * Returns the tags of the file.
     *
     * @return the tags of the file
     */
    public List<Tag> getAllTags() {
        return Collections.unmodifiableList(allTags);
    }
    /**
     * Returns the documents in the file.
     *
     * @return the documents in the file
     */
    public List<Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }
    /**
     * Returns the number of files.
     *
     * @return the number of files
     */
    public static int getNumberOfFiles() {
        return numberOfFiles;
    }
}
