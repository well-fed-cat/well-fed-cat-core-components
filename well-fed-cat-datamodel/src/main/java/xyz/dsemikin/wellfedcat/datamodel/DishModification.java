package xyz.dsemikin.wellfedcat.datamodel;

import java.util.Optional;
import java.util.Set;

/**
 * This class is a container to supply information about modifications to apply
 * for the EditableDishStore.update() method.
 */
public class DishModification {
    Optional<String> newName;
    Optional<String> newPublicId;
    Optional<Set<MealTime>> newSuitableMealTimes;

    public DishModification(
            final Optional<String> newName,
            final Optional<String> newPublicId,
            final Optional<Set<MealTime>> newSuitableMealTimes
    ) {
        this.newName = newName;
        this.newPublicId = newPublicId;
        this.newSuitableMealTimes = newSuitableMealTimes;
    }

    public Optional<String> getNewName() {
        return newName;
    }

    public void setNewName(Optional<String> newName) {
        this.newName = newName;
    }

    public Optional<String> getNewPublicId() {
        return newPublicId;
    }

    public void setNewPublicId(Optional<String> newPublicId) {
        this.newPublicId = newPublicId;
    }

    public Optional<Set<MealTime>> getNewSuitableMealTimes() {
        return newSuitableMealTimes;
    }

    public void setNewSuitableMealTimes(Optional<Set<MealTime>> newSuitableMealTimes) {
        this.newSuitableMealTimes = newSuitableMealTimes;
    }
}
