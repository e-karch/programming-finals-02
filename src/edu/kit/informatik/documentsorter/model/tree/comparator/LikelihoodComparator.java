package edu.kit.informatik.documentsorter.model.tree.comparator;

import edu.kit.informatik.documentsorter.model.tree.Path;

import java.util.Comparator;

/**
 * This class represents a comparator to compare paths by the likelihood of
 * the current subset.
 *
 * @author uexnb
 * @version 1.0
 */
public class LikelihoodComparator implements Comparator<Path> {
    @Override
    public int compare(Path path1, Path path2) {
        if (Math.abs(path1.getLikelihood() - path2.getLikelihood()) < InformationGainComparator.EPSILON) {
            //compare tag values lexikographically
            return path1.getCurrentTag().getValue().compareToIgnoreCase(path2.getCurrentTag().getValue());
        }
        return Double.compare(path2.getLikelihood(), path1.getLikelihood());
    }
}
