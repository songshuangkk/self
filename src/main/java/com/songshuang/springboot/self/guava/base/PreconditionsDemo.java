package com.songshuang.springboot.self.guava.base;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By songshuang on 2018/5/11
 * <p>
 * Talk is cheap. Show me the code.
 */
@Slf4j
public class PreconditionsDemo {

  public static void main(String[] args) {

    Preconditions.checkNotNull(null, "Null Data");
  }
}
