package com.songshuang.springboot.self.guava.base;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created By songshuang on 2018/5/11
 * <p>
 * Talk is cheap. Show me the code.
 */
public class OptionalDemo {

  private static final Logger logger = LoggerFactory.getLogger(OptionalDemo.class);

  public static void main(String[] args) {

    Optional<Integer> possible = Optional.of(null);
    logger.info("is null? {}", possible.isPresent());
    logger.info("result is ", possible.get());
  }
}
