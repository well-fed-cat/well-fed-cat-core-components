package xyz.dsemikin.wellfedcat.datamodel;

import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * Objects of this class represent modified dishes, which are
 * intended to be used to update the Dish in the store.
 * </p>
 *
 * <p>
 * The objects of this class are not supposed to be created directly, but instead
 * using the "update"-instance-methods of {@link Dish} class.
 * </p>
 *
 * <p>
 * Objects of this class have alongside normal fields of {@link Dish} object
 * also the {@code oldPublicId}, which is intended to make it possible to
 * identify original object (mainly in the dish store) in case {@code publicId}
 * was modified.
 * </p>
 *
 * <p>
 * Update-methods of this class also automatically handle update of {@code version}.
 * </p>
 */
public class DishModified extends Dish {

    private final String oldPublicId;

    /**
     * <p>
     *     This method exposes possibility to create new {@code DishModified} object
     *     with new public id
     *     and automatically updated version without exposing constructor (i.e. without
     *     exposing direct control over version).
     * </p>
     * <p>
     *     It is recommended to use {@link Dish#updatePublicId(String)} instance methods
     *     instead.
     * </p>
     *
     * @param dish  Dish used as basis for the new object.
     * @param newPublicId  New public id for new object.
     * @return  New object, which equals to {@code dish}, but has {@code newPublicId}.
     */
    public static DishModified updatePublicId(
            final Dish dish,
            final String newPublicId
    ) {
        final int newVersion = newVersion(dish);
        return new DishModified(
                newPublicId,
                dish.name(),
                dish.suitableForMealTimes(),
                newVersion,
                dish.publicId()
        );
    }

    /**
     * <p>
     *     This method exposes possibility to create new {@code DishModified} object
     *     with new name
     *     and automatically updated version without exposing constructor (i.e. without
     *     exposing direct control over version).
     * </p>
     * <p>
     *     It is recommended to use {@link Dish#updateName(String)} instance methods
     *     instead.
     * </p>
     *
     * @param dish  Dish used as basis for the new object.
     * @param newName  New name for new object.
     * @return  New object, which equals to {@code dish}, but has {@code newName}.
     */
    public static DishModified updateName(
            final Dish dish,
            final String newName
    ) {
        final int newVersion = newVersion(dish);
        return new DishModified(
                dish.publicId(),
                newName,
                dish.suitableForMealTimes(),
                newVersion
        );
    }

    /**
     * <p>
     *     This method exposes possibility to create new {@code DishModified} object
     *     with new "suitable for meal times" field
     *     and automatically updated version without exposing constructor (i.e. without
     *     exposing direct control over version).
     * </p>
     * <p>
     *     It is recommended to use {@link Dish#updateSuitableForMealTimes(Set)}
     *     instance methods instead.
     * </p>
     *
     * @param dish  Dish used as basis for the new object.
     * @param suitableForMealTimes  New value for new object.
     * @return  New object, which equals to {@code dish}, but has new value of
     *          {@code suitableForMealTimes}.
     */
    public static DishModified updateSuitableForMealTimes(
            final Dish dish,
            final Set<MealTime> suitableForMealTimes
    ) {
        return new DishModified(
                dish.publicId(),
                dish.name(),
                suitableForMealTimes,
                newVersion(dish)
        );
    }

    private static int newVersion(Dish dish) {
        final int newVersion;
        if (dish instanceof DishModified) {
            newVersion = dish.version();
        } else {
            newVersion =  dish.version() + 1;
        }
        return newVersion;
    }

    private DishModified(
            final String publicId,
            final String name,
            final Set<MealTime> suitableForMealTimes,
            final int version,
            final String oldPublicId
    ) {
        super(publicId, name, suitableForMealTimes, version);
        this.oldPublicId = oldPublicId;
    }

    private DishModified(
            final String publicId,
            final String name,
            final Set<MealTime> suitableForMealTimes,
            final int version
    ) {
        this(publicId, name, suitableForMealTimes, version, publicId);
    }

    public String oldPublicId() {
        return oldPublicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DishModified that = (DishModified) o;
        return oldPublicId.equals(that.oldPublicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oldPublicId);
    }
}
