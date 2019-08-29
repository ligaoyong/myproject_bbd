package com.kafka.demo1.producer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 消息生产者与kafka通讯
 */
public class ProducerTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //kafka生产者配置
        Properties props = new Properties();
        //配置kafka集群的broker地址，指定一个即可
        props.put("bootstrap.servers", "localhost:9092");
        //配置消息key、value的序列化类
        //将用户提供的key和value对象ProducerRecord转换成字节，也可以使用附带的ByteArraySerializaer或StringSerializer处理简单的string或byte类型。
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //配置partitionner选择策略，可选配置
        //props.put("partitioner.class", "cn.ljh.kafka.kafka_helloworld.SimplePartitioner");

        //判别请求是否为完整的条件（就是是判断是不是成功发送了）。我们指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的
        props.put("acks", "all");
        //如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性。
        props.put("retries", 0);
        //(生产者)缓存每个分区未发送消息。缓存的大小是通过 batch.size 配置指定的。
        // 值较大的话将会产生更大的批。并需要更多的内存（因为每个“活跃”的分区都有1个缓冲区）
        props.put("batch.size", 16384);
        //默认缓冲可立即发送，即便缓冲空间还没有满，但是，如果你想减少请求的数量，可以设置linger.ms大于0。
        // 这将指示生产者发送请求之前等待一段时间，希望更多的消息填补到未满的批中。
        props.put("linger.ms", 1);
        //控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间。
        // 当缓存空间耗尽，其他发送调用将被阻塞，阻塞时间的阈值通过max.block.ms设定，之后它将抛出一个TimeoutException。
        props.put("buffer.memory", 33554432);

        //根据配置文件获取kafka生产者实例
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for(int i = 0; i < 10000; i++){
            //给kafka发送消息
            //方法是异步的，添加消息到缓冲区等待发送，并立即返回。生产者将单个的消息批量在一起发送来提高效率。
            Future<RecordMetadata> metadataFuture = producer.send(new ProducerRecord<String, String>("test3-topic", "" + i, "" + i));
            RecordMetadata recordMetadata = metadataFuture.get();
            System.out.println("topic:"+recordMetadata.topic()+",partition:"+recordMetadata.partition()+",offset:"+recordMetadata.offset());
        }

        //ProducerRecord<String,String> record = new ProducerRecord<>("my-topic", "key-100", "value-100");
        /*try {
            Future<RecordMetadata> future = producer.send(record);
            //由于send调用是异步的，它将为分配消息的此消息的RecordMetadata返回一个Future。
            // 如果future调用get()，则将阻塞，直到相关请求完成并返回该消息的metadata，或抛出发送异常。
            RecordMetadata metadata = future.get();
            System.out.println("offset:"+metadata.offset()+",partition:"+metadata.partition()+",topic:"+metadata.topic());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
       }*/

        //非阻塞式发送消息及回调 : 发送消息到缓冲区后立即返回 当消息成功发送时调用回调函数
        /*producer.send(record,
                new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if(e != null) {
                            e.printStackTrace();
                        }
                        System.out.println("The offset of the record we just sent is: " + metadata.offset());
                    }
                });*/

        producer.close();

    }
}
