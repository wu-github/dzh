package com.wurd.bd.train.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.util.Bytes;

public class BatchPutsWithSomeError {
    public static void main(String[] args) throws IOException {

        TestTableHelper.execute(new TestTableHelper.Executor() {
            @Override
            public void execute(Connection connection, Table table) throws IOException {
                List<Put> puts = new ArrayList<Put>();

                Put put1 = new Put(Bytes.toBytes("row1"));
                put1.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("qual1"), Bytes.toBytes("val11"));
                puts.add(put1);

                Put put2 = new Put(Bytes.toBytes("row2"));
                put2.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("qual1"), Bytes.toBytes("val22"));
                puts.add(put2); //this put will fail

                Put put3 = new Put(Bytes.toBytes("row3"));
                put3.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("qual1"), Bytes.toBytes("val33"));
                puts.add(put3);

                try {
                    table.put(puts);
                } catch (RetriesExhaustedWithDetailsException re) {
                    int numErrors = re.getNumExceptions();
                    System.out.println(String.format("Number of exceptions %d", numErrors));
                    for (int i = 0 ; i < numErrors; i++) {
                        System.out.println(String.format("Cause[%d] %s ", i, re.getCause(i)));
                        System.out.println(String.format("Hostname[%d] %s", i, re.getHostnamePort(i)));
                        System.out.println(String.format("Row[%d] %s", i, re.getRow(i)));
                    }
                    System.out.println(String.format("Cluster issues %s", re.mayHaveClusterIssues()));
                    System.out.println(String.format("Description %s", re.getExhaustiveDescription()));
                }
            }
        });
    }
}
