package com.songshuang.springboot.self.reactive.reactor;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By songshuang on 2018/3/23
 * <p>
 * Talk is cheap. Show me the code.
 */
public class ReactorDemo {

  public static void main(String[] args) {
    Flux<String> just= Flux.just("1", "2", "3");

    Mono<String> mono = Mono.just("foo");

    Publisher<String> justPublisher = Mono.just("foo");

    List<Integer> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4).subscribe(elements::add);

    System.out.printf("elements is " + elements.toString());
  }
}
