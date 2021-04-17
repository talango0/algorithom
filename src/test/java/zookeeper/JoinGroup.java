package zookeeper;

import org.apache.zookeeper.server.ConnectionBean;
import org.apache.zookeeper.server.ServerCnxn;
import org.apache.zookeeper.server.ZooKeeperServer;

public class JoinGroup extends ConnectionBean {
    public JoinGroup(ServerCnxn connection, ZooKeeperServer zk) {
        super(connection, zk);
    }
}
