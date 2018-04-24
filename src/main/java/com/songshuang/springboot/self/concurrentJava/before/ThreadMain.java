package com.songshuang.springboot.self.concurrentJava.before;

/**
 * Created By songshuang on 2018/4/23
 * <p>
 * Talk is cheap. Show me the code.
 *
 * Java 5 前时代:
 *  实现局限性:
 *    1. 缺少线程管理的原生支持。
 *    2. 缺少"锁" API
 *    3. 缺少执行完成的原生支持
 *    4. 执行结果获取困难
 *    5. Double Check Locking不确定性
 */
public class ThreadMain {

  public static void main(String[] args) {
    Thread thread = new Thread(() -> System.out.printf("Hello World"), "Sub");

    thread.start();

    System.out.println("Starting...");
  }
}
