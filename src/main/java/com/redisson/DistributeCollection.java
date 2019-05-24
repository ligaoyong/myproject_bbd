package com.redisson;

import org.junit.Before;
import org.junit.Test;
import org.redisson.api.*;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryUpdatedListener;

import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 分布式集合
 */
public class DistributeCollection {

    private static RedissonClient redisson = null;

    @Before
    public void before(){
        redisson = RedissonConfig.newSingleInstance();
    }

    /**
     * 优先队列：
     *      基于Redis的Redisson分布式优先队列（Priority Queue）Java对象实现了java.util.Queue的接口。
     *      可以通过比较器（Comparator）接口来对元素排序
     */

    /**
     *  延迟队列（Delayed Queue）
     */
    @Test
    public void test21() throws InterruptedException {
        /**
         * 基于Redis的Redisson分布式延迟队列（Delayed Queue）结构的RDelayedQueue Java对象在实现了RQueue接口的基础上提供了向队列按要求延迟添加项目的功能。
         * 该功能可以用来实现消息传送延迟按几何增长或几何衰减的发送策略
         */
        RQueue<String> delayQueue = redisson.getQueue("delayQueue"); //目标队列
        RDelayedQueue<String> rDelayedQueue = redisson.getDelayedQueue(delayQueue);
        //延迟30秒添加到指定队列
        rDelayedQueue.offer("a",30,TimeUnit.SECONDS);
        String poll = delayQueue.poll();
        System.out.println("currencies ：" +poll);
        Thread.sleep(32000);
        System.out.println("30 s after ："+delayQueue.poll());
    }

    /**
     * 阻塞公平队列（Blocking Fair Queue）
     * 阻塞公平队列（Blocking Fair Queue）
     * 略 看官网
     */

    /**
     * 有界阻塞队列（Bounded Blocking Queue）
     */
    @Test
    public void test20() throws InterruptedException {
        RBoundedBlockingQueue<String> boundBlockQueue =
                redisson.getBoundedBlockingQueue("boundBlockQueue");
        boundBlockQueue.trySetCapacity(2); //容量

        boundBlockQueue.offer("a"); //提供
        boundBlockQueue.offer("b");

        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        threadPool.execute(()->{
            String take = null;
            try {
                Thread.sleep(10000);
                take = boundBlockQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("出队 ："+take);
        });

        System.out.println("入队。。。。");
        boundBlockQueue.put("c");// 此时容量已满，下面代码将会被阻塞，直到有空闲为止。
        System.out.println("入队完成！");
    }

    /**
     * 阻塞队列（Blocking Queue）
     *      基于Redis的Redisson分布式无界阻塞队列（Blocking Queue）结构的RBlockingQueue Java对象实现了java.util.concurrent.BlockingQueue接口。尽管RBlockingQueue对象无初始大小（边界）限制，
     *      但对象的最大容量受Redis限制，最大元素数量是4 294 967 295个
     */
    @Test
    public void test19() throws InterruptedException {
        RBlockingQueue<String> blockQueue = redisson.getBlockingQueue("blockQueue");
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.execute(()->{
            System.out.println("从阻塞队列中获取数据....");
            String poll = null;
            try {
                poll = blockQueue.take(); //阻塞读
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("数据 : "+poll);
        });
        threadPool.execute(()->{
            try {
                Thread.sleep(10000);
                blockQueue.add("a");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.currentThread().join();
    }

    /**
     * 双端队列（Deque）
     *      基于Redis的Redisson分布式无界双端队列（Deque）结构的RDeque Java对象实现了java.util.Deque接口。尽管RDeque对象无初始大小（边界）限制，
     *      但对象的最大容量受Redis限制，最大元素数量是4 294 967 295个。
     */
    @Test
    public void test18(){
        RDeque<Object> deque = redisson.getDeque("deque");
        deque.addFirst("a");
        deque.addFirst("b");
        deque.addLast("c");
        deque.addLast("d");

        deque.stream().forEach(System.out::println); // b a c d
    }

    /**
     * 队列（Queue）
     *      基于Redis的Redisson分布式无界队列（Queue）结构的RQueue Java对象实现了java.util.Queue接口。尽管RQueue对象无初始大小（边界）限制，
     *      但对象的最大容量受Redis限制，最大元素数量是4 294 967 295个
     */
    @Test
    public void test17(){
        RQueue<Object> queue = redisson.getQueue("queue");
        queue.add("a");
        queue.add("b");
        queue.add("c");

        System.out.println("peek : " + queue.peek()); //a peek 瞄一眼
        System.out.println("peek : " + queue.peek()); //a

        System.out.println("poll : "+queue.poll()); //a poll 出队
        System.out.println("poll : "+queue.poll());//b
    }

    /**
     * 列表(List)
     *      基于Redis的Redisson分布式列表（List）结构的RList Java对象在实现了java.util.List接口的同时，确保了元素插入时的顺序。
     *      该对象的最大容量受Redis限制，最大元素数量是4 294 967 295个。
     */
    @Test
    public void test16(){
        RList<Object> list = redisson.getList("list");
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println("first : "+list.get(0)); //a
        System.out.println("first : "+list.get(0)); // a
        list.remove(0);
        System.out.println("first in remove 0 : " +list.get(0)); //b
    }

    /**
     * 字典排序集（LexSortedSet）
     *      基于Redis的Redisson的分布式RLexSortedSet Java对象在实现了java.util.Set<String>接口的同时，
     *      将其中的所有字符串元素按照字典顺序排列。
     *      它公式还保证了字符串元素的唯一性。
     */
    @Test
    public void test15(){
        RLexSortedSet lexSortedSet = redisson.getLexSortedSet("lexSortedSet");
        lexSortedSet.add("b");
        lexSortedSet.add("a");
        lexSortedSet.add("c");
    }

    /**
     * 计分排序集（ScoredSortedSet）
     *      基于Redis的Redisson的分布式RScoredSortedSet Java对象是一个可以按插入时指定的元素评分排序的集合。
     *      它同时还保证了元素的唯一性。
     */
    @Test
    public void test14(){
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("scoredSortedSet");
        set.add(88, "小明");
        set.add(79, "小红");
        set.add(90, "小王");

        System.out.println("first : "+set.first()); //first : 小红
        System.out.println("小王 index : "+set.rank("小王"));// 获取元素在集合中的位置
    }

    /**
     * 有序集（SortedSet）
     *      基于Redis的Redisson的分布式RSortedSet Java对象实现了java.util.SortedSet接口。
     *      在保证元素唯一性的前提下，通过比较器（Comparator）接口实现了对元素的排序。
     */
    @Test
    public void test13(){
        RSortedSet<String> sortedSet = redisson.getSortedSet("sortedSet");
        sortedSet.trySetComparator(Comparator.naturalOrder());
        sortedSet.add("c");
        sortedSet.add("a");
        sortedSet.add("b");

        System.out.println("first : "+sortedSet.first());
    }

    /**
     * 集（Set）数据分片（Sharding）
     */
    @Test
    public void test12(){
//        RClusteredSet<SomeObject> set = redisson.getClusteredSet("anySet");
//        set.add(new SomeObject());
//        set.remove(new SomeObject());
    }

    /**
     *  集（Set）淘汰机制（Eviction）
     */
    @Test
    public void test11() throws InterruptedException {
        /**
         * 目前的Redis自身并不支持Set当中的元素淘汰，
         * 因此所有过期元素都是通过org.redisson.EvictionScheduler实例来实现定期清理的
         * 底层使用zset数据结构  score存储过期时刻的时间戳 然后开启一个定时调度任务
         * 去清除那些score小于当前时间戳的元素
         */
        RSetCache<String> setCache = redisson.getSetCache("setCache");
        System.out.println(new Date().getTime());
        setCache.add("set", 10, TimeUnit.SECONDS);
        setCache.add("set2");
        Thread.sleep(11000);
        System.out.println(setCache.size());
    }

    /**
     * 多值映射（Multimap）淘汰机制（Eviction）
     */
    @Test
    public void test10() throws InterruptedException {
        /**
         * 原理
         *      使用一个map和多个set来实现
         *      map保存 1-xxx 2-yyy
         *      set保存 xxx-{a,b,c} yyy-{e,f}
         */
        RSetMultimapCache<Object, Object> multimap = redisson.getSetMultimapCache("setMultiMapCache");
        multimap.put("1", "a");
        multimap.put("1", "b");
        multimap.put("1", "c");

        multimap.put("2", "e");
        multimap.put("2", "f");

        multimap.expireKey("2", 10, TimeUnit.SECONDS);

        Thread.sleep(10000);
        RSet<Object> objects = multimap.get("2");
        System.out.println(objects.size()); // 0
    }

    /**
     * 基于列表（List）的多值映射（Multimap）
     */
    @Test
    public void test9(){
        /**
         * 基于List的Multimap在保持插入顺序的同时允许一个字段下包含重复的元素。
         */
        RListMultimap<String, String> map = redisson.getListMultimap("listMultiMap");
        map.put("num", "1");
        map.put("num", "2");

        RList<String> num = map.get("num");
    }

    /**
     * LRU有界Map
     */
    @Test
    public void test8(){
        /**
         * Redisson提供了基于Redis的以LRU为驱逐策略的分布式LRU有界映射对象。
         * 顾名思义，分布式LRU有界映射允许通过对其中元素按使用时间排序处理的方式，
         * 主动移除超过规定容量限制的元素。
         */
        RMapCache<String, String> map = redisson.getMapCache("map");
        // 尝试将该map的最大容量限制设定为10
        map.trySetMaxSize(100);
        // 将该映射的最大容量限制设定或更改为10
        map.setMaxSize(10);

    }

    /**
     * 映射监听器（Map Listener）
     */
    @Test
    public void test7(){
        /**
         * Redisson为所有实现了RMapCache或RLocalCachedMapCache接口的对象提供了监听以下事件的监听器：
         *
         * 事件 | 监听器 元素 添加 事件 | org.redisson.api.map.event.EntryCreatedListener
         * 元素 过期 事件 | org.redisson.api.map.event.EntryExpiredListener
         * 元素 删除 事件 | org.redisson.api.map.event.EntryRemovedListener
         * 元素 更新 事件 | org.redisson.api.map.event.EntryUpdatedListener
         */
        RMapCache<Object, Object> map = redisson.getMapCache("myMap");
        map.addListener(new EntryUpdatedListener<Integer, Integer>() {
            @Override
            public void onUpdated(EntryEvent<Integer, Integer> event) {
                event.getKey(); // 字段名
                event.getValue(); // 新值
                event.getOldValue(); // 旧值
                // ...
            }
        });
    }

    /**
     * 映射持久化方式（缓存策略）
     */
    @Test
    public void test6(){
        /**
         * Redisson供了将映射中的数据持久化到外部储存服务的功能。主要场景有一下几种：
         *
         * 将Redisson的分布式映射类型作为业务和外部储存媒介之间的缓存。
         * 或是用来增加Redisson映射类型中数据的持久性，或是用来增加已被驱逐的数据的寿命。
         * 或是用来缓存数据库，Web服务或其他数据源的数据。
         *
         * Read-through策略
         * 通俗的讲，如果一个被请求的数据不存在于Redisson的映射中的时候，Redisson将通过预先配置好的MapLoader对象加载数据。
         *
         * Write-through（数据同步写入）策略
         * 在遇到映射中某条数据被更改时，Redisson会首先通过预先配置好的MapWriter对象写入到外部储存系统，然后再更新Redis内的数据。
         *
         * Write-behind（数据异步写入）策略
         * 对映射的数据的更改会首先写入到Redis，然后再使用异步的方式，通过MapWriter对象写入到外部储存系统。
         * 在并发环境下可以通过writeBehindThreads参数来控制写入线程的数量，已达到对外部储存系统写入并发量的控制。
         *
         * 以上策略适用于所有实现了RMap、RMapCache、RLocalCachedMap和RLocalCachedMapCache接口的对象。
         */
        MapOptions<Object, Object> options = MapOptions.defaults().writer(null).loader(null);
        RMap<Object, Object> map = redisson.getMap("ts", options);
    }

    /**
     * 映射（Map）
     *      的数据分片功能（Sharding）
     */
    public void test5(){
        /**
         * Map数据分片是Redis集群模式下的一个功能。
         * Redisson提供的分布式集群映射RClusteredMap Java对象也是基于RMap实现的
         */
//        RClusteredMap<String, String> map = redisson.getClusteredMap("anyMap");
//
//        SomeObject prevObject = map.put("123", new SomeObject());
//        SomeObject currentObject = map.putIfAbsent("323", new SomeObject());
//        SomeObject obj = map.remove("123");
//
//        map.fastPut("321", new SomeObject());
//        map.fastRemove("321");
    }

    /**
     * 映射（Map）
     *      的本地缓存功能（Local Cache）
     */
    @Test
    public void test4(){
        /**
         * 在特定的场景下，映射（Map）上的高度频繁的读取操作，使网络通信都被视为瓶颈时，
         * 使用Redisson提供的带有本地缓存功能的分布式本地缓存映射RLocalCachedMapJava对象会是一个很好的选择。
         * 它同时实现了java.util.concurrent.ConcurrentMap和java.util.Map两个接口。
         * 本地缓存功能充分的利用了JVM的自身内存空间，对部分常用的元素实行就地缓存，
         * 这样的设计让读取操作的性能较分布式映射相比提高最多 45倍 。以下配置参数可以用来创建这个实例：
         */
        LocalCachedMapOptions options = LocalCachedMapOptions.defaults()
                // 用于淘汰清除本地缓存内的元素
                // 共有以下几种选择:
                // LFU - 统计元素的使用频率，淘汰用得最少（最不常用）的。
                // LRU - 按元素使用时间排序比较，淘汰最早（最久远）的。
                // SOFT - 元素用Java的WeakReference来保存，缓存元素通过GC过程清除。
                // WEAK - 元素用Java的SoftReference来保存, 缓存元素通过GC过程清除。
                // NONE - 永不淘汰清除缓存元素。
                .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.NONE)
                // 如果缓存容量值为0表示不限制本地缓存容量大小
                .cacheSize(1000)
                // 以下选项适用于断线原因造成了未收到本地缓存更新消息的情况。
                // 断线重连的策略有以下几种：
                // CLEAR - 如果断线一段时间以后则在重新建立连接以后清空本地缓存
                // LOAD - 在服务端保存一份10分钟的作废日志
                //        如果10分钟内重新建立连接，则按照作废日志内的记录清空本地缓存的元素
                //        如果断线时间超过了这个时间，则将清空本地缓存中所有的内容
                // NONE - 默认值。断线重连时不做处理。
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
                // 以下选项适用于不同本地缓存之间相互保持同步的情况
                // 缓存同步策略有以下几种：
                // INVALIDATE - 默认值。当本地缓存映射的某条元素发生变动时，同时驱逐所有相同本地缓存映射内的该元素
                // UPDATE - 当本地缓存映射的某条元素发生变动时，同时更新所有相同本地缓存映射内的该元素
                // NONE - 不做任何同步处理
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
                // 每个Map本地缓存里元素的有效时间，默认毫秒为单位
                .timeToLive(10000)
                // 或者
                .timeToLive(10, TimeUnit.SECONDS)
                // 每个Map本地缓存里元素的最长闲置时间，默认毫秒为单位
                .maxIdle(10000)
                // 或者
                .maxIdle(10, TimeUnit.SECONDS);

        RLocalCachedMap localCacheMap =
                redisson.getLocalCachedMap("localCacheMap", options);
        RLocalCachedMap<String, Integer> map = redisson.getLocalCachedMap("test", options);

        Integer prevObject = map.put("123", 1);
        Integer currentObject = map.putIfAbsent("323", 2);
        Integer obj = map.remove("123");

// 在不需要旧值的情况下可以使用fast为前缀的类似方法
        map.fastPut("a", 1);
        map.fastPutIfAbsent("d", 32);
        map.fastRemove("b");

        RFuture<Integer> putAsyncFuture = map.putAsync("321",321);
        RFuture<Boolean> fastPutAsyncFuture = map.fastPutAsync("321",321);

        map.fastPutAsync("321", 321);
        map.fastRemoveAsync("321");
        //当不再使用Map本地缓存对象的时候应该手动销毁，如果Redisson对象被关闭（shutdown）了，则不用手动销毁。
        map.destroy();
    }

    /**
     *  映射（Map）
     *      的元素淘汰（Eviction）
     */
    @Test
    public void test3(){
        /**
         * Redisson的分布式的RMapCache Java对象在基于RMap的前提下实现了针对单个元素的淘汰机制。
         * 同时仍然保留了元素的插入顺序。
         * 目前的Redis自身并不支持散列（Hash）当中的元素淘汰，
         * 因此所有过期元素都是通过org.redisson.EvictionScheduler实例来实现定期清理的。
         * 为了保证资源的有效利用，每次运行最多清理300个过期元素。
         * 任务的启动时间将根据上次实际清理数量自动调整，间隔时间趋于1秒到1小时之间。
         * 比如该次清理时删除了300条元素，那么下次执行清理的时间将在1秒以后（最小间隔时间）。
         * 一旦该次清理数量少于上次清理数量，时间间隔将增加1.5倍。
         */
        RMapCache<String, String> map = redisson.getMapCache("mapCache");
        //有效时间 10 分钟
        map.put("key1", "value1", 10, TimeUnit.MINUTES);
        // 有效时间 ttl = 10分钟, 最长闲置时间 maxIdleTime = 10秒钟
        map.put("key2", "value2", 10, TimeUnit.MINUTES,
                10, TimeUnit.SECONDS);

        // 有效时间 = 3 秒钟
        map.putIfAbsent("key3", "", 3, TimeUnit.SECONDS);
        // 有效时间 ttl = 40秒钟, 最长闲置时间 maxIdleTime = 10秒钟
        map.putIfAbsent("key4", "", 40, TimeUnit.SECONDS,
                10, TimeUnit.SECONDS);
    }

    /**
     * 映射（Map）
     *      map字段锁的用法
     */
    @Test
    public void test2(){
        RMap<Object, Object> map = redisson.getMap("anyMap");
        Object k = new Object();
        RLock lock = map.getLock(k);//从Map上返回锁 需要一个对象

        lock.lock();
        try {
            System.out.println("处理业务");
        }finally {
            lock.unlock();
        }

        RReadWriteLock readWriteLock = map.getReadWriteLock(k); //map上的读写锁
        readWriteLock.readLock().lock();
        try {
            System.out.println("读业务");
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 映射（Map）
     * 基于Redis的Redisson的分布式映射结构的RMap Java对象实现了java.util.concurrent.ConcurrentMap接口和java.util.Map接口。
     * 与HashMap不同的是，RMap保持了元素的插入顺序。
     * 该对象的最大容量受Redis限制，最大元素数量是4 294 967 295个
     */
    @Test
    public void test1(){
        RMap<String, String> map = redisson.getMap("anyMap");
        map.put("123", "aaa");
        String prev = map.put("123", "abc");//返回的是之前123关联的值：aaa
        System.out.println("123 pre_value : "+prev); //123 : aaa
        map.putIfAbsent("123", "def"); //如果没有123key 才put 返回的时当前的value
        System.out.println("123 : "+map.get("123"));    //123 : abc

        String remove = map.remove("123");
        System.out.println("remove 123 return ：" + remove);

        map.fastPut("321", "cba"); // 快速的 不返回之前关联的值
        map.fastRemove("321");

        //异步
        RFuture<String> putAsyncFuture = map.putAsync("321","cba");
        RFuture<Boolean> fastPutAsyncFuture = map.fastPutAsync("321","cba");

        map.fastPutAsync("321", "aaa");
        map.fastRemoveAsync("321");
    }
}
