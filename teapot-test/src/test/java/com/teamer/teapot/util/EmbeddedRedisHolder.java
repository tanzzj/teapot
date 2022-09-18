package com.teamer.teapot.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.embedded.RedisServer;

/**
 * @author tanzj
 * @date 2022/9/19
 */
public class EmbeddedRedisHolder {

    private static RedisServer redisServer;

    private static Jedis jedis;

    private static JedisPool jedisPool;

    /**
     * 启动器，需要在@BeforeClass中调用初始化
     */
    public static void startUp() {
        redisServer = RedisServer.builder()
            .port(63792)
            .setting("maxmemory 64m")
            .build();
        redisServer.start();

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setMaxWaitMillis(2000);
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMinIdle(0);
        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 63792, 10000);
    }


    /**
     * 进程销毁，需要在@AfterClass中调用销毁
     */
    public static void end() {
        redisServer.stop();
    }

    public static Jedis getJedis() {
        if (jedis != null) {
            return jedis;
        }
        if (jedisPool == null) {
            initJedisPool();
        }
        jedis = jedisPool.getResource();
        return jedis;
    }

    private static JedisPool initJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setMaxWaitMillis(2000);
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMinIdle(0);
        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 63792, 10000);
        return jedisPool;
    }


    public static JedisPool getJedisPool() {
        return jedisPool;
    }
}
