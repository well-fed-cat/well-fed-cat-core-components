package xyz.dsemikin.wellfedcat.datamodel;

/**
 * Base class for all exceptions thrown from {@link DishStore}
 * or {@link MenuTimelineStore} implementations.
 *
 * It can also be used directly.
 */
@SuppressWarnings("unused")
public class StoreException extends RuntimeException {

    public StoreException(final String message) {
        super(message);
    }

    public StoreException(final String message, final Throwable reason) {
        super(message, reason);
    }
}
