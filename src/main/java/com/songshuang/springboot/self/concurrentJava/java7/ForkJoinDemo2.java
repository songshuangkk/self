package com.songshuang.springboot.self.concurrentJava.java7;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created By songshuang on 2018/4/23
 * <p>
 * Talk is cheap. Show me the code.
 */
public class ForkJoinDemo2 {

  public static void main(String[] args) {

    ForkJoinPool forkJoinPool = new ForkJoinPool();

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // Java中的累加对象
    LongAdder longAdder = new LongAdder();

    AddTask addTask = new AddTask(numbers, longAdder);

    forkJoinPool.invoke(addTask);

    forkJoinPool.shutdown();

    System.out.printf("result is %d\n", longAdder.longValue());
  }

  private static class AddTask extends RecursiveAction {

    private final List<Integer> numbers;

    private final LongAdder longAdder;

    private AddTask(List<Integer> numbers, LongAdder longAdder) {
      this.numbers = numbers;
      this.longAdder = longAdder;
    }

    @Override
    protected void compute() {

      int size = numbers.size();

      if (size > 1) {
        // 二分数量
        int parts = size / 2;
        int offset = 0;
        // 上半部
        List<Integer> leftPart = numbers.subList(0, parts);
        AddTask leftTask = new AddTask(leftPart, longAdder);
        // 下半部
        List<Integer> rightPart = numbers.subList(parts, size);
        AddTask rightTask = new AddTask(rightPart, longAdder);

        invokeAll(leftTask, rightTask);   // fork / join操作
      } else {
        // 当前元素只有一个的时候
        if (size == 0) {
          return;
        }

        Integer value = numbers.get(0);

        longAdder.add(Long.valueOf(value));
      }
    }
  }
}
