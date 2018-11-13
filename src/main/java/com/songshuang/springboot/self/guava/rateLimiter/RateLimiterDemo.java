package com.songshuang.springboot.self.guava.rateLimiter;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {

  private static RateLimiter rateLimiter = RateLimiter.create(10);

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 10; i++) {
      Thread task = new Thread(new Task(), Joiner.on("-").join("Thread", i));
      task.start();
      Thread.sleep(100L);
    }
    Thread.sleep(10000);
  }

  static class Task implements Runnable {
    @Override
    public void run() {
      System.out.printf("%s 进入服务...\n", Thread.currentThread().getName());
      if (rateLimiter.tryAcquire()) {
        System.out.printf("%s 获取到流量进入到功能...\n", Thread.currentThread().getName());
      } else {
        System.out.printf("%s 限流中...\n", Thread.currentThread().getName());
      }
    }
  }
}
