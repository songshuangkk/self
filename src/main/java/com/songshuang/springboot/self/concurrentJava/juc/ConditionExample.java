package com.songshuang.springboot.self.concurrentJava.juc;


import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

public class ConditionExample  {

  public static class ConditionDemo implements Condition {

    private ConditionDemo next;

    private Thread thread;

    private int lock;

    private ConditionDemo(int lock, Thread thread) {
      this.lock = lock;
      this.thread = thread;
    }

    public void addConditionDemo(ConditionDemo conditionDemo) {
      this.next = conditionDemo;
    }

    @Override
    public void await() throws InterruptedException {
      LockSupport.park(thread);
    }

    @Override
    public void awaitUninterruptibly() {

    }

    @Override
    public long awaitNanos(long nanosTimeout) throws InterruptedException {
      return 0;
    }

    @Override
    public boolean await(long time, TimeUnit unit) throws InterruptedException {
      return false;
    }

    @Override
    public boolean awaitUntil(Date deadline) throws InterruptedException {
      return false;
    }

    @Override
    public void signal() {
      LockSupport.unpark(this.next.thread);
    }

    @Override
    public void signalAll() {
      if (this.next != null) {
        ConditionDemo demo = this;
        while (demo.next != null) {
          ConditionDemo nextDemo = demo.next;
          LockSupport.unpark(nextDemo.thread);
          demo = nextDemo;
        }
      }

    }

    public void getLock() throws InterruptedException {
      if (lock == 10) {
        signalAll();
      } else {
        await();
      }
    }
  }
}
