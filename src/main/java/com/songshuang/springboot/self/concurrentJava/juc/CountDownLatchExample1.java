package com.songshuang.springboot.self.concurrentJava.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample1 {

  private static int threadCount = 200;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();

    final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      final int threadNum = i;
      exec.execute(() -> {
        try {
          test(threadNum);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          countDownLatch.countDown();
        }
      });
    }

    countDownLatch.await();
    System.out.println("Finish");
    exec.shutdown();
  }

  private static void test(int threadNum) throws InterruptedException {
    System.out.printf("%d\n", threadNum);
    Thread.sleep(100);
  }
}
