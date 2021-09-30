package com.wurd.bd.train.mr;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 本类就是mapreduce 程序中的reduce阶段的处理类，对应着ReduceTask
 */
public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
    private LongWritable result = new LongWritable();

    /**
     * 当map的所有输出数据来到reduce之后，该如何跳用reduce方法进行处理呢？
     * 1、排序
     * 2、分组规则：key相同的分为一组
     * 3、分组之后，同一组的数据组成一个新的kv键值对，调用一次reduce方法，reduce方法基于分组调用的，一个分组调用一次
     *          同一组中的数据组成一个新的绿兼职对。
     *          新的key：改组共同的key
     *          新的value：改组所有的value组成的一个迭代器Iterable
     *          例如：<hadoop, 1><hadoop, 1><hadoop, 1> --->  <hadoop, Iterable[1,1,1]>
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        // 统计变量
        long count = 0;
        // 遍历一组数据，取出该组所有的value
        for(LongWritable value: values) {
            // 所有的value累加，就是该单词的总次数
            count +=value.get();
        }
        result.set(count);
        // 输出最终结果<单词，总次数>
        context.write(key, result);
    }
}
