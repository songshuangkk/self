package com.songshuang.springboot.self.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedissonLock {

  private static Config config;

  private static ClusterServersConfig clusterServersConfig;

  private static final String address = "127.0.0.1:6379";

  public static void init() {

    config = new Config();

    // 使用集群模式
    clusterServersConfig = config.useClusterServers();
    clusterServersConfig.addNodeAddress(address);
    clusterServersConfig.setMasterConnectionPoolSize(100);
    clusterServersConfig.setSlaveConnectionPoolSize(100);
    clusterServersConfig.setTimeout(1000);
  }

  public void testLock() {
    RedissonClient redisson = null;

    try {
      redisson = Redisson.create(config);
      RLock lock = redisson.getLock("testLock");
      // 获取锁
      lock.lock(20, TimeUnit.MILLISECONDS);
      // 释放锁
      lock.unlock();
      // 尝试获取锁
      boolean result = lock.tryLock(10, 20, TimeUnit.MILLISECONDS);
      // 判断是否获取到锁
      if (result) {
        lock.forceUnlock();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != redisson) {
        redisson.shutdown();
      }
    }
  }
}
