package edu.kit.informatik.documentsorter.model;

import edu.kit.informatik.documentsorter.model.tree.DirectoryTree;
import edu.kit.informatik.documentsorter.model.tree.Path;
import edu.kit.informatik.documentsorter.model.tree.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the document sorter which sorts the documents of a file in an
 * efficient and customised tree-like structure.
 *
 * @author uexnb
 * @version 1.0
 */
public class FilingFinesse {
    private static final String FILE_SEPARATOR = "/";

    private final List<DocumentFile> allDocumentFiles;

    private DirectoryTree directoryTree;

    /**
     * Constructs a new document sorter.
     */
    public FilingFinesse() {
        allDocumentFiles = new ArrayList<>();
    }

    /**
     * Adds a new document file containing different documents to the document sorter.
     *
     * @param documentFile the document file to be added
     */
    public void addFile(DocumentFile documentFile) {
        allDocumentFiles.add(documentFile);
    }

    /**
     * Returns all document files already added to the document sorter.
     *
     * @return all document files of the document sorter
     */
    public List<DocumentFile> getAllDocumentFiles() {
        return Collections.unmodifiableList(allDocumentFiles);
    }

    /**
     * Generates the directory tree for the specified input file.
     *
     * @param documentFile the document file to be sorted
     */
    public void generateDirectoryTree(DocumentFile documentFile) {
        TreeNode root = new TreeNode(documentFile.getAllTags(), documentFile.getDocuments(), new Path(FILE_SEPARATOR,
                0, 0, null));
        directoryTree = new DirectoryTree(root);
        directoryTree.generateTree();
    }

    /**
     * Returns the directory tree of the document sorter.
     *
     * @return the directory tree of the document sorter
     */
    public DirectoryTree getDirectoryTree() {
        return directoryTree;
    }

}
