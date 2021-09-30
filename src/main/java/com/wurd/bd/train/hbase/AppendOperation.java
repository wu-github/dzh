package com.wurd.bd.train.hbase;

import java.io.IOException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class AppendOperation {
    public static void main(String[] args) throws IOException {
        TestTableHelper.execute(new TestTableHelper.Executor() {
            @Override
            public void execute(Connection connection, Table table) throws IOException {
                // 插入
                Put put = new Put(Bytes.toBytes("row1"));
                put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("qual3"), Bytes.toBytes("val3"));

                table.put(put);

                // 查看
                Get get = new Get(Bytes.toBytes("row1"));
                Result result = table.get(get);
                byte[] valBytes = result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("qual3"));
                System.out.println(Bytes.toString(valBytes));

                // 追加
                Append append = new Append(Bytes.toBytes("row1"));
                append.add(Bytes.toBytes("cf"), Bytes.toBytes("qual3"), Bytes.toBytes("val3333"));
                table.append(append);

                // 查看
                Result result2 = table.get(get);
                byte[] valBytes2 = result2.getValue(Bytes.toBytes("cf"), Bytes.toBytes("qual3"));
                System.out.println(Bytes.toString(valBytes2));

//                Delete delete = new Delete(Bytes.toBytes("row1"));
//                table.delete(delete);
            }
        });
    }
}
