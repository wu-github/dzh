package com.wurd.bd.train.hbase;

import java.io.IOException;

import com.wurd.bd.train.constants.Config;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class TestTableHelper {
    public interface Executor {
        void execute(Connection connection, Table table) throws IOException;
    }

    public static void execute(Executor executor) throws IOException {
        //  HBaseConfiguration 类用于管理 HBase 的配置信息
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", Config.zookeeperConnectString);
        //configuration.set("hbase.master", "9.134.38.210:16010");
        Connection connection = ConnectionFactory.createConnection(configuration);

        /**
         * Admin 是 Java 接口类型，不能直接用该接口来实例化一个对象，而是必须通过调用 Connection.getAdmin() 方法，来调用返回子对象的成员方法。
         * 该接口用来管理 HBase 数据库的表信息。它提供的方法包括创建表，删除表，列出表项，使表有效或无效，以及添加或删除表列族成员等。
         */
//        Admin admin = connection.getAdmin();
//        if(admin.tableExists(tableName)) {//如果存在要创建的表，那么先删除，再创建
//            admin.disableTable(tableName);
//            admin.deleteTable(tableName);
//        }
//        admin.createTable(tableDescriptor);
//        admin.disableTable(tableName);
//        HColumnDescriptor hd = new HColumnDescriptor(columnFamily);
//        admin.addColumn(tableName,hd);

//        HTableDescriptor 包含了表的详细信息。创建表时添加列族使用的例子如下
//        HTableDescriptor tableDescriptor = new HTableDescriptor (TableName.valueOf("student"));// 表的数据模式
//        tableDescriptor. addFamily (new HColumnDescriptor("name"));// 增加列族
//        tableDescriptor.addFamily(new HColumnDescriptor("age"));
//        tableDescriptor.addFamily(new HColumnDescriptor("gender"));
//        admin.createTable(tableDescriptor);


        Table table = connection.getTable(TableName.valueOf("test"));

        cleanRow123(table);

        executor.execute(connection, table);

        table.close();
        connection.close();
    }

    private static void cleanRow123(Table table) throws IOException {
        Delete delete1 = new Delete(Bytes.toBytes("row1"));
        table.delete(delete1);
        Delete delete2 = new Delete(Bytes.toBytes("row2"));
        table.delete(delete2);
        Delete delete3 = new Delete(Bytes.toBytes("row3"));
        table.delete(delete3);
    }

}
