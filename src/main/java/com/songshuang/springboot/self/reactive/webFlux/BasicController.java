package com.songshuang.springboot.self.reactive.webFlux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created By songshuang on 2018/4/19
 * <p>
 * Talk is cheap. Show me the code.
 */
@RestController
public class BasicController {

  @GetMapping("/hello_world")
  public Mono<String> sayHelloWorld() {
    return Mono.just("Hello World");
  }
}
