package xyz.dsemikin.wellfedcat.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

public class DayMenu implements Serializable {

    private DayMenuSimple dayMenu;
    private LocalDate date;

    public DayMenu(final LocalDate date) {
        dayMenu = new DayMenuSimple();
        this.date = date;
    }

    /** Sets `date` of this object to given `date` and `dayOfWeek` of this object to corresponding day of week. */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public LinkedHashSet<Dish> get(final MealTime mealTime) {
        return dayMenu.get(mealTime);
    }

    public LinkedHashSet<Dish> getBreakfast() {
        return dayMenu.getBreakfast();
    }

    public LinkedHashSet<Dish> getLunch() {
        return dayMenu.getLunch();
    }

    public LinkedHashSet<Dish> getSupper() {
        return dayMenu.getSupper();
    }

    public void setBreakfast(final List<Dish> breakfast) {
        dayMenu.setBreakfast(breakfast);
    }

    public void setBreakfast(final Dish breakfastDish) {
        dayMenu.setBreakfast(breakfastDish);
    }

    public void setLunch(final List<Dish> lunch) {
        dayMenu.setLunch(lunch);
    }

    public void setLunch(final Dish lunchDish) {
        dayMenu.setLunch(lunchDish);
    }

    public void setSupper(List<Dish> supper) {
        dayMenu.setSupper(supper);
    }

    public void setSupper(final Dish supperDish) {
        dayMenu.setSupper(supperDish);
    }

    public boolean isEmpty() {
        return dayMenu.isEmpty();
    }

    @Override
    public String toString() {
        return date.toString() + "\n" + dayMenu.toString();
    }
}
