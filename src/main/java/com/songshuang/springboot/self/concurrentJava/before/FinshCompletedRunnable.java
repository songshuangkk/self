package com.songshuang.springboot.self.concurrentJava.before;

/**
 * Created By songshuang on 2018/4/23
 * <p>
 * Talk is cheap. Show me the code.
 */
public class FinshCompletedRunnable {

  public static void main(String[] args) {

    CompletableRunnable completableRunnable = new CompletableRunnable();

    Thread thread = new Thread(completableRunnable, "Sub");

    thread.start();

    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Thread result = " + completableRunnable.isCompleted());
  }

  private static class CompletableRunnable implements Runnable {

    private boolean completed = false;

    @Override
    public void run() {

      System.out.println("Thread Hello World");

      completed = true;
    }

    public boolean isCompleted() {
      return completed;
    }

    public void setCompleted(boolean completed) {
      this.completed = completed;
    }
  }
}
