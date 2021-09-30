package com.wurd.bd.train.zk.case1;

import com.wurd.bd.train.constants.Config;
import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {

    private int sessionTimeout = 112000;
    private ZooKeeper zk;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        DistributeServer server = new DistributeServer();
        // 1 获取zk连接
        server.getConnect();

        // 2 注册服务器到zk集群
        server.regist(args[0]);

        // 3 启动业务逻辑（sleep）
        server.business();

    }

    // 3 启动业务逻辑（sleep）
    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

     // 2 注册服务器到zk集群
    private void regist(String hostname) throws KeeperException, InterruptedException {
        String create = zk.create("/servers/"+hostname, hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname +" is online") ;
    }

    // 1 获取zk连接
    private void getConnect() throws IOException {
        zk = new ZooKeeper(Config.zookeeperConnectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
    }
}
