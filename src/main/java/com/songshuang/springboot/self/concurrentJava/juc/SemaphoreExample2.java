package com.songshuang.springboot.self.concurrentJava.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample2 {
  private static final int threadCount = 20;

  public static void main(String[] args) {

    ExecutorService exec = Executors.newCachedThreadPool();

    final Semaphore semaphore = new Semaphore(3);

    for (int i=0; i<threadCount; i++) {
      final int threadNum = i;
      exec.execute(() -> {
        try {
          semaphore.acquire(3);
          test(threadNum);
          semaphore.release(3);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }

    System.out.println("Finish");
    exec.shutdown();
  }

  private static void test(int threadNum) throws InterruptedException {
    System.out.printf("%d\n", threadNum);
    Thread.sleep(1000);
  }
}
