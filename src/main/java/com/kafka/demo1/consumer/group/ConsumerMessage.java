package com.kafka.demo1.consumer.group;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Set;

public class ConsumerMessage implements Runnable {
    private KafkaConsumer consumer;

    public ConsumerMessage(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            Set assignment = consumer.assignment();
            System.out.println("assignment:"+assignment.toString());
            ConsumerRecords<String, String> records = consumer.poll(100);
            System.out.println("records:"+records+",nums:"+records.count());
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            System.out.println("---------------------------------------------------------");
        }
    }
}
