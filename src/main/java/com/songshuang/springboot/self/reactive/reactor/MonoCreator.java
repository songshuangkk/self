package com.songshuang.springboot.self.reactive.reactor;

import reactor.core.publisher.Mono;

public class MonoCreator {

  static void just() {
    Mono.just("Hello").subscribe(System.out::println);
  }

  static void empty() {
    Mono.empty().subscribe(System.out::println);
  }

  public static void main(String[] args) {
    just();
    empty();
  }
}
