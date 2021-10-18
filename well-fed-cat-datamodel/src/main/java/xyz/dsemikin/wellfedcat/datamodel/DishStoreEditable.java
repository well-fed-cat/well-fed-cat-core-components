package xyz.dsemikin.wellfedcat.datamodel;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * <p>
 *     Any implementation should respect these additional requirements described below.
 * </p>
 * <ul>
 * <li>
 *     If the dish with particular public id and name was deleted, it
 *     should be possible to use them again (independently) for other
 *     dishes.
 * </li>
 * <li>
 *     Same is valid, if dish name or public id was changed, - the old
 *     value is allowed to be reused by other dishes.
 * </li>
 * </ul>
 * <p>
 *     Note: Possibility to change and reuse public ids and names
 *     means, that the system does not guarantee validity and unambiguity
 *     of external references, which use those fields. Thus it is not recommended
 *     to use them for this purpose (they were not designed for it). Instead one
 *     should better use strong id, which is guaranteed to be unique and permanent.
 * </p>
 * <p></p>
 *
 *
 * <h2>Interaction with {@code MenuTimelineStore}</h2>
 *
 * <p>
 *     Even though {@code DishStoreEditable} does not explicitly refer to
 *     {@link MenuTimelineStore} or {@link MenuTimelineStoreEditable}, their
 *     behavior together should be explicitly specified to ensure consistent
 *     behavior of different implementations of these stores.
 * </p>
 * <p>
 *     See documentation for {@link MenuTimelineStoreEditable} for additional
 *     constraints interaction of this class with {@link MenuTimelineStore}
 *     and {@link MenuTimelineStoreEditable}.
 * </p>
 * <p></p>
 *
 *
 * <h2>Stores with "staging" and without it</h2>
 *
 * <p>
 *     More thoughts about staging are available here:
 *     <a href="https://well-fed-cat.github.io/2021/09/20/Concurrent-acess-and-staging-problem-for-stores.html">
 *     Concurrent Access And Staging Problem For Stores
 *     </a>
 * </p>
 * <p>
 *     Current implementation will assume "staging" is present, i.e. the Dish objects
 *     are not directly connected to the DB. Instead copy of the data from DB is
 *     created. The object may be modified and then updated version can be stored
 *     again to the DB.
 * </p>
 * <p>
 *     To handle concurrent access we will introduce versioning of the objects and
 *     will prohibit update, if the other update happened since this object was created.
 * </p>
 */
public interface DishStoreEditable extends DishStore {

    /** Create a dish in the store if possible.
     *
     * If dish cannot be created because it violates some "unique"-
     * constraint (e.g. this dish-name is already used), then
     * dish is not added and `false` is returned.
     *
     * @param name      See {@link Dish#name()}
     * @param publicId  See {@link Dish#publicId()}
     * @param suitableForMealTimes  See {@link Dish#suitableForMealTimes()}
     *
     * @return  Dish object representing created dish,
     *          if it was successfully created.
     *          {@code Optional.empty()} if some "unique"-constraint was
     *           violated and thus the dish was not created
     *           (e.g. another dish with this name already
     *           exists. Later another constraints may be
     *           added to the model). If dish was not created
     *           because another problem happened, exception
     *           will be thrown (implementation specific).
     */
    Optional<Dish> create(
            final String publicId,
            final String name,
            final Set<MealTime> suitableForMealTimes
            );

    /** Delete dish identified by its strong id from store, if possible.
     *
     * @param strongId   strong id of the dish to be removed from store.
     * @return   {@link DeleteStatus#SUCCESS}, if dish was removed,
     *           {@link DeleteStatus#DOES_NOT_EXIST}, if dish with given name
     *           does not exist, and
     *           {@link DeleteStatus#USED_IN_MENU_TIMELINE}, if dish cannot
     *           be deleted because it is used in menu timeline store.
     *           If dish cannot be deleted because of some other reason,
     *           some exception will be thrown (implementation specific).
     */
    DeleteStatus delete(final UUID strongId);

    /**
     * Convenience (default) method to delete dish by it's object.
     *
     * Strong ID is used to identify the dish object.
     *
     * @param dish  Dish to be deleted.
     * @return See {@link #delete(UUID)}
     */
    default DeleteStatus delete(final Dish dish) {
        return delete(dish.strongId());
    }

    /**
     * Using this method it is possible to change some parameters of the dish.
     *
     * <p>
     *     After update the dish object should not be used anymore in any context.
     *     Instead new version of the object should be fetch from the store
     *     using strongId.
     * </p>
     * @param dish  Dish to be modified
     * @param dishModification  Modification to be applied.
     * @return See description of the {@link UpdateStatus} values. If update cannot be
     *         done because of some reason not covered by this enum, then implementation
     *         specific exception is thrown (connection error etc.).
     */
    UpdateStatus updateDish(final Dish dish, final DishModification dishModification);
    // TODO: Should this method return new version of the dish? Need to check, how it will actually be used.

    /**
     * This method can be used to detect if dish was deleted.
     *
     * @param strongId - Strong ID of dish to check.
     * @return - Dish state.
     */
    DishState getDishState(final UUID strongId);

    enum DeleteStatus {
        /** Dish was successfully removed. */
        SUCCESS,
        /** Dish was not removed, because it does not exist. */
        DOES_NOT_EXIST,
        /** Dish was not removed, because it is referenced (used) in menu timeline store. */
        USED_IN_MENU_TIMELINE
    }

    enum UpdateStatus {
        /** Dish was updated successfully. */
        SUCCESS,
        /**
         * Version of the new dish must be version of the original dish +1.
         * This code is returned, if this condition does not hold (usually
         * happens, if tried to update from the same version more than one
         * time).
         */
        VERSION_MISMATCH,
        /**
         * Dish with the same public id was not found in the store.
         * Probably it was deleted.
         */
        NOT_FOUND
    }

}
