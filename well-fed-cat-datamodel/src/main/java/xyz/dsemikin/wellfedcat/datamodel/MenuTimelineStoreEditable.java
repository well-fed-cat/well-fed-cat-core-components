package xyz.dsemikin.wellfedcat.datamodel;

import java.time.LocalDate;

/**
 * <p>
 *     This interface extends {@link MenuTimelineStore} with operations,
 *     which allow modification of menu timelines.
 * </p>
 *
 * <h3>Relation to DishStore</h3>
 * <p>
 *     Even though this java interface does not imply, that dishes
 *     (Dish-objects) used in menu timline are stored in some store
 *     and not particularly in the DishStore. But to avoid unneeded
 *     abstraction we will assume it, because in practice we only
 *     plan to use this MenuTimeLineStore (adn {@link MenuTimelineStoreEditable})
 *     in conjugation with {@link DishStore} or {@link DishStoreEditable}.
 * </p>
 * <p>
 *     To make relation between menu timelines and dish stores well defined
 *     additional constraints on their interaction-behavior are
 *     specified.
 * </p>
 * <ul>
 *     <li>
 *         Dish cannot be deleted from the dish store, if it is used
 *         in menu timeline store.
 *     </li>
 *     <li>
 *         If dish is used in menu timeline store and were used in dish
 *         store, then the changes are seen, if the dish is accessed
 *         from the menu timeline store.
 *     </li>
 *     <li>
 *         Dish can be used in menu even if it is not in the DishStore.
 *         When the menu gets saved in the MenuTimelineStore, then
 *         the dish is also stored in corresponding DishStore and if it
 *         fails for any reason, then saving of the menu should also
 *         fail.
 *     </li>
 *     <li>
 *         Similar, if dish was edited, but not yet saved to the store,
 *         it can be used in the menu. The dish will be saved automatically
 *         in this case. If saving dish fails, then saving the menu also
 *         fails.
 *     </li>
 *     <li>
 *         In previous situation, if the Dish was deleted before saving the
 *         menu, saving it (with the same strong id) should fail (to avoid
 *         problems with treating object with this "old" strong id) and
 *         consequently the saving of menu should also fail.
 *     </li>
 * </ul>
 */
public interface MenuTimelineStoreEditable extends MenuTimelineStore {

    void acceptMenu(final Menu menu, final OnAlreadyDefined onAlreadyDefined);
    void acceptMenu(final DayMenu dayMenu, final OnAlreadyDefined onAlreadyDefined);

    /** @return  {@code true} if menu was removed, {@code false} if date did not have assigned menu. */
    boolean removeMenu(final LocalDate date);

    enum OnAlreadyDefined {
        OVERWRITE,
        SKIP,
        FAIL
    }
}
