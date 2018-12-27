package com.songshuang.springboot.self.limiter;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 令牌桶算法.
 */
public class TokenBucket {

  // 每秒放入5个令牌放入到桶中
  // 桶中的数量最多20个
  // 从桶中获取令牌
  // 获取桶中的令牌进入执行业务
  // 没有获取到令牌，进行一个丢弃处理.
  private static final int TOKEN_BUCKET_SIZE = 20;

  private static final ArrayBlockingQueue<Long> bucket = new ArrayBlockingQueue<>(TOKEN_BUCKET_SIZE);

  /**
   * 进行往桶中进行添加处理.
   */
  static class AddTokenTask implements Runnable {

    @Override
    public void run() {
      try {
        while (true) {
          System.out.println("Begin add token...");
          addToken();
          addToken();
          addToken();
          addToken();
          addToken();
          Thread.sleep(1000);         // 随眠一秒
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 消费令牌任务.
   */
  static class ConsumeToken implements Runnable {

    @Override
    public void run() {
      System.out.printf("Get Token %d\n", pullBucket());
    }
  }

  private static void addToken() throws InterruptedException {
    if (bucket.size() == TOKEN_BUCKET_SIZE) {
      System.out.println("Token bucket is full.");
    }

    bucket.put(System.nanoTime());
  }

  private static long pullBucket() {
    Long token = bucket.poll();
    if (token == null) {
      throw new RuntimeException("获取不到令牌");
    }
    return token;
  }

  public static void main(String[] args) {
    // 开始添加令牌
    new Thread(new AddTokenTask()).start();

    try {
      while (true) {
        for (int i = 0; i < 6; i++) {
          new Thread(new ConsumeToken()).start();
        }
        Thread.sleep(1500L);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
