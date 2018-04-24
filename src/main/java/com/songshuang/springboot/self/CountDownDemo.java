package com.songshuang.springboot.self;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
public class CountDownDemo {

  public static class Videoconference implements Runnable {

    private final CountDownLatch controller;

    public Videoconference(int number) {
      controller = new CountDownLatch(number);
    }

    public void arrive(String name) {
      System.out.printf("%s has arrived;", name);
      controller.countDown();
      System.out.printf("VideoConfreence: Waiting for %d participants", controller.getCount());
    }

    @Override
    public void run() {
      System.out.printf("VideoConference: Initialization: %d participants.", controller.getCount());

      try {
        System.out.printf("VideoConference: Let's start...");
        controller.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static class Participant implements Runnable {

    private Videoconference conference;

    private String name;

    public Participant(Videoconference conference, String name) {
      this.conference = conference;
      this.name = name;
    }

    @Override
    public void run() {
      long duration = (long) (Math.random() * 10);

      try {
        TimeUnit.SECONDS.sleep(duration);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      conference.arrive(name);
    }
  }

  public static void main(String[] args) {
    Videoconference conference = new Videoconference(10);

    Thread threadConference = new Thread(conference);

    threadConference.start();

    for (int i=0; i<10; i++){
      Participant p = new Participant(conference, "Participant ");
      Thread t = new Thread(p);
      t.start();
    }
  }
}
