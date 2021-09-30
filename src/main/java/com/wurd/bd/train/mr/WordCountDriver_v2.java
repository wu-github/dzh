package com.wurd.bd.train.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 使用hadoop中的工具类ToolRunner提交Mr作业
 */
public class WordCountDriver_v2 extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        // 创建作业实例
        Job job = Job.getInstance(getConf(), WordCountDriver_v2.class.getSimpleName());

        // 设置作业驱动类
        job.setJarByClass(WordCountDriver_v2.class);

        // 设置本次作业mapper reducer类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 设置作业mapper阶段输出key，value数据类型，也就是程序最终输出的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        // 设置作业reducer阶段输出key，value数据类型，也就是程序最终输出的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 设置作业的输入数据路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        // 设置作业的输出数据路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        return job.waitForCompletion(true) ? 0: 1;
    }

  public static void main(String[] args) throws Exception {
      // 创建配置文件对象
      Configuration conf = new Configuration();
      // 使用工具类ToolRunner 提交程序
      int status = ToolRunner.run(conf, new WordCountDriver_v2(), args);
      // 退出客户端程序，客户端退出状态码和MapReduce 程序执行结果绑定
      System.exit(status);
  }
}
