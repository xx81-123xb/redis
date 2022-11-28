package com.dupenghao.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 杜鹏豪 on 2022/11/28.
 */
@Component
public class RedisTemplateDemo {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    private RedisTemplate<String,List<String>> redisTemplate;

    public void test(){
//        doTest();
//        doTest2();
        doTest3();
    }

    private void doTest3(){

        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
//        hashOperations.keys("*").forEach(System.out::println);
        hashOperations.put("hashDemo","key1","value1");
        System.out.println(hashOperations.get("hashDemo", "key1"));
        hashOperations.keys("hashDemo").forEach(System.out::println);
    }

    private void doTest2() {
        ListOperations listOperations = redisTemplate.opsForList();
    }

    private void doTest() {
        ValueOperations valueOperations = redisTemplate.opsForValue();

//        valueOperations.set("name","刘亦菲");

        String key = "heima:user:1";
        key="age";
//        key="name";
//        key="heima:item:3";
        Object value = valueOperations.get(key);
        System.out.println(value);
    }

}
