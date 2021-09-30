package com.wurd.bd.train.kafka.demo;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 增加时间戳拦截器
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {

    //1、构建配置信息，初始化
    @Override
    public void configure(Map<String, ?> map) {

    }

    //2、在这里对消息进行处理
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {

        //在发送的消息前面加上时间戳
        //参数：String topic, Integer partition, Long timestamp, K key, V value
        return new ProducerRecord<String, String>(producerRecord.topic(),
                producerRecord.partition(), producerRecord.timestamp(),
                producerRecord.key(), System.currentTimeMillis() + ":" + producerRecord.value());
    }


    //3、当消息发送成功后，ack反馈
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }


    //4、关闭资源
    @Override
    public void close() {

    }
}