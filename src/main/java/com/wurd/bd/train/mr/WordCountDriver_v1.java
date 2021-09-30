package com.wurd.bd.train.mr;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 该类就是MapReduce程序客户端驱动类，主要是构造Job对象实例
 * 指定各种组件属性，包括：mapper reducer类，输入输出的数据类型、输入输出的数据路径
 * 提交job作业 ---> job.submit()
 */
public class WordCountDriver_v1 {
  public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
      // 配置文件对象
      Configuration conf = new Configuration();
//      conf.set("mapreduce.framework.name", "yarn");
      // 创建作业实例， 参数说明 配置对象，job名字
      Job job = Job.getInstance(conf, WordCountDriver_v1.class.getSimpleName());
      // 设置作业驱动类，mr程序的主类
      job.setJarByClass(WordCountDriver_v1.class);
      // 设置本次作业mapper reducer类
      job.setMapperClass(WordCountMapper.class);
      job.setReducerClass(WordCountReducer.class);

      // 设置作业mapper阶段输出key，value数据类型，也就是程序最终输出的数据类型
      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(LongWritable.class);

      // 设置作业reducer阶段输出key，value数据类型，也就是程序最终输出的数据类型
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(LongWritable.class);

      // 设置作业的输入数据路径和输出路径
      // todo：TextInputFormat TextOutFormat
      FileInputFormat.setInputPaths(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1]));

      // 最终提交本次job作业
//      job.submit();

      // 通常采用下面方法提交job，参数表示是否开启实时监视追踪作业的执行情况
      boolean flag = job.waitForCompletion(true);

      // 0 正常退出，1异常退出
      System.exit(flag ? 0: 1);
  }
}



















