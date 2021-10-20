package xyz.dsemikin.wellfedcat.datamodel.test;

import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static xyz.dsemikin.wellfedcat.datamodel.MealTime.BREAKFAST;
import static xyz.dsemikin.wellfedcat.datamodel.MealTime.LUNCH;
import static xyz.dsemikin.wellfedcat.datamodel.MealTime.SUPPER;

public class TestDataProvider {

    public List<DishCreationData> expectedDishes() {
        final List<DishCreationData> expectedDishes = new ArrayList<>();

        // TODO: Fix it
        expectedDishes.add(makeDishData("boiled_eggs", "Boiled Eggs", BREAKFAST, SUPPER));
        expectedDishes.add(makeDishData("sandwich", "Sandwich", BREAKFAST, SUPPER));
        expectedDishes.add(makeDishData("granola", "Granola", BREAKFAST));
        expectedDishes.add(makeDishData("omelette", "Omelette", SUPPER));
        expectedDishes.add(makeDishData("yoghurt", "Yoghurt", BREAKFAST));
        expectedDishes.add(makeDishData("oatmeal", "Oatmeal", SUPPER));
        expectedDishes.add(makeDishData("steak", "Steak", LUNCH));
        expectedDishes.add(makeDishData("pasta_carbonara", "Pasta Carbonara", LUNCH, SUPPER));
        expectedDishes.add(makeDishData("pizza", "Pizza", BREAKFAST, LUNCH, SUPPER));

        return expectedDishes;
    }

    private DishCreationData makeDishData(final String publicId, final String name, final MealTime... suitableMealTimes) {
        Set<MealTime> suitableMealTimesSet = Arrays.stream(suitableMealTimes).collect(Collectors.toSet());
        return new DishCreationData(publicId, name, suitableMealTimesSet);
    }

    public List<DayMenu> expectedDayMenus() {
        // TODO
        return new ArrayList<>();
    }

    public static record DishCreationData(String publicId, String name, Set<MealTime> suitableForMealTimes){}
}
