package com.wurd.bd.train.zk.zk;

import com.wurd.bd.train.constants.Config;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class zkClient2 {
    private int sessionTimeout = 2000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(Config.zookeeperConnectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
    }

    // 创建node数据
    @Test
    public void createZnode() throws  Exception {
        // 1：定制一个个重试策略
        /**
         * param1: 重试间隔时间 1秒
         * param2： 重试的最大次数
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
        // 2: 获取一个客户端对象
        /**
         * param1: 要脸的zk服务器列表
         * param2：会话连接时间 5秒
         * param3：连接超时时间 5秒
         */
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.zookeeperConnectString, 5000, 5000, retryPolicy);
        // 3： 开始客户端
        client.start();
        // 4： 创建节点
        //  client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/hello3", "world".getBytes());
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/tmp22", "world".getBytes());

        Thread.sleep(30000);
        // 5：关闭客户端
        client.close();
    }

    // 修改node数据
    @Test
    public void nodeData() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 1);
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.zookeeperConnectString, 15000, 15000, retryPolicy);
        client.start();
        client.setData().forPath("/hello3", "lemon".getBytes());
        client.close();
    }

    // 获取节点数据
    @Test
    public  void getZnode() throws Exception {
        // 1：定制一个个重试策略
        /**
         * param1: 重试间隔时间 1秒
         * param2： 重试的最大次数
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
        // 2: 获取一个客户端对象
        /**
         * param1: 要脸的zk服务器列表
         * param2：会话连接时间 5秒
         * param3：连接超时时间 5秒
         */
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.zookeeperConnectString, 15000, 15000, retryPolicy);
        // 3： 开始客户端
        client.start();

        byte[] bytes = client.getData().forPath("/hello3");
        System.out.println(new String(bytes));
        client.close();
    }

    // 删除节点数据
    @Test
    public  void deleteZnode() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 1);
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.zookeeperConnectString, 5000, 5000, retryPolicy);
        client.start();
        // 删除节点
        System.out.println(client.checkExists().forPath("/test"));
        client.delete().withVersion(-1).forPath("/test");
        client.close();
    }

    // 设置节点数据
    @Test
    public void setZnode() throws Exception {
        /**
         * param1: 重试间隔时间 1秒
         * param2： 重试的最大次数
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
        // 2: 获取一个客户端对象
        /**
         * param1: 要脸的zk服务器列表
         * param2：会话连接时间 5秒
         * param3：连接超时时间 5秒
         */
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.zookeeperConnectString, 5000, 5000, retryPolicy);
        // 3： 开始客户端
        client.start();
        // 4:修改节点数据
        client.setData().forPath("/hello", "zookeeper".getBytes());
        // 5:关闭客户端
        client.close();
    }


    // 监听
    @Test
    public  void watchNode() throws Exception {
        /**
         * param1: 重试间隔时间 1秒
         * param2： 重试的最大次数
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 1);
        // 2: 获取一个客户端对象
        /**
         * param1: 要脸的zk服务器列表
         * param2：会话连接时间 5秒
         * param3：连接超时时间 5秒
         */
        CuratorFramework client = CuratorFrameworkFactory.newClient(Config.zookeeperConnectString, 5000, 5000, retryPolicy);
        // 3： 开始客户端
        client.start();
        // 4:创建一个TreeCache 对象，指定要监控的节点
        TreeCache treeCache = new TreeCache(client, "/hello");
        // 5：自定一个监听器
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                // 自定义监听器
                ChildData data = treeCacheEvent.getData();
                if(data != null){
                    switch (treeCacheEvent.getType()) {
                        case NODE_ADDED:
                            System.out.println("x监控到有新增节点");
                            break;
                        case NODE_REMOVED:
                            System.out.println("监控到有节点被删除");
                            break;
                        case NODE_UPDATED:
                            System.out.println("监控到有节点被更新");
                            break;
                        default:
                            break;

                    }
                }
            }
        });

        // 开始监听
        treeCache.start();

        Thread.sleep(300000);

    }
}
