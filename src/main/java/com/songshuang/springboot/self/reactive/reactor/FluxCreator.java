package com.songshuang.springboot.self.reactive.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class FluxCreator {

  static void just() {
    Flux.just("Hello", "World").subscribe(System.out::println);
  }

  static void fromArray() {
    Flux.fromArray(new Integer[]{1, 2, 3, 4}).subscribe(System.out::println);
  }

  static void empty() {
    Flux.empty().subscribe(System.out::println);
  }

  static void interval() {
    Flux.interval(Duration.of(5, ChronoUnit.SECONDS)).subscribe(System.out::println);
  }

  static void generate() {
    Flux.generate(sink -> {
      sink.next("Hello");
      sink.complete();
    }).subscribe(System.out::println);

    final Random random = new Random();
    Flux.generate(ArrayList::new, (list, sink) -> {
      int value = random.nextInt(100);
      list.add(value);
      sink.next(value);
      if (list.size() == 10) {
        sink.complete();
      }

      return list;
    }).subscribe(System.out::println);
  }

  static void create() {
    Flux.create(sink -> {
      for (int i = 0; i < 10; i++) {
        sink.next(i);
      }

      sink.complete();
    }).subscribe(System.out::println);
  }

  public static void main(String[] args) {
    just();
    fromArray();
    empty();
    interval();
    generate();
  }
}
