package zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZKConnection {

    private ZooKeeper zoo;
    final CountDownLatch connectionLatch = new CountDownLatch(1);

    public ZooKeeper connect(String host) throws Exception {
        zoo = new ZooKeeper(host, 2000, we -> {
            if (we.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectionLatch.countDown();
            }
        });
        connectionLatch.await();
        return zoo;
    }

    public void close() throws Exception {
        zoo.close();
    }

}
