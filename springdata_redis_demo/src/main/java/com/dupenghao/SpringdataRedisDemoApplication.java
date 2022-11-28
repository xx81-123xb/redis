package com.dupenghao;

import com.dupenghao.demo.RedisTemplateDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringdataRedisDemoApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringdataRedisDemoApplication.class, args);
        RedisTemplateDemo redisTemplate = (RedisTemplateDemo) applicationContext.getBean("redisTemplateDemo");

        redisTemplate.test();

//        TimeUnit.SECONDS.sleep(5);
        applicationContext.close();
    }

}
