package xyz.dsemikin.wellfedcat.datamodel;

import xyz.dsemikin.wellfedcat.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>
 *     Day menu without date.
 * </p>
 * <p>
 *     It is made of sets of dishes. How these sets play with "public ids", "strong ids" etc?
 * </p>
 *
 * <h2>Dishes, DishStores and dish-equality</h2>
 * <p>
 *     If we consider Dish to be a complex object, i.e. object, which contains
 *     more information, than just its name (e.g. name and suitable meal time),
 *     then usually we want to imply certain restrictions on how the fields
 *     of equal dish objects must relate to each other. E.g. it is normal, to wish,
 *     that the dish is completely identified by its name. But it is also desirable,
 *     that two equal dishes have equal sets of suitable meal-times. In fact we
 *     don't want to consider dishes with same name but different suitable-meal-times
 *     a different objects. Instead we want to prohibit this combination of
 *     objects to exist...
 * </p>
 * <p>
 *     But as soon as we want to apply some restrictions onto "which
 *     combinations of objects are allowed to exist, we immediately need
 *     some context, which would enforce this restriction.
 * </p>
 * <p>
 *     This means, that considering Dish without relation to the DishStore
 *     in our context is meaningless. Though probably the DishStore must be
 *     responsible for all operations on dishes like creation, modification,
 *     deletion etc.
 * </p>
 * <p>
 *     This means also, that we can include terms like "strong id", public-id
 *     etc without really loosing generallity because our store object should
 *     enforce the relations between the fields and rules of equality etc.
 * </p>
 *
 * <h2>Relation of Dishes (from Store) to Menus and Menu Stores</h2>
 * <p>
 *     When considering dishes and menus the questions of order of
 *     modification operations on both arise. What if we use "outdated"
 *     dish object to create or modify menu?
 * </p>
 * <p>
 *     The simplest answer (at least it seems to be simplest) is to consider
 *     dish objects just a references to whatever data is stored in the store
 *     (strong id comes to mind at this point).
 *     Thus, when saving menu in the store, it does not matter, with which
 *     version of dish were used for creation. Just the reference to the
 *     current version of dish will be stored.
 * </p>
 * <p>
 *     The issues related to order (and/or concurrency) of operations one may
 *     try to solve using additional constructs like locks or versioning.
 * </p>
 *
 * <h2>Dish initial design</h2>
 * <p>
 *     With all said, we will create dishes with "strong ids", "public ids"
 *     and names. They will be uniquely identified by strong ids. And it is
 *     enough to consider only strong id for hash and equality, because other
 *     fields will be enforced by the store.
 * </p>
 * <p>
 *     Also Dish class should be contained by the store, so that the constructors
 *     are not public. It is still not clear though, how to deal with multiple
 *     store implementations...
 * </p>
 */
public class DayMenuSimple implements Serializable {

    private LinkedHashSet<Dish> breakfast;
    private LinkedHashSet<Dish> lunch;
    private LinkedHashSet<Dish> supper;

    public DayMenuSimple() {
        breakfast = new LinkedHashSet<>();
        lunch = new LinkedHashSet<>();
        supper = new LinkedHashSet<>();
    }

    public LinkedHashSet<Dish> getBreakfast() {
        return breakfast;
    }

    public LinkedHashSet<Dish> getLunch() {
        return lunch;
    }

    public LinkedHashSet<Dish> getSupper() {
        return supper;
    }

    public void setBreakfast(final List<Dish> breakfast) {
        this.breakfast = new LinkedHashSet<>(breakfast);
    }

    public void setBreakfast(final Dish breakfastDish) {
        breakfast = new LinkedHashSet<>(singletonArrayList(breakfastDish));
    }

    public void setLunch(final List<Dish> lunch) {
        this.lunch = new LinkedHashSet<>(lunch);
    }

    public void setLunch(final Dish lunchDish) {
        lunch = new LinkedHashSet<>(singletonArrayList(lunchDish));
    }

    public void setSupper(List<Dish> supper) {
        this.supper = new LinkedHashSet<>(supper);
    }

    public void setSupper(final Dish supperDish) {
        supper = new LinkedHashSet<>(singletonArrayList(supperDish));
    }

    public boolean isEmpty() {
        return breakfast.isEmpty() && lunch.isEmpty() && supper.isEmpty();
    }

    private ArrayList<Dish> singletonArrayList(final Dish dish) {
        return new ArrayList<>(Collections.singletonList(dish));
    }

    public LinkedHashSet<Dish> get(final MealTime mealTime) {
        return switch (mealTime) {
            case BREAKFAST -> breakfast;
            case LUNCH -> lunch;
            case SUPPER -> supper;
        };
    }

    @Override
    public String toString() {
        BiConsumer<Dish, StringBuilder> appendDish =
                (final Dish dish, final StringBuilder sb) -> {
                    sb
                            .append("[").append(dish.publicId()).append("] ")
                            .append(Utils.translit(dish.name())).append(", ");
                    sb.delete(sb.length()-2, sb.length());
                };

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("B:  ");
        getBreakfast().forEach(dish -> appendDish.accept(dish, stringBuilder));
        stringBuilder.append("\n");

        stringBuilder.append("L:  ");
        getLunch().forEach(dish -> appendDish.accept(dish, stringBuilder));
        stringBuilder.append("\n");

        stringBuilder.append("S:  ");
        getSupper().forEach(dish -> appendDish.accept(dish, stringBuilder));
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
