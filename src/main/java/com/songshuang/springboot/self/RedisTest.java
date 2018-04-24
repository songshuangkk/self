package com.songshuang.springboot.self;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RedisTest implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);

  @Autowired
  private RedisTemplate redisTemplate;

  @Override
  public void run(String... args) throws Exception {

    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);


    redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.afterPropertiesSet();

    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
    valueOperations.set("songshuang", "hello");
    Object object = valueOperations.get("songshuang");

    ListOperations<String, Demo> listOperations = redisTemplate.opsForList();

    logger.info("result is {}", object);

    List<Demo> list = new ArrayList<>();
    for (int i=1; i<3; i++) {
      list.add(new Demo("haha"));
    }
    listOperations.leftPushAll("list", list);
  }

  public static class Demo {

    public Demo(String name) {
      this.name = name;
    }

    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
