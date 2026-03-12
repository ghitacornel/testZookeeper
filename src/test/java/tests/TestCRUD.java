package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zookeeper.ZKManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestCRUD {

    private static ZKManager manager;

    @BeforeEach
    public void setupAll() {
        manager = new ZKManager();
    }

    @AfterEach
    public void tearDownAll() {
        manager.close();
    }

    @Test
    public void test() {

        String path = "/path";

        // read
        {
            Object data = manager.getZNodeData(path);
            assertNull(data);
        }

        // create
        {
            manager.create(path, "custom data".getBytes());
        }

        // read
        {
            Object data = manager.getZNodeData(path);
            assertEquals("custom data", data);
        }

        // update
        {
            manager.update(path, "custom new data".getBytes());
        }

        // read
        {
            Object data = manager.getZNodeData(path);
            assertEquals("custom new data", data);
        }

        // delete
        {
            manager.delete(path);
        }

        // read
        {
            Object data = manager.getZNodeData(path);
            assertNull(data);
        }
    }

}
