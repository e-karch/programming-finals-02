package edu.kit.informatik.documentsorter.model.tree;

import edu.kit.informatik.documentsorter.model.document.Document;
import edu.kit.informatik.documentsorter.model.tag.Tag;
import edu.kit.informatik.documentsorter.model.tree.comparator.InformationGainComparator;
import edu.kit.informatik.documentsorter.model.tree.comparator.LikelihoodComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a node in the directory tree of the file system.
 *
 * @author uexnb
 * @version 1.0
 */
public class TreeNode {

    private static final double MINIMUM_INFORMATION_GAIN = 0.001d;
    private final List<TreeNode> children;
    private final List<Document> remainingDocuments;
    private final List<Tag> remainingTags;
    private final Path currentPath;
    private final List<Path> potentialPathsSortedByInformationGain;
    private final List<Path> nextPathsSortedByLikelihood;
    /**
     * Constructs a new node with the documents of the remaining subset, the current tree path and
     * the remaining tags.
     *
     * @param remainingTags the remaining tags of the node
     * @param remainingDocuments the remaining documents
     * @param currentPath the current path to this node
     */
    public TreeNode(List<Tag> remainingTags,
                    List<Document> remainingDocuments, Path currentPath) {
        this.remainingTags = new ArrayList<>(remainingTags);
        this.children = new ArrayList<>();
        this.remainingDocuments = new ArrayList<>(remainingDocuments);
        setNewProbabilityOfDocuments(remainingDocuments); //set the new probability of the documents depending on the subset
        this.currentPath = currentPath;
        this.potentialPathsSortedByInformationGain = new ArrayList<>();
        this.nextPathsSortedByLikelihood = new ArrayList<>();
    }
    private void setNewProbabilityOfDocuments(List<Document> documents) {
        int accumlatedAccessNumber = documents.stream()
                .mapToInt(Document::getAccessNumber)
                .sum();
        documents.forEach(document ->
                document.setCurrentProbability(document.getAccessNumber() / ((double) accumlatedAccessNumber))
        );
    }
    private double calculateEntropy(List<Document> documents) {
        double entropy = 0;
        for (Document document : documents) {
            double probability = document.getCurrentProbability();
            entropy += probability * (Math.log(probability) / Math.log(2));
        }
        return -entropy;
    }
    private List<List<Document>> getPotentialSubsetsForTag(Tag tag) {
        return tag.getPossibleValues().stream()
                .map(value -> remainingDocuments.stream()
                        .filter(document -> document.getTags().get(document.getTags().indexOf(tag)).getValue().equals(value))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
    private double getLikelihoodForDocumentInSubset(List<Document> potentialSubsetOfTag) {
        double likelihood = 0;
        for (Document document : potentialSubsetOfTag) {
            likelihood += document.getCurrentProbability();
        }
        return likelihood;
    }

    private double calculateUncertaintyForTag(List<List<Document>> potentialSubsetsOfTag) {
        double uncertainty = 0;
        for (List<Document> potentialSubset : potentialSubsetsOfTag) {
            //calculate the likelihood for a document in this subset of the current tag
            double likelihoodOfSubset = getLikelihoodForDocumentInSubset(potentialSubset);
            setNewProbabilityOfDocuments(potentialSubset);
            double entropyOfSubset = calculateEntropy(potentialSubset);
            uncertainty += likelihoodOfSubset * entropyOfSubset;
            //reset the probability of the documents
            setNewProbabilityOfDocuments(remainingDocuments);

        }
        return uncertainty;
    }
    private double calculateInformationGainForTag(double uncertaintyForTag) {
        return calculateEntropy(remainingDocuments) - uncertaintyForTag;
    }
    private void setNextPaths() {
        for (Tag tag : remainingTags) {
            List<List<Document>> potentialSubsetsOfTag = getPotentialSubsetsForTag(tag);
            double uncertaintyForTag = calculateUncertaintyForTag(potentialSubsetsOfTag);
            double informationGainForTag = calculateInformationGainForTag(uncertaintyForTag);
            if (informationGainForTag >= MINIMUM_INFORMATION_GAIN) {
                potentialPathsSortedByInformationGain.add(new Path(currentPath.getName(),
                        informationGainForTag, 0, tag)); //likelihood to 0 because it is not relevant for this list
            }
        }
        potentialPathsSortedByInformationGain.sort(new InformationGainComparator());
        if (!potentialPathsSortedByInformationGain.isEmpty()) {
            //choose path with highest information gain
            Path path = potentialPathsSortedByInformationGain.get(0);
            List<List<Document>> potentialSubsets = getPotentialSubsetsForTag(path.getCurrentTag());
            for (List<Document> potentialSubset : potentialSubsets) {
                if (potentialSubset.isEmpty()) {
                    continue;
                }
                //calculate the likelihood for a document in this subset of the current tag
                double likelihood = getLikelihoodForDocumentInSubset(potentialSubset);
                //set a new path with the likelihood of the current subset and a copy of the current tag
                //with the same possible values but the current value of the subset
                Tag newTag = path.getCurrentTag().copy();
                //we use the first document of the subset to get the current value of the tag because they all have the same value
                Document firstDocument = potentialSubset.get(0);
                Tag currentTag = path.getCurrentTag();
                int tagIndex = firstDocument.getTags().indexOf(currentTag);
                String tagValue = firstDocument.getTags().get(tagIndex).getValue();
                newTag.setValue(tagValue);
                nextPathsSortedByLikelihood.add(new Path(currentPath.getName(),
                        0, likelihood, newTag));

            }
        }
        nextPathsSortedByLikelihood.sort(new LikelihoodComparator());
    }

    private void addChildren() {
        setNextPaths();
        if (nextPathsSortedByLikelihood.isEmpty()) {
            return;
        }
        List<Tag> newRemainingTags = new ArrayList<>(remainingTags);
        newRemainingTags.remove(nextPathsSortedByLikelihood.get(0).getCurrentTag());
        for (Path path : nextPathsSortedByLikelihood) {
            //get the subset for this tag value
            List<Document> subset = new ArrayList<>();
            Tag currentTag = path.getCurrentTag();
            for (Document document : remainingDocuments) {
                // if the tag value of the document is equal to the value of the current tag
                if (document.getTags().get(document.getTags().indexOf(currentTag)).getValue()
                        .equals(currentTag.getValue())) {
                    subset.add(document);
                }
            }
            TreeNode child = new TreeNode(newRemainingTags, subset, new Path(path.getTagAsString(),
                    0, 0, currentTag));
            children.add(child);
        }
    }

    /**
     * Generates a tree with the current node as root.
     */
    public void generateTree() {
        addChildren();
        if (children.isEmpty()) {
            return;
        }
        for (TreeNode child : children) {
            child.generateTree();
        }
    }
    /**
     * Returns the documents of the current subset.
     *
     * @return the documents of the current subset
     */
    public List<Document> getRemainingDocuments() {
        return Collections.unmodifiableList(remainingDocuments);
    }

    /**
     * Returns the children of the node.
     *
     * @return the children of the node
     */
    public List<TreeNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Returns the potential paths sorted by information gain.
     *
     * @return the potential paths sorted by information gain
     */
    public List<Path> getPotentialPathsSortedByInformationGain() {
        return Collections.unmodifiableList(potentialPathsSortedByInformationGain);
    }

    /**
     * Returns the current path of the node.
     *
     * @return the current path of the node
     */
    public Path getCurrentPath() {
        return currentPath;
    }

    /**
     * Returns whether the node is a leaf.
     *
     * @return whether the node is a leaf
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }


}
