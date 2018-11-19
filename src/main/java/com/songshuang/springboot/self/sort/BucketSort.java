package com.songshuang.springboot.self.sort;

/**
 * 桶排序.
 */
public class BucketSort {

  static int[] sort(int[] unsorted, int maxNumber) {
    int[] sorted = new int[maxNumber + 1];

    for (int anUnsorted : unsorted) {
      if (anUnsorted > 0) {
        sorted[anUnsorted] = anUnsorted;
      }
    }

    return sorted;
  }

  public static void main(String[] args) {
    int[] unsorted = {99, 65, 24, 47, 50, 88, 33, 66, 67, 31, 18};

    int[] sorted = sort(unsorted, 99);

    for (int item : sorted) {
      if (item > 0) {
        System.out.printf("%d ", item);
      }
    }
  }
}
