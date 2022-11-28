package com.dupenghao.jedis;

import com.dupenghao.jedis.util.JedisConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by 杜鹏豪 on 2022/11/28.
 */
public class JedisDemo {

    private Jedis jedis;

    @Before
    public void before() {
//        jedis = new Jedis("192.168.1.85", 6379);
        jedis = JedisConnectionFactory.getJedis();
//        jedis.auth("password");
        jedis.select(0);
    }

    @Test
    public void testJedis_String() {
        String result = jedis.set("name", "胡歌");
        System.out.println("result:" + result);

        String key = "heima:user:1";

        String value = jedis.get(key);
        System.out.println(value);

        String type = jedis.type(key);
        System.out.println(type);

    }

    @Test
    public void testJedis_Hash() {
        jedis.keys("*").forEach(System.out::println);
    }

    @After
    public void after() {
        if (jedis != null) {
            jedis.close();
        }
    }

}
