package com.songshuang.springboot.self.annotation;

import org.springframework.stereotype.Service;

@Service
public class AnnotationDemo {

  @ConfigDemo(config = "${annotation.config.url}")
  public void test() {
    System.out.printf("hello world");
  }
}
