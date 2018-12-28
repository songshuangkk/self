package com.songshuang.springboot.self.limiter;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 漏桶算法.
 */
public class LeakyBucket {

  private static final int TOKEN_BUCKET_SIZE = 5;

  private static final ArrayBlockingQueue<Runnable> bucket = new ArrayBlockingQueue<>(TOKEN_BUCKET_SIZE);

  static class LeakyBucketTask implements Runnable {
    @Override
    public void run() {
      System.out.printf("Thread-%d - 我开始执行啦\n", Thread.currentThread().getId());
    }
  }

  static void addLeakyBucket(Runnable runnable) throws InterruptedException {
    // 桶里最多只能存放5个
    bucket.put(runnable);
  }

  /**
   * 每隔一秒获取一个任务进行处理.
   *
   * @throws InterruptedException
   */
  static void pullLeakyBucket() throws InterruptedException {
    System.out.printf("Bucket size = %d\n", bucket.size());
    Runnable runnable = bucket.poll();
    if (runnable != null) {
      new Thread(bucket.poll()).start();
    }
    Thread.sleep(1000L);
  }

  public static void main(String[] args) {
    // 创建流量请求.
    new Thread(() -> {
      try {
        while (true) {
          addLeakyBucket(new LeakyBucketTask());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    try {
      while (true) {
        pullLeakyBucket();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
