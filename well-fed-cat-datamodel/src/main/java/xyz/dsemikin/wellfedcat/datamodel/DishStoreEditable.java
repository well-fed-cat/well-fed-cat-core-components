package xyz.dsemikin.wellfedcat.datamodel;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * <p>
 *     Any implementation should respect these additional requirements:
 * </p>
 *
 * <ul>
 *     <li>
 *         If the dish with particular public id and name was deleted, it
 *         should be possible to use them again (independently) for other
 *         dishes.
 *     </li>
 *     <li>
 *         Same is valid, if dish name or public id was changed, - the old
 *         value is allowed to be reused by other dishes.
 *     </li>
 * </ul>
 *
 * <p>
 *     Note: Possibility to change and reuse public ids and names
 *     means, that the system does not enforce validity
 *     of external references to the dishes. If public ID was saved
 *     outside the system and then was used to retrieve the dish,
 *     it is not guaranteed, that the dish will be the same or will
 *     exist at all.
 * </p>
 *
 * <p>
 *     To enforce it we would need to include "constant-quasi-unique-id",
 *     (one could call it "strong-id") which would be generated
 *     in such a way, that it is quasi-globally-unique
 *     (e.g. UUID) and it is not allowed to be reused ever.
 * </p>
 *
 * <h3>Interaction with {@code MenuTimelineStore}</h3>
 * <p>
 *     Even though {@code DishStoreEditable} does not explicitly refer to
 *     {@link MenuTimelineStore} or {@link MenuTimelineStoreEditable}, their
 *     behavior together should be explicitly specified to ensure consistent
 *     behavior of different implementations of these stores.
 *
 *     See documentation for {@link MenuTimelineStoreEditable} for additional
 *     constraints interaction of this class with {@link MenuTimelineStore}
 *     and {@link MenuTimelineStoreEditable}.
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

    /** Delete dish identified by name from store, if possible.
     *
     * <p>Note, that {@link #deleteByStrongId(UUID)} identifies dish
     * more reliably</p>
     *
     * @param name   name of the dish to be removed from store.
     * @return   {@link DeleteStatus#SUCCESS}, if dish was removed,
     *           {@link DeleteStatus#DOES_NOT_EXIST}, if dish with given name
     *           does not exist, and
     *           {@link DeleteStatus#USED_IN_MENU_TIMELINE}, if dish cannot
     *           be deleted because it is used in menu timeline store.
     *           If dish cannot be deleted because of some other reason,
     *           some exception will be thrown (implementation specific).
     */
    DeleteStatus deleteByName(final String name);

    /** Delete dish identified by its public id from store, if possible.
     *
     * <p>Note, that {@link #deleteByStrongId(UUID)} identifies dish
     * more reliably</p>
     *
     * @param publicId   public id of the dish to be removed from store.
     * @return   {@link DeleteStatus#SUCCESS}, if dish was removed,
     *           {@link DeleteStatus#DOES_NOT_EXIST}, if dish with given name
     *           does not exist, and
     *           {@link DeleteStatus#USED_IN_MENU_TIMELINE}, if dish cannot
     *           be deleted because it is used in menu timeline store.
     *           If dish cannot be deleted because of some other reason,
     *           some exception will be thrown (implementation specific).
     */
    DeleteStatus deleteByPublicId(final String publicId);

    /** Delete dish identified by its strong id from store, if possible.
     *
     * Note, that it is the most reliable way of deletion in the sense,
     * that it is not possible, that dish with this strong id was already
     * deleted and then another one was created (because strong id is
     * unique within the store, - no repetitions are allowed even after
     * deletion).
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
    DeleteStatus deleteByStrongId(final UUID strongId);

    /**
     * Convenience (default) method to delete dish by it's object.
     *
     * Strong ID is used to identify the dish object.
     *
     * @param dish  Dish to be deleted.
     * @return See {@link #deleteByStrongId(UUID)}
     */
    default DeleteStatus delete(final Dish dish) {
        return deleteByStrongId(dish.strongId());
    }

    /**
     * Using this method it is possible to change some parameters of the dish.
     *
     * For this one needs to get dish from store and then modify it using
     * "update" methods and then update it in the store using this method.
     *
     * If it is for some reason not possible to update the dish, then
     * implementation specific exceptions may be thrown.
     *
     * Implementation should use {@link DishModified#oldPublicId()} to
     * search for the dish in the store, which is to be updated.
     *
     * @param newDishVersion  updated version of the dish. Obtain by
     *                        getting dish from the store and then using
     *                        "update"-methods on it.
     * @return See description of the {@link UpdateStatus} values.
     */
    UpdateStatus updateDish(final DishModified newDishVersion);

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
