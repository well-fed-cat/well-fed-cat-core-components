package xyz.dsemikin.wellfedcat.datamodel.test;

import org.junit.jupiter.api.Test;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStore;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class MenuTimelineStoreTestBase {

    protected abstract MenuTimelineStore getMenuTimelineStore();

    protected TestDataProvider testDataProvider() {
        return new TestDataProvider();
    }

    @Test
    public void test_dummy() {
        assertTrue(true);
    }
}
