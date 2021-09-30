package com.wurd.bd.train.hbase;

import java.io.IOException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseWriteBuffer {
    public static void main(String[] args) throws IOException {

        TestTableHelper.execute(new TestTableHelper.Executor() {
            @Override
            public void execute(Connection connection, Table table) throws IOException {
                BufferedMutator bufferedMutator = connection.getBufferedMutator(TableName.valueOf("test"));

                Put put1 = new Put(Bytes.toBytes("row-1"));
                put1.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("qual1"), Bytes.toBytes("val11"));
                bufferedMutator.mutate(put1);

                Put put2 = new Put(Bytes.toBytes("row-2"));
                put2.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("qual1"), Bytes.toBytes("val22"));
                bufferedMutator.mutate(put2);

                Put put3 = new Put(Bytes.toBytes("row-3"));
                put3.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("qual1"), Bytes.toBytes("val33"));
                bufferedMutator.mutate(put3);

                Get get = new Get(Bytes.toBytes("row-1"));
                Result result1 = table.get(get);
                System.out.println(result1);

                bufferedMutator.flush();

                Result result2 = table.get(get);
                System.out.println(result2);

                bufferedMutator.close();
            }
        });
    }
}
