package com.songshuang.springboot.self.reactive.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ReactorOperator {

  static void buffer() {
    Flux.just(1, 2, 3).buffer(2).subscribe(System.out::println);
  }

  static void bufferTimeout() {
//    Flux.just(1, 2, 3, 4).bufferTimeout(1, Duration.of(1, TimeUnit.SECONDS)).subscribe(System.out::println);
  }

  public static void main(String[] args) {
    buffer();
    bufferTimeout();
  }
}
