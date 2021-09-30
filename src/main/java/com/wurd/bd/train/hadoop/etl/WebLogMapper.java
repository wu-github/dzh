package com.wurd.bd.train.hadoop.etl;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WebLogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 1 获取一行数据
        String line = value.toString();

        // 2 解析日志
        boolean result = parseLog(line, context);

        // 3 日志不合法退出 -- ETL
        if (!result){
            return;
        }

        // 4 日志合法就直接写出
        context.write(value, NullWritable.get());
    }

    private boolean parseLog(String line, Context context) {
        // 截取
        // 1.206.126.5 - - [19/Sep/2013:05:41:41 +0000] "-" 400 0 "-" "-"
        String[] fields = line.split(" ");

        // 2 判断一下日志的长度是否大于11
        if (fields.length > 11){
            return true;
        }else {
            return false;
        }
    }
}
