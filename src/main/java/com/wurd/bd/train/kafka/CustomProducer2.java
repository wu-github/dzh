package com.wurd.bd.train.kafka;

import com.wurd.bd.train.constants.Config;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 带回调函数的 API
 * 回调函数会在producer收到 ack时调用，为异步调用， 该方法有两个参数，分别是
 * RecordMetadata 和 Exception ，如果 Exception 为 null ，说明消息发送成功，如果
 * Exception 不为 null ，说明消息发送失败。
 * 注意：消息发送失败会自动重试，不需要我们在回调函数中手动重试。
 */
public class CustomProducer2 {
    public static void main(String[] args) {
        Properties props = new Properties();
        // kafka 集群， broker-list
        props.put("bootstrap.servers", Config.kafkaConnectString);

        //可用ProducerConfig.ACKS_CONFIG 代替 "acks"
        //props.put(ProducerConfig.ACKS_CONFIG, "all");
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
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)),
                new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(e == null) {
                            System.out.println("success->" + recordMetadata.offset());
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            
        }
        producer.close();
    }
}
