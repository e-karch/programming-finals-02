package edu.kit.informatik.documentsorter.model.tree;

import edu.kit.informatik.documentsorter.model.document.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class represents the directory tree of the file system.
 *
 * @author uexnb
 * @version 1.0
 */
public class DirectoryTree {
    private static final String DOCUMENT_NAME_FORMAT = "\"%s\"";
    private static final String OUTPUT_SEPARATOR = "---" + System.lineSeparator();
    private final TreeNode root;

    /**
     * Constructs a new directory tree with the specified root node.
     *
     * @param root the root of the directory tree
     */
    public DirectoryTree(TreeNode root) {
        this.root = root;
    }


    /**
     * Generates the directory tree with the specified root node.
     */
    public void generateTree() {
        root.generateTree();
    }

    /**
     * Returns the required output of the directory tree.
     *
     * @return the output of the directory tree
     */
    public String getOutput() {
        return getFirstPartOfOutput()
                + OUTPUT_SEPARATOR
                + getSecondPartOfOutput();
    }

    private String getFirstPartOfOutput() {
        //prints the information gain of the nodes in the tree
        StringBuilder output = new StringBuilder(getPathsAndInformationGain(root));
        if (!output.toString().isEmpty()) {
            output.append(System.lineSeparator());
        }
        return output.toString();
    }

    private String getPathsAndInformationGain(TreeNode node) {
        StringBuilder output = new StringBuilder();
        if (!node.isLeaf()) {
            // Add copies of all potential paths to the list
            List<Path> pathsAtTheSameNode = node.getPotentialPathsSortedByInformationGain().stream()
                    .map(Path::copy)
                    .toList();
            //append all the tags of the paths with their information gain to the output
            for (Path path : pathsAtTheSameNode) {
                if (!output.isEmpty()) {
                    output.append(System.lineSeparator());
                }
                output.append(path.getInformationGainAsString());
            }
            //proceed recursively for the remaining tree
            node.getChildren().stream()
                    .filter(child -> !child.isLeaf())
                    .map(this::getPathsAndInformationGain)
                    .forEach(child -> output.append(System.lineSeparator()).append(child));
        }
        return output.toString();
    }

    private String getSecondPartOfOutput() {
        //prints the paths to the documents ordered by the likelihood of the path
        return getPathsToDocument(root);
    }
    /**
     * Returns the paths to the documents ordered by the likelihood of the path.
     *
     * @return the paths to the documents ordered by the likelihood of the path
     */
    private String getPathsToDocument(TreeNode node) {
        StringBuilder result = new StringBuilder();
        if (node.isLeaf()) {
            //sort remaining documents lexikographically
            List<Document> documents = new ArrayList<>(node.getRemainingDocuments());
            Collections.sort(documents);
            for (Document document : documents) { //append the path to the document to the output
                result.append(node.getCurrentPath())
                        .append(String.format(DOCUMENT_NAME_FORMAT, document.getName()));
                if (documents.indexOf(document) != documents.size() - 1) {
                    result.append(System.lineSeparator());
                }
            }
        } else { //proceed to the child with the highest likelihood
            for (TreeNode child : node.getChildren()) {
                result.append(getPathsToDocument(child));
                if (node.getChildren().indexOf(child) != node.getChildren().size() - 1) {
                    result.append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

}
