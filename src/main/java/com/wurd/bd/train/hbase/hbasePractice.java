package com.wurd.bd.train.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 先hbase shell 创建 create 'testtable', 'cf'
 * testtable: 表名
 * cf: 列族
 */
public class hbasePractice {
    public static void main(String[] args) {
        try {
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            configuration.set("hbase.zookeeper.quorum", "9.134.38.210");
            //configuration.set("hbase.master", "172.16.25.129:16010");
            Connection connection = ConnectionFactory.createConnection(configuration);
            Table table = connection.getTable(TableName.valueOf("student"));

            System.out.println("got table student");

            Put put = new Put(Bytes.toBytes("1001"));
            /**
             * byte[] family,
             * byte[] qualifier,
             * byte[] value
             */
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("sex"), Bytes.toBytes("female"));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes("38"));

            table.put(put);
            System.out.println("done put");
            table.close();
            connection.close();
            System.out.println("close");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
