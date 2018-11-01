package com.songshuang.springboot.self.gc;

import java.util.Map;
import java.util.Random;

/**
 * 模拟GC overhead limit的应用.
 * java -Xmx100m -XX:+UseParallelGC GCOverheadLimitDemo
 */
public class GCOverheadLimitDemo {

  public static void main(String[] args) {
    Map map = System.getProperties();
    Random r = new Random();
    while (true) {
      map.put(r.nextInt(), "value");
    }
  }
}
