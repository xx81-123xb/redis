package com.dupenghao.jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by 杜鹏豪 on 2022/11/28.
 */
public class JedisConnectionFactory {

    private static final JedisPool jedisPool;


    static {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(8);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(8);
        //最小空闲连接
        jedisPoolConfig.setMinIdle(0);

        jedisPoolConfig.setMaxWaitMillis(1000);
        jedisPool=new JedisPool(jedisPoolConfig,"192.168.1.85");

    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
