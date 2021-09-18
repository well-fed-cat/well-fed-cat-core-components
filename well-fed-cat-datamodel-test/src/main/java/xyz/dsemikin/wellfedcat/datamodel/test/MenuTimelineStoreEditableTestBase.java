package xyz.dsemikin.wellfedcat.datamodel.test;

import org.junit.jupiter.api.Test;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class MenuTimelineStoreEditableTestBase {

    protected abstract MenuTimelineStoreEditable getMenuTimelineStore();

    protected TestDataProvider testDataProvider() {
        return new TestDataProvider();
    }

    @Test
    public void test_dummy() {
        assertTrue(true);
    }
}
