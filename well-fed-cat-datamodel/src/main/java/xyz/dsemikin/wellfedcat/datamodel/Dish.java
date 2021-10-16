package xyz.dsemikin.wellfedcat.datamodel;

import xyz.dsemikin.wellfedcat.utils.Utils;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * <p>
 *     Some ideas about {@code Dish} design are available
 *     <a href="https://well-fed-cat.github.io/2021/09/21/Design-of-dish-and-dish-store.html">here</a>.
 * </p>
 */
public class Dish implements Serializable
{
    private final UUID strongId;
    private final String publicId;
    private final String name;
    private final Set<MealTime> suitableForMealTimes;
    private final int version;

    /**
     * Max supported dish name length (in characters) - also to be supported by
     * {@link DishStore} implementations.
     */
    public static final int MAX_DISH_NAME_LENGTH = 100; // must be coherent with DB schema

    /**
     * Max supported dish public id length (in characters) - also to be supported by
     * {@link DishStore} implementations.
     */
    public static final int MAX_DISH_PUBLIC_ID_LENGTH = 100;

    /**
     * The constructor is only supposed to be used by DishStore implementations.
     * Compliant DishStore implementations should not allow saving Dish objects
     * created externally, so creating Dishes without help of DishStore should
     * be useless.
     *
     * @param strongId              see {@link #strongId()}
     * @param publicId              see {@link #publicId()}
     * @param name                  see {@link #name()}
     * @param suitableForMealTimes  see {@link #suitableForMealTimes()}
     * @param version               see {@link #version()}
     */
    public Dish(
            final UUID strongId,
            final String publicId,
            final String name,
            final Set<MealTime> suitableForMealTimes,
            final int version
    ) {
        this.strongId = strongId;
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
            throw new IllegalArgumentException("publicId must be composed of digits, ascii chars or underscore. Current value is (excluding enclosing quotes) \"" + publicId + "\"");
        }

        if (publicId.length() > MAX_DISH_PUBLIC_ID_LENGTH) {
            throw new IllegalArgumentException("Max allowed length of dish public id is " + MAX_DISH_PUBLIC_ID_LENGTH + ". Current value is " + publicId.length());
        }

        if (name.length() > MAX_DISH_NAME_LENGTH) {
            throw new IllegalArgumentException("Max allowed length of dish name is " + MAX_DISH_NAME_LENGTH + ". Current value is " + name.length());
        }

        if (version <= 0) {
            throw new IllegalArgumentException("Version must be greater than zero. Current value is " + version);
        }
    }

    /**
     * <p>
     *     strongId is supposed to be permanent, quasi-globally-unique id
     *     of the Dish, i.e. it is designed in such a way, that there should
     *     be no other dish with the same strongId, and strongId of this
     *     dish will never change.
     * </p>
     * <p>
     *     This ID may be stored outside of this system for arbitrarily long
     *     and it is guaranteed, that if it is used again in the system, then
     *     either the same dish will be returned, or the dish will not be
     *     returned, if it was deleted.
     * </p>
     *
     * @return strongId of the dish.
     */
    public UUID strongId() {
        return this.strongId;
    }

    /**
     * @return  Name of the dish. Has virtually no restrictions on its
     *          content except length (should be printable unicode symbols though).
     *
     * @see Dish#MAX_DISH_NAME_LENGTH
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
     *     Main purpose is to access dishes in the store while
     *     using well-fed-cat interactively from console (e.g.
     *     from jshell or groovy console), which maybe more handy, then
     *     using name (which can be complicated and use non-ascii characters).
     * </p>
     * <p>
     *     Unique within the dish store. Max length is limited.
     * </p>
     *
     * @return  public id.
     *
     * @see Dish#MAX_DISH_PUBLIC_ID_LENGTH
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
        final StringBuilder string = new StringBuilder(strongId() + "[" + version() + "]:" + publicId() + " : " + Utils.translit(name()) + " : ");
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
        if (!dish.strongId().equals(this.strongId())) {
            return false;
        }
        if (dish.version() != this.version()) {
            final String message = "The strongId of the compared objects are the same (i.e. it is the same object), " +
                    "but versions are different. It is prohibited to use different versions of the same object " +
                    "in one context. Strong ID: " + strongId() + ", this.version: " + this.version() + ", " +
                    "other.version: " + dish.version();
            throw new IllegalArgumentException(message);
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strongId);
    }
}
