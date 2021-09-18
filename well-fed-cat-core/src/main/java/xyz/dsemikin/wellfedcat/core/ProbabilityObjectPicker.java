package xyz.dsemikin.wellfedcat.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class ProbabilityObjectPicker<ObjectType> {

    public static int pick(final Collection<Double> probabilityWeights) {

        final var ranges = new ArrayList<Double>();
        double previousValue = 0D;
        for (Double probabilityWeight : probabilityWeights) {
            final double nextValue = previousValue + probabilityWeight;
            ranges.add(nextValue);
            previousValue = nextValue;
        }

        // At this point `previousValue` is the max value in `ranges`.
        if (previousValue == 0) {
            // This means, that all probabilities are 0, so we cannot return anything.
            throw new IllegalArgumentException("At least one probability weight must be non-zero.");
        }

        double random = ThreadLocalRandom.current().nextDouble(0, previousValue);
        int selectedIdx = -1;
        for (int kk = 0; kk < ranges.size(); ++kk) {
            Double upperRangeBoundary = ranges.get(kk);
            if (random < upperRangeBoundary && 0 < upperRangeBoundary) {
                selectedIdx = kk;
                break;
            }
        }

        return selectedIdx;
    }

}
