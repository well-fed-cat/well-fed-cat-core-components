package xyz.dsemikin.wellfedcat.datamodel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>
 *     See important notes about implementation of {@code DishStore}
 *     and {@code DishStoreEditable} in the descripton of the
 *     {@link DishStoreEditable} interface.
 * </p>
 *<p>
 *     Note, that this interface does not provide method to determine, if
 *     some dish was deleted (which is logical, because it does not support
 *     notion of deleted dish). Such method is provided by {@link DishStoreEditable}.
 *</p>
 *
 *
 * @see DishStoreEditable
 * @see xyz.dsemikin.wellfedcat.datamodel
 */
public interface DishStore {

    /** Returns all dishes in store as a list
     *
     * @return List of all dishes in the store. List is empty if
     *         store is empty.
     */
    List<Dish> all();

    /** Get dish by name.
     *
     * @param name - name of the dish to get.
     *
     * @return - Empty `Optional`, if store does not contain dish with
     *           such a name. Otherwise `Optional` filled with found
     *           object.
     */
    Optional<Dish> getByName(final String name);

    /**
     * Get dish from store by its public id.
     *
     * @param publicId  public id, which identifies the dish to get.
     * @return  dish from store with given public id.
     */
    Optional<Dish> getByPublicId(final String publicId);

    /**
     * Get dish from store by its strong id. This method should be used by the application
     * by default.
     *
     * @param strongId - Strong ID, identifying the dish.
     * @return - Optional of the found dish or Optional.empty(), if dish does not exists
     *           (including the case, when dish was deleted).
     */
    Optional<Dish> getByStrongId(final UUID strongId);
}
