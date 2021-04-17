package zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建组
 */
public class CreateGroup implements Watcher{
    private static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zk;
    private CountDownLatch connectedSignal =  new CountDownLatch(1);

    public  void connect(String host) throws IOException, InterruptedException {
       zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
       connectedSignal.await();
    }

    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        String createPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created " + createPath);
    }

    public void close() throws InterruptedException {
        zk.close();
    }
    @Override
    public void process(WatchedEvent watchedEvent) {   //Watcher interface
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect("localhost");
        createGroup.create("zoo");
        createGroup.close();

        System.out.println("zookeeper.CreateGroup");
    }

}
