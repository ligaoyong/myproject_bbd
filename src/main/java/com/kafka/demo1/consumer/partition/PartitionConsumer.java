package com.kafka.demo1.consumer.partition;

import com.kafka.demo1.consumer.group.ConsumerMessage;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PartitionConsumer {
    public static void main(String[] args) {
        PartitionConsumer partitionConsumer = new PartitionConsumer();
        partitionConsumer.moreThreadConsumer();
    }

    /**
     * 使用多线程 模仿分区消费
     */
    private void moreThreadConsumer(){
        Properties properties = getProperties();
        KafkaConsumer<String, String> consumer1 = new KafkaConsumer<>(properties);
        KafkaConsumer<String, String> consumer2 = new KafkaConsumer<>(properties);
        KafkaConsumer<String, String> consumer3 = new KafkaConsumer<>(properties);
        consumer1.assign(Arrays.asList(new TopicPartition("test3-topic",0)));
//        consumer2.assign(Arrays.asList(new TopicPartition("test2-topic",1)));
//        consumer3.assign(Arrays.asList(new TopicPartition("test2-topic",2)));
        //重新指定offset的位置 从100开始消费
        consumer1.seek(new TopicPartition("test3-topic",0),100);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new ConsumerMessage(consumer1));
//        executorService.execute(new ConsumerMessage(consumer2));
//        executorService.execute(new ConsumerMessage(consumer3));
    }

    private Properties getProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
//        props.put("group.id", "test3");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}
