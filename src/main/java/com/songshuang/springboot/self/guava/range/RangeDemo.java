package com.songshuang.springboot.self.guava.range;

import com.google.common.collect.Range;

public class RangeDemo {

  public static void main(String[] args) {
    Range<Double> doubleRange = Range.openClosed(0d, 10d);

    assert doubleRange.contains(1.1);
    System.out.printf("start is %f end is %f\n", doubleRange.lowerEndpoint(), doubleRange.upperEndpoint());
    System.out.printf("%f\n", doubleRange.upperEndpoint());

    Range<Long> longRange = Range.closedOpen(0L, 11L);

    System.out.printf("long range start is %d end is %d contains start is %s end is %s\n",
        longRange.lowerEndpoint(),
        longRange.upperEndpoint(),
        longRange.lowerBoundType(),
        longRange.upperBoundType());
  }
}
