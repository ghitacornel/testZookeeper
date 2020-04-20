package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.nio.charset.StandardCharsets;

public class ZKManager {

    private static ZooKeeper keeper;
    private static ZKConnection connection;

    public ZKManager() {
        initialize();
    }

    /**
     * Initialize connection
     */
    private void initialize() {
        try {
            connection = new ZKConnection();
            keeper = connection.connect("localhost");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create(String path, byte[] data) {
        try {
            keeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object getZNodeData(String path) {
        try {
            byte[] b = keeper.getData(path, null, null);
            String data = new String(b, StandardCharsets.UTF_8);
            System.out.println(data);
            return data;
        } catch (org.apache.zookeeper.KeeperException e) {
            if (!e.code().equals(KeeperException.Code.NONODE)) {
                throw new RuntimeException(e);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String path, byte[] data) {
        try {
            int version = keeper.exists(path, true).getVersion();
            keeper.setData(path, data, version);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String path) {
        try {
            int version = keeper.exists(path, true).getVersion();
            keeper.delete(path, version);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}