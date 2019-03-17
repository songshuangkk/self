package com.songshuang.springboot.self.concurrentJava.juc;

import java.util.concurrent.*;

/**
 * 类似CountDownLatch，不过，这个是可以重置.
 */
public class CyclicBarrierExample1 {

  private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
    System.out.printf("Callback is running...\n");
  });

  public static void main(String[] args) throws InterruptedException {

    ExecutorService executorService = Executors.newCachedThreadPool();

    for (int i=0; i<10; i++) {
      final int threadNum = i;
      Thread.sleep(1000);
      executorService.execute(() -> {
         try {
           race(threadNum);
         } catch (Exception e) {
           e.printStackTrace();
         }
      });
    }

    System.out.printf("Finish\n");
    executorService.shutdown();
  }

  private static void race(int threadNum)  throws Exception {
    Thread.sleep(1000);

    System.out.printf("%d is ready\n", threadNum);

    try {
      cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);      // 这里是要等待所有的线程都完成的时候，在进行调用
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
    System.out.printf("%d continue\n", threadNum);
  }
}
