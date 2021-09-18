package xyz.dsemikin.wellfedcat.core;

import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datamodel.Menu;

import java.time.LocalDate;

public class MenuManager {

    private final DishPicker dishPicker;

    public MenuManager(final DishStoreEditable dishStore) {
        dishPicker = new DishPicker(dishStore);
    }

    public void fillMenu(final Menu menu) {
        for (DayMenu dayMenu : menu) {
            dayMenu.setBreakfast(pickNextDish(MealTime.BREAKFAST));
            dayMenu.setLunch(pickNextDish(MealTime.LUNCH));
            dayMenu.setSupper(pickNextDish(MealTime.SUPPER));
        }
    }

    public Menu generateMenu(final LocalDate startDay, final int daysCount) {
        Menu menu = new Menu();
        LocalDate currentDay = startDay;
        for (int kk = 0; kk < daysCount; kk++) {
            menu.add(new DayMenu(currentDay));
            currentDay = currentDay.plusDays(1);
        }
        fillMenu(menu);
        return menu;
    }

    public Menu generateWeekMenu(final LocalDate startDay) {
        return generateMenu(startDay, 7);
    }

    private Dish pickNextDish(final MealTime mealTime) {
        return dishPicker.pickNextDishAndUpdateHistory(mealTime);
    }
}
