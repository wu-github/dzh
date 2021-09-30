package com.wurd.bd.train.zk.zk;

import com.wurd.bd.train.constants.Config;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zkClient {

    private int sessionTimeout = 115000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(Config.zookeeperConnectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getPath());

//                System.out.println("-------------------------------");
//                List<String> children = null;
//                try {
//                    children = zkClient.getChildren("/", true);
//
//                    for (String child : children) {
//                        System.out.println(child);
//                    }
//
//                    System.out.println("-------------------------------");
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @Test
    public void create() throws KeeperException, InterruptedException {
        String nodeCreated = zkClient.create("/testCreate", "xiaoma".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);

        for (String child : children) {
            System.out.println(child);
        }

        // 延时
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exist() throws KeeperException, InterruptedException {

        Stat stat = zkClient.exists("/config", false);

        System.out.println(stat==null? "not exist " : "exist");
    }
}
