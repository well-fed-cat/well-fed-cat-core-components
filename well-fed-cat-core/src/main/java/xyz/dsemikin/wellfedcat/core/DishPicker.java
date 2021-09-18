package xyz.dsemikin.wellfedcat.core;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DishPicker {

    private static final int DISH_HISTORY_DEPTH = 30;

    private final List<Dish> allDishes;
    private final List<Integer> dishHistory; // Contains indices from `allDishes`

    public DishPicker(final DishStoreEditable dishStore) {
        allDishes = dishStore.all();
        dishHistory = new LinkedList<>(); // TODO: It should be possible to restore it from some storage
    }

    public Dish pickNextDish(final MealTime mealTime) {
        return allDishes.get(pickNextDishIdx(mealTime));
    }

    public Dish pickNextDishAndUpdateHistory(final MealTime mealTime) {
        int pickedDishIdx = pickNextDishIdx(mealTime);
        final Dish pickedDish = allDishes.get(pickedDishIdx);
        updateDishesHistory(pickedDishIdx);
        return pickedDish;
    }

    private int pickNextDishIdx(final MealTime mealTime) {
        final List<Double> probabilityWeightsOfDishes = generateProbabilityWeightsOfDishes(mealTime);
        return ProbabilityObjectPicker.pick(probabilityWeightsOfDishes);
    }

    private List<Double> generateProbabilityWeightsOfDishes(final MealTime mealTime) {
        //noinspection Convert2Diamond
        var probabilityWeights = new ArrayList<Double>(Collections.nCopies(allDishes.size(), 1.0D));
        updateProbabilityWeightsBasedOnHistory(probabilityWeights);
        updateProbabilityWeightsBasedOnMealTime(probabilityWeights, mealTime);
        return probabilityWeights;
    }

    private void updateProbabilityWeightsBasedOnMealTime(
            final List<Double> probabilityWeights,
            final MealTime mealTime
    ) {
        for (int dishNum = 0; dishNum < allDishes.size(); ++dishNum) {
            final Dish dish = allDishes.get(dishNum);
            if (!dish.suitableForMealTimes().contains(mealTime)) {
                probabilityWeights.set(dishNum, 0.0);
            }
        }
    }

    /** parameter `probabilityList` is "inout". The method modifies it in place. */
    private void updateProbabilityWeightsBasedOnHistory(final List<Double> probabilityWeights) {
        for (int kk = 0; kk < dishHistory.size(); kk++) {
            final int dishIdx = dishHistory.get(kk);
            final double epsilon = 0.01; // we don't want, that latest dish has 0 probability.
            final double probabilityWeightMultiplier = 1.0D - 1.0D/(dishHistory.size() - kk + epsilon);
            final double currentProbabilityWeight = probabilityWeights.get(dishIdx);
            final double newProbabilityWeight = probabilityWeightMultiplier * currentProbabilityWeight;
            probabilityWeights.set(dishIdx, newProbabilityWeight);
        }
    }

    public void updateDishesHistory(final int latestDishIdx) {
        dishHistory.add(latestDishIdx);
        if (dishHistory.size() >= DISH_HISTORY_DEPTH) {
            dishHistory.remove(0);
        }
        if (dishHistory.size() >= DISH_HISTORY_DEPTH) {
            throw new IllegalStateException("Internal Error. Dish history size must not exceed DISH_HISTORY_DEPTH.");
        }
    }
}
