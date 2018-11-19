package com.songshuang.springboot.self.sort;

import org.springframework.util.StringUtils;

/**
 * 基数排序.
 */
public class RadixSort {

  static void print(int[] sorted) {
    for (int item : sorted) {
      System.out.printf("%d ", item);
    }
  }

  static int[] sort(int[] unsorted) {

    int[] result = new int[unsorted.length];

    int[] sorted = new int[unsorted.length];

    for (int i=0; i<unsorted.length; i++) {
      for (int item : unsorted) {
        int temp = (item / (int) Math.pow(10, i)) % 10;
      }
    }

    print(sorted);

    return result;
  }

  public static void main(String[] args) {
    int[] x = {999999999, 65, 24, 47, 13, 50, 92, 88, 66, 33, 22445, 10001, 624159, 624158, 624155501};

    sort(x);
  }
}
