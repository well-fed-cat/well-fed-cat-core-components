package xyz.dsemikin.wellfedcat.datamodel;

import xyz.dsemikin.wellfedcat.utils.Utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Even though, the notion of "dish" can be considered without referring to
 * {@link DishStore}, or {@link DishStoreEditable}, it is not likely to be
 * used without this context. Thus the traits specific to "store" like e.g.
 * {@code version} are included directly into this class and not to its
 * subclasses.
 */
public class Dish implements Serializable
{
    private final String publicId;
    private final String name;
    private final Set<MealTime> suitableForMealTimes;
    private final int version;

    /**
     * Max supported dish name length (in letters) - also to be supported by
     * {@link DishStore} implementations.
     */
    public static final int MAX_DISH_NAME_LENGTH = 100; // must be coherent with DB schema

    /**
     * Convenience method to create {@code Dish} objects.
     *
     * @param publicId  see {@link #publicId()}
     * @param name  see {@link #name()}
     * @param mealTimes  see {@link #suitableForMealTimes()}. Repeated meal times will be ignored.
     * @return  created object.
     */
    public static Dish make(final String publicId, final String name, MealTime... mealTimes) {
        return new Dish(publicId, name, new LinkedHashSet<>(Arrays.asList(mealTimes)));
    }

    public DishModified updatePublicId(final String newPublicId) {
        return DishModified.updatePublicId(this, newPublicId);
    }

    public DishModified updateName(final String newName) {
        return DishModified.updateName(this, newName);
    }

    public DishModified updateSuitableForMealTimes(final Set<MealTime> suitableForMealTimes) {
        return DishModified.updateSuitableForMealTimes(this, suitableForMealTimes);
    }

    /**
     * Use this constructor or convenience {@link #make(String, String, MealTime...)}
     * method to create new {@code Dish} objects.
     *
     * @param publicId  see {@link #publicId()}
     * @param name  see {@link #name()}
     * @param suitableForMealTimes  see {@link #suitableForMealTimes()}
     */
    public Dish(
            final String publicId,
            final String name,
            final Set<MealTime> suitableForMealTimes
    ) {
        this(publicId, name, suitableForMealTimes, 0);
    }

    /**
     * This constructor is only supposed to be used by methods, which are
     * responsible for recreating objects e.g., when reading data from DB,
     * and must not be used for manual manipulations with version. Otherwise
     * dish store integrity may be corrupted.
     *
     * @param publicId              see {@link #publicId()}
     * @param name                  see {@link #name()}
     * @param suitableForMealTimes  see {@link #suitableForMealTimes()}
     * @param version               see {@link #version()}
     */
    public Dish(
            final String publicId,
            final String name,
            final Set<MealTime> suitableForMealTimes,
            final int version
    ) {
        this.publicId = publicId;
        this.name = name;
        this.suitableForMealTimes = suitableForMealTimes;
        this.version = version;

        // TODO: Check, that arguments are not empty
        final boolean publicIdIsOk =
                publicId.codePoints().allMatch(c ->
                        c < 128 &&
                                (Character.isLetter(c)
                                        || Character.isDigit(c)
                                        || c == Character.codePointAt("_", 0)
                                )
                );
        if (!publicIdIsOk) {
            throw new IllegalArgumentException("publicId must be composed of digits, ascii chars or underscore.");
        }

        if (name.length() > MAX_DISH_NAME_LENGTH) {
            throw new IllegalArgumentException("Max allowed length of dish name is " + MAX_DISH_NAME_LENGTH);
        }
    }

    /**
     * @return  Name of the dish. Has virtually no restrictions on its
     *          content (should be printable unicode symbols though).
     */
    public String name() {
        return name;
    }

    /**
     * <p>
     *     Public ID is a one-word identifier of a dish composed of latin
     *     letters, numbers and underscore symbols.
     * </p>
     * <p>
     *     Unique within the dish store.
     * </p>
     * <p>
     *     By convention it is used in the code to retrieve the dishes from
     *     the store programmatically (even though {@code name} can also be
     *     used for this purpose. E.g. {@link DishModified} will contain
     *     both: old and new values of publicId, if it was changed, while
     *     only new value of the name.
     * </p>
     * <p>
     *     Another purpose is to access dishes in the store while
     *     using well-fed-cat interactively from console (e.g.
     *     from jshell or groovy console).
     * </p>
     *
     * @return  public id.
     */
    public String publicId() {
        return publicId;
    }

    /**
     * @return  List of the meal times for which this dish is
     *          suitable. Used by the algorithms, which
     *          generate menu.
     */
    public Set<MealTime> suitableForMealTimes() {
        return suitableForMealTimes;
    }

    /**
     * @return  Integer number used by the dish store to ensure, that
     *          recent changes will not be overwritten, if two independent
     *          clients try to write modified data simultaneously without
     *          refreshing it first before other-client's write (the
     *          "latter" write will be declined by the store).
     */
    public int version() {
        return version;
    }

    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder(publicId() + " : " + Utils.translit(name()) + " : ");
        for (var mealTime : suitableForMealTimes()) {
            string.append(mealTime).append(", ");
        }
        string.delete(string.length()-2, string.length());
        return string.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return version == dish.version && publicId.equals(dish.publicId) && name.equals(dish.name) && suitableForMealTimes.equals(dish.suitableForMealTimes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicId, name, suitableForMealTimes, version);
    }
}
