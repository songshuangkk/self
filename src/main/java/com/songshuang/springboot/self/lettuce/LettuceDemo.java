package com.songshuang.springboot.self.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceDemo {

  private static void standaloneRedis() {
    RedisClient redisClient = RedisClient.create("redis://localhost:32768");
    StatefulRedisConnection<String, String> connection = redisClient.connect();
    RedisCommands<String, String> syncCommands = connection.sync();
    syncCommands.set("key", "Hello, Redis!");
    connection.close();
    redisClient.shutdown();
  }

  private static void sentinelRedis() {
    RedisClient redisClient = RedisClient.create("redis-sentinel://localhost:32768");

    StatefulRedisConnection<String, String> connection = redisClient.connect();

    System.out.printf("Connected to Redis using Redis Sentinel");

    connection.close();
    redisClient.shutdown();
  }

  public static void main(String[] args) {
    standaloneRedis();
  }
}
