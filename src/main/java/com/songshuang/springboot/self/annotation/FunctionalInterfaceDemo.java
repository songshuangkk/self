package com.songshuang.springboot.self.annotation;

import java.util.stream.Stream;

public class FunctionalInterfaceDemo {

  public static void main(String[] args) {
    // 使用lambda的方式，结合FunctionalInterface注解来实现只有一个功能的接口
    FunctionalInterfaceService functionalInterfaceService = () -> Stream.of("a", "b", "c");

    functionalInterfaceService.getString().forEach(item -> {
      System.out.printf(" result = %s\n", item);
    });
  }
}
