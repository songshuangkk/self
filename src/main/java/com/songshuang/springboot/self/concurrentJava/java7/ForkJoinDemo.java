package com.songshuang.springboot.self.concurrentJava.java7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * Created By songshuang on 2018/4/23
 * <p>
 * Talk is cheap. Show me the code.
 *
 * Fork/Join是递归的二分法进行操作
 */
public class ForkJoinDemo {

  /**
   * 1 到 10的相加
   * @param args
   */
  public static void main(String[] args) {

    // 并行: 是多核参与
    // 并发: 是一同参与
    // ForkJoinPool

    System.out.printf("当前CPU处理数: %d\n",ForkJoinPool.commonPool().getParallelism());

    System.out.printf("当前CPU处理数: %d\n",Runtime.getRuntime().availableProcessors());

    ForkJoinPool forkJoinPool = new ForkJoinPool();

    forkJoinPool.invoke(new RecursiveAction() {
      @Override
      protected void compute() {
        System.out.printf("[Thread: %s ]: Hello World", Thread.currentThread().getName());
      }
    });

    forkJoinPool.shutdown();

  }
}
