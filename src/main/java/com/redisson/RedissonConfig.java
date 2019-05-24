package com.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;

/**
 * redisson 客户端的一些配置
 */
public class RedissonConfig {

    //RedissonClient是线程安全的  所以只用创建一个实例即可
    private static RedissonClient redissonClusterClient;

    private static RedissonClient redissonStandaloneClient;

    private static RedissonClient getRedissonClient(){
        Config config = new Config();
        config.useClusterServers() //使用集群模式
                .setScanInterval(5000) // 集群状态扫描间隔时间，单位是毫秒
                .addNodeAddress("redis://120.78.74.253:7000")
                .addNodeAddress("redis://120.78.74.253:7001", "redis://120.78.74.253:7003")
                //.setReadMode(ReadMode.MASTER_SLAVE) //读取模式 主从节点都可以都 默认都从节点
                .setTimeout(3000)   //命令超时
                .setRetryAttempts(3)    //重试次数
                .setRetryInterval(1500) //重试间隔
                .setPassword(null)
        ;
        return Redisson.create(config);
    }

    /**
     * 单机模式
     * @return
     */
    private static RedissonClient getRedissonStandaloneClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://120.78.74.253:6379")
                .setConnectionMinimumIdleSize(5); //最小空闲连接数
        return Redisson.create(config);
    }

    public static RedissonClient newClusterInstance(){
        if (redissonClusterClient == null){
            redissonClusterClient = getRedissonClient();
        }
        return redissonClusterClient;
    }

    public static RedissonClient newSingleInstance(){
        if (redissonStandaloneClient == null){
            redissonStandaloneClient = getRedissonStandaloneClient();
        }
        return redissonStandaloneClient;
    }
}
