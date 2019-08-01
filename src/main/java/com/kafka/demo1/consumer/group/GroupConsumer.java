package com.kafka.demo1.consumer.group;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerConnector;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * kafka组消费模型
 */
public class GroupConsumer {
    public static void main(String[] args) {
        GroupConsumer groupConsumer = new GroupConsumer();
        //groupConsumer.autoCommitOffset();
        //groupConsumer.handleCommitOffset();
    }

    /**
     * 自动提交offset 这个例子是单个consumer 不是组
     * 会分批次获取消息
     */
    private void autoCommitOffset(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //consumer.subscribe(Arrays.asList("foo", "bar","my-topic","test-topic"));
        //手动指定分区
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            System.out.println("records:"+records+",nums:"+records.count());
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }

    /**
     * 手动提交offset
     */
    private void handleCommitOffset(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        //要放到不同的组中 否则分区分配给了test组中的consumer后 便不会分配给当前consumer
        props.put("group.id", "test1");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(/*"foo", "bar","my-topic",*/"test-topic"));
        final int minBatchSize = 40;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                System.out.println("手动提交offset");
                consumer.commitSync();
                buffer.clear();
            }
        }
    }
}
