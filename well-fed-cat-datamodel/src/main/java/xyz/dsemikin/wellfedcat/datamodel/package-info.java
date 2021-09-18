/**
 * <h1>Summary</h1>
 *
 * <p>
 * This package provides classes, which define core concepts and interfaces
 * used in well-fed-cat software.
 * </p>
 *
 * <p>
 * Particularly the terms (classes) {@link xyz.dsemikin.wellfedcat.datamodel.Dish}
 * and {@link xyz.dsemikin.wellfedcat.datamodel.DayMenu} are defined.
 * </p>
 *
 * <p>
 * Also the "store" interfaces are defined, which document, required
 * capabilities of the constructs for storing dishes and menus.
 * </p>
 *
 * <p></p>
 *
 * <h1>{@code DishStore} and {@code MenuTimelineStore} interdependency </h1>
 *
 * <p>
 * <em>IMPORTANT:</em> even though definition of {@code Dish} does not
 * directly refer to "menu" or "menu timeline" in any way and definition
 * of {@code DishStore} does not refer to definition of
 * {@code MenuTimelineStore}, still those two stores are semantically strongly
 * related. Without defining this relation one cannot guarantee, that the
 * behavior of the compliant implementations will be well-determined.
 * This is incomplete list of aspects, which needs to be defined:
 * <ul>
 *     <li>
 *         When the dish is deleted from the store, what happens with the
 *         menus in the menu timeline, which refer to this dish? Is it even
 *         allowed?
 *     </li>
 *     <li>
 *         If the dish is deleted from the dish-store, while being used by
 *         some of the menus in the menu-timeline? Should the menus refer to
 *         the updated version of the dish or should they preserve copy of the
 *         original dish definition?
 *     </li>
 * </ul>
 * </p>
 *
 * @see xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable
 * @see xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable
 *
 */
package xyz.dsemikin.wellfedcat.datamodel;
