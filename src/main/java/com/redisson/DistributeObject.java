package com.redisson;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分布式对象
 *      每一个对象 在redis的实现 可能会对应多个数据结构
 */
public class DistributeObject {

    private static Logger logger = LoggerFactory.getLogger(DistributeObject.class);

    private static RedissonClient redisson = null;

    @Before
    public void before(){
        redisson = RedissonConfig.newSingleInstance();
    }

    /**
     *  限流器（RateLimiter）
     */
    @Test
    public void test11() throws InterruptedException {
        /**
         * 基于Redis的分布式限流器（RateLimiter）可以用来在分布式环境下现在请求方的调用频率。
         * 既适用于不同Redisson实例下的多线程限流，也适用于相同Redisson实例下的多线程限流。
         * 该算法不保证公平性。
         */
        RRateLimiter rateLimiter = redisson.getRateLimiter("myRateLimiter");
        //初始化
        //最大速率 ：每秒产生10个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        threadPool.execute(()->{
            System.out.println("获取5个令牌.....");
            rateLimiter.acquire(5);
            System.out.println("获取成功");
            System.out.println("处理业务.....");
        });

        threadPool.execute(()->{
            System.out.println("获取6个令牌.....");
            rateLimiter.acquire(6);
            System.out.println("获取成功");
            System.out.println("处理业务.....");
        });
        Thread.currentThread().join();
    }

    /**
     * 整长型累加器（LongAdder）
     * 双精度浮点累加器（DoubleAdder）
     *
     * 基于Redis的Redisson分布式整长型累加器（LongAdder）
     * 采用了与java.util.concurrent.atomic.LongAdder类似的接口。
     * 通过利用客户端内置的LongAdder对象，为分布式环境下递增和递减操作提供了很高得性能。
     * 据统计其性能最高比分布式AtomicLong对象快 12000 倍。
     * 完美适用于分布式统计计量场景。
     */
    @Test
    public void test10(){
        RLongAdder longAdder = redisson.getLongAdder("myLongAdder");
        longAdder.add(12);
        longAdder.increment();
        longAdder.decrement();
        System.out.println(longAdder.sum());
        //当不再使用整长型累加器对象的时候应该自行手动销毁，
        longAdder.destroy();

        //双精度浮点累加器
        //redisson.getDoubleAdder("aa");
    }

    /**
     * 基数估计算法（HyperLogLog）：不是一个准确数据
     * 该对象可以在有限的空间内通过概率算法统计大量的数据。
     * 例如 ：一个页面一天的访问量(同一个用户一天只能算一次)UV
     */
    @Test
    public void test9(){
        RHyperLogLog<Integer> log = redisson.getHyperLogLog("log");
        log.add(1);
        log.add(2);
        log.add(3);
        log.add(3);//重复的不算

        System.out.println(log.count()); //3
    }

    /**
     *  布隆过滤器（Bloom Filter） : 原理见小册  利用hash函数来实现过滤
     *  Redisson利用Redis实现了Java分布式布隆过滤器（Bloom Filter）。
     *  所含最大比特数量为2^32。(43亿、4Gb)
     */
    @Test
    public void test8(){
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("sample");
        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.add("a");
        bloomFilter.add("b");
        System.out.println(bloomFilter.contains("a"));
        System.out.println(bloomFilter.contains("c"));
        /**
         * 基于Redis的Redisson集群分布式布隆过滤器通过RClusteredBloomFilter接口，
         * 为集群状态下的Redis环境提供了布隆过滤器数据分片的功能。
         * 通过优化后更加有效的算法，通过压缩未使用的比特位来释放集群内存空间。
         * 每个对象的状态都将被分布在整个集群中。所含最大比特数量为2^64
         */
//        RClusteredBloomFilter<SomeObject> bloomFilter =
//                redisson.getClusteredBloomFilter("sample");
    }

    /**
     * 话题（订阅发布）
     * Redisson的模糊话题RPatternTopic对象可以通过正式表达式来订阅多个话题
     */
    @Test
    public void test7() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //发布者
        threadPool.execute(()->{
            RTopic topic = redisson.getTopic("myTopic");
            topic.publish("3.14");
        });

        //订阅者
        threadPool.execute(()->{
            //redisson.getPatternTopic() 模糊话题 订阅多个话题
            RTopic topic = redisson.getTopic("myTopic");
            topic.addListener(String.class, ((channel, msg) -> {
                System.out.println("收到消息："+msg);
            }));
        });

        //订阅者
        threadPool.execute(()->{
            RTopic topic = redisson.getTopic("myTopic");
            topic.addListener(String.class, ((channel, msg) -> {
                System.out.println("收到消息："+msg);
            }));
        });

        Thread.currentThread().join();
    }

    /**
     * 原子整长形（AtomicLong）
     * 原子双精度浮点（AtomicDouble）
     */
    @Test
    public void test6(){
        RAtomicLong myAtomicLong = redisson.getAtomicLong("myAtomicLong");
        System.out.println(myAtomicLong);
        myAtomicLong.set(3);
        long l = myAtomicLong.incrementAndGet();
        System.out.println(l);

        RAtomicDouble myAtomicDouble = redisson.getAtomicDouble("myAtomicDouble");
        myAtomicDouble.set(3.14);
        double v = myAtomicDouble.addAndGet(1.0);
        System.out.println(v);
    }

    /**
     * BitSet : 位图
     * 存储多个boolean类型的值时非常有用
     */
    @Test
    public void test5(){
        /**
         * Redisson的分布式RBitSetJava对象采用了与java.util.BiteSet类似结构的设计风格。
         * 可以理解为它是一个分布式的可伸缩式位向量。
         * 需要注意的是RBitSet的大小受Redis限制，最大长度为4 294 967 295
         */
        RBitSet bitSet = redisson.getBitSet("simpleBitSet");
        bitSet.set(0,true); //设置第一个bit位位true
        bitSet.set(1812,false);
        //bitSet.clear(0);//清空第0个bit位
        System.out.println(bitSet.size());

        /**
         * 基于Redis的Redisson集群分布式BitSet通过RClusteredBitSet接口，
         * 为集群状态下的Redis环境提供了BitSet数据分片的功能。
         * 通过优化后更加有效的分布式RoaringBitMap算法，
         * 突破了原有的BitSet大小限制，达到了集群物理内存容量大小
         */
//        RClusteredBitSet set = redisson.getClusteredBitSet("simpleBitset");
    }

    /**
     * 地理空间位置桶
     */
    @Test
    public void test4(){
        //Redisson的分布式RGeo Java对象是一种专门用来储存与地理位置有关的对象桶。
        RGeo<Object> geo = redisson.getGeo("test");
        geo.add(new GeoEntry(13.361389, 38.115556, "Palermo"),
                new GeoEntry(15.087269, 37.502669, "Catania"));
        geo.addAsync(37.618423, 55.751244, "Moscow");

        Double distance = geo.dist("Palermo", "Catania", GeoUnit.METERS);
        System.out.println("距离："+distance);
    }

    /**
     * 二进制流
     */
    @Test
    public void test3() throws IOException {
        /**
         * Redisson的分布式RBinaryStream Java对象同时提供了InputStream接口和OutputStream接口的实现。
         * 流的最大容量受Redis主节点的内存大小限制。
         */
        RBinaryStream stream = redisson.getBinaryStream("stream");
        String value = "流的最大容量受Redis主节点的内存大小限制。";
        byte[] bytes = JSON.toJSONBytes(value);
        stream.set(bytes);

        InputStream inputStream = stream.getInputStream();
        OutputStream outputStream = stream.getOutputStream();

        long size = stream.size();
        byte[] buf = new byte[(int)size];
        //用流读
        inputStream.read(buf);
        //也可以直接读
        byte[] buf1 = stream.get();

        System.out.println("inputStream："+JSON.parse(buf));
        System.out.println("直接读："+JSON.parse(buf1));
    }

    /**
     * 通用对象桶
     */
    @Test
    public void test2(){
        /**
         * Redisson的分布式RBucketJava对象是一种通用对象桶可以用来存放任类型的对象。
         * 除了同步接口外，还提供了异步（Async）、反射式（Reactive）和RxJava2标准的接口。
         * 对象以二进制保存到redis中
         */
        RBucket<Double> bucket = redisson.getBucket("doubleObject");
        bucket.set(3.14);
        System.out.println(bucket.get());

        RBucket<Object> object = redisson.getBucket("object");
        object.set(new Object());
        System.out.println(object.get());

        //还可以通过RBuckets接口实现批量操作多个RBucket对象：
        RBuckets buckets = redisson.getBuckets();
        HashMap<String, Object> map = new HashMap<>();
        map.put("myBucket1", new Object());
        map.put("myBucket2", new Object());
        //// 利用Redis的事务特性，同时保存所有的通用对象桶，如果任意一个通用对象桶已经存在则放弃保存其他所有数据。
        buckets.trySet(map);
        // 同时保存全部通用对象桶。
        buckets.set(map);
    }

    @Test
    public void test1(){
        /**
         * 每个Redisson对象实例都会有一个与之对应的Redis数据实例，
         * 可以通过调用getName方法来取得Redis数据实例的名称（key）。
         */
        RMap map = redisson.getMap("mymap");
        System.out.println(map.getName()); // = mymap

        /**
         * 所有与Redis key相关的操作都归纳在RKeys这个接口里：
         */
        RKeys keys = redisson.getKeys();
        keys.getKeysStream().forEach(System.out::println);
        System.out.println(keys.count());
        Iterable<String> keys1 = keys.getKeys();//所有的key
        Iterable<String> keysByPattern = keys.getKeysByPattern("my*");//匹配key
        long delete = keys.delete("a", "b");
    }

}
