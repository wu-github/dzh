package com.wurd.bd.train.hadoop.outputformat;

import java.io.IOException;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class LogRecordWriter extends RecordWriter<Text, NullWritable> {

    private  FSDataOutputStream fsOut;
    private  FSDataOutputStream otherOut;

    public LogRecordWriter(TaskAttemptContext job) {
        // 创建两条流
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());

            fsOut = fs.create(new Path("/usr/bigdata/testData/apache.log"));

            otherOut = fs.create(new Path("/usr/bigdata/testData/other.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        String log = key.toString();

        // 具体写
        if (log.contains("ERROR")){
            fsOut.writeBytes(log+"\n");
        }else {
            otherOut.writeBytes(log+"\n");
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        // 关流
        IOUtils.closeStream(fsOut);
        IOUtils.closeStream(otherOut);
    }
}
