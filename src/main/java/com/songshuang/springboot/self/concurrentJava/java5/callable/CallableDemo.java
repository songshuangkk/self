package com.songshuang.springboot.self.concurrentJava.java5.callable;

import java.util.concurrent.*;

/**
 * Created By songshuang on 2018/4/23
 * <p>
 * Talk is cheap. Show me the code.
 */
public class CallableDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    ExecutorService executor = Executors.newFixedThreadPool(1);

    Future<String> future = executor.submit((Callable) () -> "Hello World");

    System.out.println(future.get());

    // finally 中关闭线程池
    executor.shutdown();
  }
}
