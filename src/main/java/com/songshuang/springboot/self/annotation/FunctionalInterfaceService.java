package com.songshuang.springboot.self.annotation;

import java.util.stream.Stream;

@FunctionalInterface
public interface FunctionalInterfaceService {

  Stream<String> getString();
}
