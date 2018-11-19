package com.songshuang.springboot.self.sort;

/**
 * 鸽巢排序Pigeonhole sort.
 */
public class PigeonholeSort {

  static int[] sort(int[] unsorted, int maxNumber) {
    int[] sorted = new int[100];

    for (int item: unsorted) {
      sorted[item]++;
    }

    return sorted;
  }

  public static void main(String[] args) {

    int[] x = { 99, 65, 24, 47, 47, 50, 99, 88, 66, 33, 66, 67, 31, 18, 24 };

    int[] result = sort(x, x.length);

    for (int index = 0; index < result.length; index++) {
      if (result[index] > 0) {
        System.out.printf("%d ", index);
      }
    }

  }
}
