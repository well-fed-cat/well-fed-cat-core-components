package xyz.dsemikin.wellfedcat.datamodel.test;

import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.Dish;

import java.util.ArrayList;
import java.util.List;

import static xyz.dsemikin.wellfedcat.datamodel.MealTime.BREAKFAST;
import static xyz.dsemikin.wellfedcat.datamodel.MealTime.LUNCH;
import static xyz.dsemikin.wellfedcat.datamodel.MealTime.SUPPER;

public class TestDataProvider {

    public List<Dish> expectedDishes() {
        final List<Dish> expectedDishes = new ArrayList<>();

        expectedDishes.add(Dish.make("boiled_eggs", "Boiled Eggs", BREAKFAST, SUPPER));
        expectedDishes.add(Dish.make("sandwich", "Sandwich", BREAKFAST, SUPPER));
        expectedDishes.add(Dish.make("granola", "Granola", BREAKFAST));
        expectedDishes.add(Dish.make("omelette", "Omelette", SUPPER));
        expectedDishes.add(Dish.make("yoghurt", "Yoghurt", BREAKFAST));
        expectedDishes.add(Dish.make("oatmeal", "Oatmeal", SUPPER));
        expectedDishes.add(Dish.make("steak", "Steak", LUNCH));
        expectedDishes.add(Dish.make("pasta_carbonara", "Pasta Carbonara", LUNCH, SUPPER));
        expectedDishes.add(Dish.make("pizza", "Pizza", BREAKFAST, LUNCH, SUPPER));

        return expectedDishes;
    }

    public List<DayMenu> expectedDayMenus() {
        // TODO
        return new ArrayList<>();
    }
}
