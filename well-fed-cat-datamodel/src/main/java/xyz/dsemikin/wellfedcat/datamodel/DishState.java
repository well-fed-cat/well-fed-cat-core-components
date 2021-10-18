package xyz.dsemikin.wellfedcat.datamodel;

public enum DishState {
    /** Dish exists in the store (and is not deleted). */
    DISH_EXISTS,
    /** Dish never existed in this store. */
    DISH_DOES_NOT_EXIST,
    /** Dish existed in this store, but was deleted. */
    DISH_IS_DELETED
}
