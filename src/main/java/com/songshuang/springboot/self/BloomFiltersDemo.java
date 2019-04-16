package com.songshuang.springboot.self;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import java.util.Random;

/**
 *  布隆过滤器的使用.
 */
public class BloomFiltersDemo {

  public static void main(String[] args) {
    BloomFilter<Integer> bloomFilter = BloomFilter.create(new IntegerFunnel(), 20);

    Random random = new Random(50);
    for (int i=0; i<20; i++) {
      int value = random.nextInt();
      bloomFilter.put(value);
    }

    boolean result = bloomFilter.mightContain(30);
    System.out.printf("%s", result);
  }

  static class IntegerFunnel implements Funnel<Integer> {

    @Override
    public void funnel(Integer integer, PrimitiveSink primitiveSink) {
      primitiveSink.putInt(integer);
    }
  }
}
