package xyz.dsemikin.wellfedcat.datamodel.test;

import org.junit.jupiter.api.Test;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;
import xyz.dsemikin.wellfedcat.datamodel.test.TestDataProvider.DishCreationData;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class provides implementation of tests for checking
 * implementations of DishStore interface. Specific implementations
 * of the tests (provided for specific implementations of the
 * interface) should close some missing gaps in test fixtures,
 * like filling in the store. Content for this is provided by
 * this class in protected methods.
 */
public abstract class DishStoreTestBase {

    /**
     * Implement this in the specific test implementations to provide DishStore object under the test.
     *
     * <p>
     *     It is expected, that returned DishStore is populated with data provided by {@link #expectedDishes()}
     *     method.
     * </p>
     */
    protected abstract DishStore getDishStore();

    /**
     * This method returns list of Dish objects expected to be
     * stored in the DishStore for the tests (and ONLY these
     * Dishes).
     *
     * Implementations of this class must use this method to
     * populate the store.
     */
    protected List<DishCreationData> expectedDishes() {
        return new TestDataProvider().expectedDishes();
    }

    @Test
    public void test_all() {
        final DishStore dishStore = getDishStore();
        List<Dish> allDishes = dishStore.all();
        Set<Dish> actualDishes = new HashSet<>(allDishes);
        Set<DishCreationData> actualDishData = actualDishes.stream().map(this::makeDishData).collect(Collectors.toSet());
        Set<DishCreationData> expectedDishes = new HashSet<>(expectedDishes());

        assertEquals(expectedDishes().size(), actualDishes.size());
        assertEquals(expectedDishes, actualDishData);
    }

    @Test
    public void test_getByName_exists() {
        final String dishName = "Pasta Carbonara";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getByName(dishName);
        assertTrue(maybeActualDish.isPresent());
        final Dish actualDish = maybeActualDish.get();

        //noinspection OptionalGetWithoutIsPresent
        final DishCreationData expectedDish = expectedDishes().stream()
                .filter(dish -> dish.name().equals(dishName))
                .findFirst().get();

        assertEquals(expectedDish, makeDishData(actualDish));
    }

    @Test
    public void test_getByName_notExist() {
        final String dishName = "Pasta Bolognese";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getByName(dishName);
        assertTrue(maybeActualDish.isEmpty());
    }

    @Test
    public void test_getById_exists() {
        final String dishId = "granola";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getByPublicId(dishId);
        assertTrue(maybeActualDish.isPresent());
        final Dish actualDish = maybeActualDish.get();

        //noinspection OptionalGetWithoutIsPresent
        final DishCreationData expectedDish = expectedDishes().stream()
                .filter(dish -> dish.publicId().equals(dishId))
                .findFirst().get();

        assertEquals(expectedDish, makeDishData(actualDish));
    }

    @Test
    public void test_getById_notExist() {
        final String dishId = "pelmeni";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getByPublicId(dishId);
        assertTrue(maybeActualDish.isEmpty());
    }

    private DishCreationData makeDishData(final Dish dish) {
        return new DishCreationData(dish.publicId(), dish.name(), dish.suitableForMealTimes());
    }
}
