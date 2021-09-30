package com.wurd.bd.train.kafka.demo;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 统计发送消息成功和发送失败消息数 ，并在 producer关闭时打印这两个计数器
 */
public class CounterInterceptor implements ProducerInterceptor {

    private int successCnt = 0;
    private int failureCnt = 0;

    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        return producerRecord; //将消息原样放行
    }

    //统计成功或者失败的次数
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(e == null) {
            successCnt++;
        } else {
            failureCnt++;
        }
    }

    @Override
    public void close() {
        System.out.println("成功的次数：" + successCnt + "，失败的次数：" + failureCnt);
    }
}