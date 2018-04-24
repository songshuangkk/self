package com.songshuang.springboot.self.concurrentJava.java8;

import java.util.concurrent.*;
import java.util.function.Function;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;

/**
 * Created By songshuang on 2018/4/23
 * <p>
 * Talk is cheap. Show me the code.
 */
public class CompletableFutureDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    simpleDemo();

    asyncDemo();

    simpleApplyDemo();

    asyncApplyDemo();

    executorDemo();

    thenAcceptDemo();

    exceptionDemo();

    cancelDemo();

    applyToEitherDemo();

    thenComposeDemo();
  }

  /**
   * 简单的使用.
   */
  private static void simpleDemo() {

    CompletableFuture completableFuture = CompletableFuture.completedFuture("Complete Message");

    assert completableFuture.isDone();

    System.out.printf("%s\n", completableFuture.getNow("HaHa"));
  }

  /**
   * 使用异步方式操作.
   * CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)， 异步执行通过ForkJoinPool实现.
   * 它使用守护线程去执行任务。注意这是CompletableFuture的特性， 其它CompletionStage可以override这个默认的行为。
   */
  private static void asyncDemo() {

    CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
      assert Thread.currentThread().isDaemon();
    });

    assert completableFuture.isDone();
  }

  /**
   * then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回大写之后的操作.
   * @throws ExecutionException
   * @throws InterruptedException
   */
  private static void simpleApplyDemo() throws ExecutionException, InterruptedException {

    CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("Complete Message").thenApply(s -> s.toUpperCase());

    assert completableFuture.isDone();
    System.out.printf("result is %s\n", completableFuture.get());
  }

  /**
   * 通过调用异步方法(方法后边加Async后缀)，串联起来的CompletableFuture可以异步地执行（使用ForkJoinPool.commonPool()）
   * @throws ExecutionException
   * @throws InterruptedException
   */
  private static void asyncApplyDemo() throws ExecutionException, InterruptedException {

    CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("Complete Message")
            .thenApplyAsync((s) -> {
              String upperStr = s.toUpperCase();
              System.out.printf("To UpperCase is %s\n", upperStr);
              return upperStr;
            })
            .thenApplyAsync((s) -> {
              String lowerStr = s.toLowerCase();
              System.out.printf("To Lower is %s\n", lowerStr);
              return lowerStr;
            });

    assert completableFuture.isDone();
    System.out.printf("async apply result is %s", completableFuture.get());
  }

  /**
   * 异步方法一个非常有用的特性就是能够提供一个Executor来异步地执行CompletableFuture。这个例子演示了如何使用一个固定大小的线程池来应用大写函数。
   * @throws ExecutionException
   * @throws InterruptedException
   */
  private static void executorDemo() throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
      int count = 1;
      @Override
      public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "customer-executor" + count++);
      }
    });

    CompletableFuture completableFuture = CompletableFuture.completedFuture("message").thenApplyAsync((s) -> {
      System.out.printf("CurrentThread name is %s\n", Thread.currentThread().getName());
      return s.toUpperCase();
    }, executor);

    System.out.printf("Executor result is %s\n", completableFuture.get());

    executor.shutdown();
  }

  /**
   * 消费前一阶段的结果.
   * 如果下一阶段接收了当前阶段的结果，但是在计算的时候不需要返回值(它的返回类型是void).
   * 那么它可以不应用一个函数，而是一个消费者， 调用方法也变成了thenAccept
   */
  private static void thenAcceptDemo() {

    StringBuilder result = new StringBuilder();

    CompletableFuture completableFuture = CompletableFuture.completedFuture("thenAcceptAsync message").thenAcceptAsync( s -> {
      result.append(s);
    });

    completableFuture.join();
  }

  /**
   * 发送异常的处理.
   *
   * @throws ExecutionException
   * @throws InterruptedException
   */
  private static void exceptionDemo() throws ExecutionException, InterruptedException {

    CompletableFuture completableFuture = CompletableFuture.completedFuture("message").thenApplyAsync( s -> {
      throw new RuntimeException("Exception HaHaHa");
    });

    // 用来处理发生异常的时候，钩子处理
    CompletableFuture exceptionHandler = completableFuture.handle((s, th) -> {
              // 这个时候，回调过来的数据为null
              System.out.printf("Exception happened string is %s\n", s);
              return (th != null) ? "message upon cancel" : "";
            }
    );

    completableFuture.completeExceptionally(new RuntimeException("completed exceptionally"));

    try {
      System.out.printf("Exception result is %s\n", completableFuture.join());
      fail("Should have thrown an exception.");
    } catch (CompletionException e) {
      e.printStackTrace();
    }

    System.out.printf("Exception Message is %s\n", exceptionHandler.join());
  }

  /**
   * 取消计算.
   *
   */
  private static void cancelDemo() {

    CompletableFuture completableFuture = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase);

    CompletableFuture exceptionHandler = completableFuture.handle((s, th) -> "Canceled Message");

    System.out.printf("Was not canceled\n", completableFuture.cancel(true));
    System.out.printf("Was not competed exceptionally\n", completableFuture.isCompletedExceptionally());
    System.out.printf("canceled message\n", exceptionHandler.join());
  }

  private static void applyToEitherDemo() {
    String original = "Message";

    CompletableFuture completableFuture1 = CompletableFuture.completedFuture(original).thenApplyAsync(String::toUpperCase);

    CompletableFuture completableFuture2 = completableFuture1.applyToEither(
            CompletableFuture.completedFuture(original).thenApplyAsync(String::toLowerCase),
            s -> s + " from applyToEither");

    System.out.printf("Apply To Either Result is %s\n", completableFuture2.join());
  }

  /**
   * 运行完之后执行线程.
   * 如果CompletableFuture依赖两个前面阶段的结果， 它复合两个阶段的结果再返回一个结果，我们就可以使用thenCombine()异步函数, 需要使用join进行等待获取结果。
   * 整个流水线是同步的，所以getNow()会得到最终的结果，它把大写和小写字符串连接起来。
   */
  private static void runAfterBothDemo() {

    String original = "Message";

    StringBuilder result = new StringBuilder();

    CompletableFuture completableFuture = CompletableFuture.completedFuture(original)
            .thenApplyAsync(String::toUpperCase)
            .runAfterBothAsync(CompletableFuture.completedFuture(original).thenApply(String::toLowerCase), () -> result.append("done"));
  }

  private static void thenComposeDemo() {

    String original = "Message";

    CompletableFuture completableFuture = CompletableFuture.completedFuture(original).thenApply(String::toUpperCase)
            .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(String::toLowerCase)
                    .thenApply(s -> upper + s));

    System.out.printf("Message is %s\n", completableFuture.join());
  }
}
