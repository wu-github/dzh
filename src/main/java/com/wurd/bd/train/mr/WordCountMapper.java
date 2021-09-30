package com.wurd.bd.train.mr;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * wordcount mapper 类：对应着maptask
 * KEYIN: 表示map阶段输入kv中的k类型，在默认组件下是其实位置的偏移量，因此是LongWritable
 * VALUEIN： 表示map阶段输入kv中的v类型，在默认组件下是每一行的呢绒，因此是Text
 * todo - mapreduce 有默认值的读取数据组件 叫做TextInputFormat
 * todo - 读数据的行为是 一行一行读取数据，k代表读取的位置，返回kv键值对
 *  k: 每一行的其实位置的偏移量，通常无意义
 *  v：这一行的文本内容
 * KEYOUT：表示map阶段输出kv中的k类型，跟业务相关，本需求中输出的是单词，因此是Text
 * VALUEOUT：表示map阶段输出kv中的v类型，跟业务相关，本需求中输出的是单词次数1，因此是LongWritable
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    // Mapper 输出kv键值对<单词，1>
    private Text keyOut = new Text();
    private final static LongWritable valueOut = new LongWritable();

    /**
     * map方法是mapper阶段核心方法，也是具体业务逻辑实现的方法
     * 注意，该方法被调用的次数和输入的kv键值对有关，每当TextInputformat读取返回一个kv键值对，就调用一次map方法进行业务处理
     * 默认情况下，map方法是基于行来处理数据
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 将读取的一行内容根据分隔符进行分割
        String[] words = value.toString().split("\\s+");
        // 遍历单词数组
        for (String word: words){
            //把每个单词标记1 输出结果<单词， 1>
            keyOut.set(word);
            // 使用上下文对象，将数据输出
            // context.write(new Text(word), valueOut);
            context.write(keyOut, valueOut); // <hello, 1> <hadoop, 1> <jim, 1><hello, 1><hello, 1>
        }
    }
}




























