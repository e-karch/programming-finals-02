package edu.kit.informatik.documentsorter.model.tree.comparator;

import edu.kit.informatik.documentsorter.model.tree.Path;
import java.util.Comparator;

/**
 * This class represents a comparator to compare paths by their information gain.
 *
 * @author uexnb
 * @version 1.0
 */
public class InformationGainComparator implements Comparator<Path> {
    /**
     * The epsilon for the comparison of the information gain.
     * Package-private because it is used in {@link LikelihoodComparator}.
     */
    static final double EPSILON = 0.00001d;

    @Override
    public int compare(Path path1, Path path2) {
        if (Math.abs(path1.getInformationGain() - path2.getInformationGain()) < EPSILON) {
            //compare tag names lexikographically
            return path1.getCurrentTag().getName().compareTo(path2.getCurrentTag().getName());
        }
        return Double.compare(path2.getInformationGain(), path1.getInformationGain());
    }
}
