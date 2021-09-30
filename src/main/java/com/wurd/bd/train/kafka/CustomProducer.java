package com.wurd.bd.train.kafka;

import com.wurd.bd.train.constants.Config;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 不带回调函数的API
 */
public class CustomProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        // kafka 集群， broker-list
        // ProducerConfig
        props.put("bootstrap.servers", Config.kafkaConnectString);

        //可用ProducerConfig.ACKS_CONFIG 代替 "acks"
//        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put("acks", "all");
        // 重试次数
        props.put("retries", 1);
        // 批次大小
        props.put("batch.size", 16384);
        // 等待时间
        props.put("linger.ms", 1);
        // RecordAccumulator 缓冲区大小
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("test", "test-" + Integer.toString(i),
                    "test-" + Integer.toString(i)));
        }
        producer.close();
    }
}
