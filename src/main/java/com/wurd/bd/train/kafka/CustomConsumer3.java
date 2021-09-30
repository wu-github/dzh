package com.wurd.bd.train.kafka;

import com.wurd.bd.train.constants.Config;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * 异步提交 offset
 * 以下为异步 提交 offset的示例：
 */
public class CustomConsumer3 {

    public static void main(String[] args) {
        Properties props = new Properties();
        //Kafka 集群
        props.put("bootstrap.servers", Config.kafkaConnectString);
//        消费者组，只要 group.id 相同，就属于同一个消费者组
        props.put("group.id", "test");

//        关闭自动提交 offset
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));// 消费者订阅主题
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);// 消费者拉取数据
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value= %s%n", record.offset(), record.key(), record.value());
            }
            // 异步提交
            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
                    if (e != null) {
                        System.err.println("Commit failed for" + offsets);
                    }
                }
            });
        }
    }
}
