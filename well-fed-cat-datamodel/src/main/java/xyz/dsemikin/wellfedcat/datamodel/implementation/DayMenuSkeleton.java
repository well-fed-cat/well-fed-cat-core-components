package xyz.dsemikin.wellfedcat.datamodel.implementation;

import xyz.dsemikin.wellfedcat.datamodel.MealTime;

import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * This class is supposed to be used in implementation of DishStores and MenuTimelineStores,
 * but not be exposed in their API.
 * <p>
 * This class has similar meaning, as DishMenu, but instead of holding
 * references to real Dish objects, it holds strings, which are publicIds
 * of the dishes, so that dishes can be queried from DishStore.
 * <p>
 * Objects of this class are convenient to return by the DAO method, which
 * queries MENU_TIMELINE table, so that no joins with DISH table is needed.
 * Later dishes can be conveniently queried using DishDao.
 * <p>
 * API of this class is also not especially reach. This is because the only
 * purpose of the objects of this class is to be created by MenuTimelineDao
 * and then be immediately transformed into real DayMenu objects with the
 * help of corresponding DishStore.
 */
public record DayMenuSkeleton(
    LinkedHashSet<String> breakfast,
    LinkedHashSet<String> lunch,
    LinkedHashSet<String> supper,
    LocalDate date
) {
    public boolean isEmpty() {
        return breakfast.isEmpty() && lunch.isEmpty() && supper.isEmpty();
    }

    public LinkedHashSet<String> get(MealTime mealTime) {
        return switch (mealTime) {
            case BREAKFAST -> breakfast;
            case LUNCH -> lunch;
            case SUPPER -> supper;
        };
    }
}
