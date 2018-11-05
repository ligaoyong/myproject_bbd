package com.guava.caches;

import com.google.common.cache.*;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuyongchong on 2018/11/5.
 * guava 缓存
 */
public class CacheTest {

    /**
     * 关于缓存的第一个问题是:
     *  是否存在一些合理的默认函数来加载或计算与键相关的值?
     *  如果是这样，您应该使用CacheLoader。
     *  如果不是，或者您需要重写默认值，那么您应该将Callable语句传递给get调用。
     *  也可以使用缓存直接插入元素。但自动缓存加载是首选的，因为这样可以更容易地推断所有缓存内容的一致性。
     */

    /**
     * 1、From a CacheLoader
     */
    @Test public void test1() throws ExecutionException {
        //用附加的CacheLoader创建缓存 也可用默认的方式
        LoadingCache<String, Integer> loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                System.out.println("call load");
                return 10;
            }
        });

        /**
         * 查询加载缓存的标准方法是使用get(K)方法。
         * 这将返回一个已经缓存的值，或者使用缓存的CacheLoader自动将一个新值加载到缓存中
         * 即当在缓存中没有找到对应的值时 会调用load方法返回一个新值，并将新值添加到缓存
         */

        System.out.println(loadingCache.get("aaa"));    //call load 10 ---> 调用load  需要处理异常
        System.out.println(loadingCache.get("aaa"));    //10  -->  不调用load 上一次调用已经缓存    需要处理异常

        //当CacheLoader的load抛出未检查异常（没有显示抛出Exception）时使用
        //loadingCache.getUnchecked("bbbb");    //不需要处理异常

        //一次性加载多个值
        //loadingCache.getAll()
    }

    /**
     * 2、From a Callable
     */
    @Test public void test2(){
        /**
         * 所有guava缓存，无论是否装载，都支持get方法(K，Callable)。
         * 此方法返回与缓存中的键相关联的值，或从指定的Callable计算该值并将其添加到缓存中。
         * 在加载完成之前，不会修改与此缓存关联的可见状态。
         * 这个方法提供了一个简单的替代传统的“如果缓存，返回;否则创建、缓存和返回“模式”。
         */
        try {
            Cache<String, Integer> cache = CacheBuilder.newBuilder().maximumSize(1000).build(); //不在使用CacheLoader
            Integer value = cache.get("aaa", () -> 100);//lambda实现callable
            System.out.println("aaaa->"+value);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 3、直接放入缓存
     */
    @Test public void test3(){
        /**
         * 值可以直接用缓存插入到缓存中。put(key,value)。
         * 但不建议使用
         */
    }

    /**
     * 驱逐策略：
     *  1、基于大小的驱逐
     *  2、基于时间的驱逐
     *  3、基于引用的驱逐
     */

    /**1、基于大小的驱逐策略**/
    @Test public void test4(){
        /**
         * 如果您的缓存不应该超过某个大小，只需使用CacheBuilder.maximumSize(long)。
         * 缓存将尝试清除最近或经常使用的条目。
         * 警告:缓存可能会在超过这个限制之前驱逐条目——通常当缓存大小接近这个限制时。
         */

        /**
         * 另外，如果不同的缓存条目具有不同的“权重”——例如，如果缓存值具有完全不同的内存占用——您
         * 可以使用CacheBuilder.weigher(Weigher)指定一个weight函数，
         * 并使用CacheBuilder.maximumWeight(long)指定一个最大的缓存权重。
         * 除了与maximumSize要求相同的注意事项之外，还要注意权重是在创建时计算的，之后是静态的。
         */
    }

    /**2、基于时间的驱逐策略**/
    @Test public void test5(){
        /**
         * CacheBuilder提供了两种计时驱逐的方法:
         * 1、expireAfterAccess(long, TimeUnit)  在访问过后一段时间内过期
         * 2、expireAfterWrite(long, TimeUnit)   在写入之后的一段时间内
         */
    }

    /**4、基于引用的驱逐**/
    @Test public void test6(){
        //略
    }

    /**
     * 明确的删除：
     *  Cache.invalidate(key)
     *  Cache.invalidateAll(keys)
     *  Cache.invalidateAll()
     */

    /**
     * 移除监听器：
     *  您可以为缓存指定一个删除侦听器，以便在删除条目时执行某些操作
     *  通过CacheBuilder.removalListener(RemovalListener)。
     *  RemovalListener将获得一个RemovalNotification，它将指定RemovalCause、键和值
     */
    @Test public void test7() throws InterruptedException, ExecutionException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .removalListener((RemovalNotification<Object, Object> notification) -> {
                    System.out.println(notification.getCause());
                    System.out.println(notification.getKey());
                    System.out.println(notification.getValue());
                })
                .build();

        cache.get("aaaa",()->100);
        cache.invalidate("aaaa");
    }

    /**
     * 特性：
     *  1、统计
     *  2、asMap
     */
}
