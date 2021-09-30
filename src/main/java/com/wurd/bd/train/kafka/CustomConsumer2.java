package com.wurd.bd.train.kafka;

import com.wurd.bd.train.constants.Config;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 手动提交offset
 * 同步提交offset
 * 由于同步提交offset有失败重试机制，故更加可靠 ，以下为同步提交 offset的示例。
 */
public class CustomConsumer2 {

    public static void main(String[] args) {
        Properties props = new Properties();
        //Kafka 集群
        props.put("bootstrap.servers", Config.kafkaConnectString);
//        消费者组，只要 group.id 相同，就属于同一个消费者组
        props.put("group.id", "test");
        // 关闭自动提交 offset
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));// 消费者订阅主题
        while (true) {
//            消费者拉取数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
//            同步提交，当前线程会阻塞, 直到 offset 提交成功
            consumer.commitSync();
        }
    }
}

