package com.songshuang.springboot.self.concurrentJava.java5.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created By songshuang on 2018/4/23
 * <p>
 * Talk is cheap. Show me the code.
 */
public class ExecutorDemo {

  public static void main(String[] args) {

    // 执行器，他是线程池的一种实现
    Executor executor = Executors.newFixedThreadPool(1);

    executor.execute(new Runnable() {
      @Override
      public void run() {
        System.out.printf("Hello World");
      }
    });

    // 合理关闭线程池
    if (executor instanceof ExecutorService) {

      ExecutorService executorService = ExecutorService.class.cast(executor);

      executorService.shutdown();
    }

    // Java5 开始实施AutoClosable。 I/O，JDBC
  }
}
