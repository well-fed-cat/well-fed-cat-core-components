package xyz.dsemikin.wellfedcat.core;

import com.ibm.icu.text.Transliterator;
import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.Menu;

import java.time.LocalDate;
import java.util.List;

public class DishUtils {

    // TODO: Probably use Utils.translit() and remove external dependency

    public static void printDishes(final List<Dish> dishes) {
        for (Dish dish : dishes) {
            System.out.println(dish.toString());
        }
    }

    public static void printMenu(final Menu menu) {
        System.out.println();
        for (DayMenu dayMenu : menu) {
            StringBuilder stringBuilder = new StringBuilder();
            LocalDate date = dayMenu.getDate();
            stringBuilder.append(date).append(" - ").append(date.getDayOfWeek());
            System.out.println(stringBuilder);
            System.out.print("Breakfast:");
            for (var dish : dayMenu.getBreakfast()) {
                System.out.print(" " + dish.name() + ",");
            }
            System.out.print("\n");

            System.out.print("Lunch:");
            for (var dish : dayMenu.getLunch()) {
                System.out.print(" " + dish.name() + ",");
            }
            System.out.print("\n");

            System.out.print("Supper:");
            for (var dish : dayMenu.getSupper()) {
                System.out.print(" " + dish.name() + ",");
            }
            System.out.print("\n");
            System.out.println();
        }
    }

    public static void printMenuT(final Menu menu) {

        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");

        System.out.println();
        for (DayMenu dayMenu : menu) {
            StringBuilder stringBuilder = new StringBuilder();
            LocalDate date = dayMenu.getDate();
            stringBuilder.append(date).append(" - ").append(date.getDayOfWeek());
            System.out.println(stringBuilder);
            System.out.print("Breakfast:");
            for (var dish : dayMenu.getBreakfast()) {
                System.out.print(" " + transliterator.transliterate(dish.name()) + ",");
            }
            System.out.print("\n");

            System.out.print("Lunch:");
            for (var dish : dayMenu.getLunch()) {
                System.out.print(" " + transliterator.transliterate(dish.name()) + ",");
            }
            System.out.print("\n");

            System.out.print("Supper:");
            for (var dish : dayMenu.getSupper()) {
                System.out.print(" " + transliterator.transliterate(dish.name()) + ",");
            }
            System.out.print("\n");
            System.out.println();
        }
    }

}
