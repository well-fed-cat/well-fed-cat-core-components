package xyz.dsemikin.wellfedcat.core;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datastore.file.simple.DishStoreSimpleFile;
import xyz.dsemikin.wellfedcat.datastore.file.simple.StoreObjectProviderSimpleFile;
import xyz.dsemikin.wellfedcat.datastore.inmemory.DishStoreInMemory;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

public class DishStoreProvider {

    public static DishStoreEditable getDishStore() {
        final DishStoreInMemory dishStore = new DishStoreInMemory();
        return initializeDishStoreWithSampleData(dishStore);
    }

    public static DishStoreEditable getDishStoreSimpleFileAndFillWithSampleData(final Path dishStoreFile) {
        StoreObjectProviderSimpleFile storeObjectsProvider = new StoreObjectProviderSimpleFile(dishStoreFile);
        DishStoreSimpleFile dishStore = storeObjectsProvider.getDishStore();
        return initializeDishStoreWithSampleData(dishStore);
    }

    /** Returns the same object, which was passed as input argument. */
    private static DishStoreEditable initializeDishStoreWithSampleData(final DishStoreEditable dishStore) {
        addDishToDishStore(dishStore, "Pasta", "Pasta", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Pizza", "Pizza", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Fish_with_Pesto", "Fish with Pesto", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Kidney_beans_with_meat", "Kidney beans with meat", MealTime.LUNCH);
//        addDishToDishStore(dishStore, "Boiled potato", MealTime.LUNCH);
//        addDishToDishStore(dishStore, "Meat", MealTime.LUNCH);
//        addDishToDishStore(dishStore, "Fish", MealTime.LUNCH);
//        addDishToDishStore(dishStore, "Eggs roasted", MealTime.LUNCH, MealTime.SUPPER);
//        addDishToDishStore(dishStore, "Eggs boiled", MealTime.SUPPER, MealTime.BREAKFAST);
//        addDishToDishStore(dishStore, "Ovsyanaya kasha", MealTime.BREAKFAST);
//        addDishToDishStore(dishStore, "Sandwiches", MealTime.BREAKFAST, MealTime.SUPPER);
//        addDishToDishStore(dishStore, "Tvorog", MealTime.SUPPER);
//        addDishToDishStore(dishStore, "Musli", MealTime.BREAKFAST, MealTime.SUPPER);
//        addDishToDishStore(dishStore, "Yoghurt", MealTime.SUPPER);
        return dishStore;
    }

    private static void addDishToDishStore(
            final DishStoreEditable dishStore,
            final String dishId,
            final String dishName,
            final MealTime... mealTimes
    ) {
        dishStore.add(new Dish(dishId, dishName, new HashSet<>(Arrays.stream(mealTimes).toList())));
    }
}
