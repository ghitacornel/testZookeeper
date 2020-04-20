package tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import zookeeper.ZKManager;

public class TestCRUD {

    private static ZKManager manager;

    @BeforeClass
    public static void setupAll() {
        manager = new ZKManager();
    }

    @AfterClass
    public static void tearDownAll() {
        manager.close();
    }

    @Test
    public void test() {

        String path = "/path";

        // read
        {
            Object data = manager.getZNodeData(path);
            Assert.assertNull(data);
        }

        // create
        {
            manager.create(path, "custom data".getBytes());
        }

        // read
        {
            Object data = manager.getZNodeData(path);
            Assert.assertEquals("custom data", data);
        }

        // update
        {
            manager.update(path, "custom new data".getBytes());
        }

        // read
        {
            Object data = manager.getZNodeData(path);
            Assert.assertEquals("custom new data", data);
        }

        // delete
        {
            manager.delete(path);
        }

        // read
        {
            Object data = manager.getZNodeData(path);
            Assert.assertNull(data);
        }
    }

}
