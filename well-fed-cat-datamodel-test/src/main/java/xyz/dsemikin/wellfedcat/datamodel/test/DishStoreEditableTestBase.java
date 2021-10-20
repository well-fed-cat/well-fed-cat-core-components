package xyz.dsemikin.wellfedcat.datamodel.test;

import org.junit.jupiter.api.Test;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.test.TestDataProvider.DishCreationData;

import java.util.List;

public abstract class DishStoreEditableTestBase {

    /**
     * Implement this in the specific test implementations to provide DishStore object under the test.
     *
     * <p>
     *     It is expected, that returned DishStore is populated with data provided by {@link #expectedDishes()}
     *     method.
     * </p>
     *
     * <p>
     *     If DishStore and MenuTimelineStore are dependent
     *     (like for SQL DB based store), both stores should
     *     be created and filled, so that the interdependencies
     *     are properly captured.
     * </p>
     */
    protected abstract DishStoreEditable getDishStore();

    /**
     * This method returns list of Dish objects expected to be
     * stored in the DishStore for the tests (and ONLY these
     * Dishes).
     *
     * Implementations of this class must use this method to
     * populate the store.
     */
    private List<DishCreationData> expectedDishes() {
        return new TestDataProvider().expectedDishes();
    }

    @Test
    public void test_add_notExists() {
//        final DishStoreEditable dishStore = getDishStore();
//        final String hamburgerId = "hamburger";
//        final String hamburgerName = "Hamburger";
//        final Dish hamburger = Dish.make(hamburgerId, hamburgerName, LUNCH);
//
//        assertFalse(dishStore.all().contains(hamburger));
//
//        final boolean addSuccessful = dishStore.add(hamburger);
//        assertTrue(addSuccessful);
//
//        final Optional<Dish> maybeHamburgerById = dishStore.getById(hamburgerId);
//        assertTrue(maybeHamburgerById.isPresent());
//        final Dish hamburgerById = maybeHamburgerById.get();
//        assertEquals(hamburger, hamburgerById);
//
//        assertTrue(dishStore.all().contains(hamburger));
//        assertTrue(dishStore.getById(hamburgerId).isPresent());
//        assertTrue(dishStore.getByName(hamburgerName).isPresent());
    }

    @Test
    public void test_add_dishExist() {
//        final DishStoreEditable dishStore = getDishStore();
//        //noinspection OptionalGetWithoutIsPresent
//        final Dish existingDish = dishStore.getById("sandwich").get();
//
//        final boolean addSuccessful = dishStore.add(existingDish);
//        assertFalse(addSuccessful);
//
//        // We know, that exactly this dish was present, so it should still be there
//        assertTrue(dishStore.all().contains(existingDish));
    }

    @Test
    public void test_add_publicIdExists() {
//        final DishStoreEditable dishStore = getDishStore();
//        final Dish dishWithExistingId = Dish.make("granola", "Granola111", BREAKFAST);
//        final boolean addSuccessful = dishStore.add(dishWithExistingId);
//        assertFalse(addSuccessful);
//
//        // We know from test data, that this dish is not in store
//        // (only other dish with same publicId).
//        assertFalse(dishStore.all().contains(dishWithExistingId));
//        // Dish with this publicId exists
//        assertTrue(dishStore.getById(dishWithExistingId.publicId()).isPresent());
//        // But dish with this name - not
//        assertTrue(dishStore.getByName(dishWithExistingId.name()).isEmpty());
    }

    @Test
    public void test_add_nameExists() {
//        final DishStoreEditable dishStore = getDishStore();
//        final Dish dishWithExistingName = Dish.make("steak1", "Steak", LUNCH, SUPPER);
//        final boolean addSuccessful = dishStore.add(dishWithExistingName);
//        assertFalse(addSuccessful);
//
//        // Exactly this dish does not exist in store
//        assertFalse(dishStore.all().contains(dishWithExistingName));
//        // Dish with this publicId does not exist
//        assertTrue(dishStore.getById(dishWithExistingName.publicId()).isEmpty());
//        // Dish with this name exists
//        assertTrue(dishStore.getByName(dishWithExistingName.name()).isPresent());
    }

    @Test
    public void test_removeByName_notExist() {
        // TODO
    }

    @Test
    public void test_removeByName_then_add_withSameName_noDependentMenuTimeone() {
        // TODO
    }

    @Test
    public void test_removeByName_then_add_withSameName_withDependentMenuTimeone() {
        // TODO
    }

    @Test
    public void test_removeById_exists_noDependentMenuTimeline() {
        // TODO
    }

    @Test
    public void test_removeById_exists_withDependentMenuTimeline() {
        // TODO
    }

    @Test
    public void test_removeById_notExists() {
        // TODO
    }

    @Test
    public void test_removeById_then_add_withSameId_noDependentMenuTimeline() {
        // TODO
    }

    @Test
    public void test_removeById_then_add_withSameId_withDependentMenuTimeline() {
        // TODO
    }
}
