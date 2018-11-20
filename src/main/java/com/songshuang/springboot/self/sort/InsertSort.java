package com.songshuang.springboot.self.sort;

/**
 * 插入排序.
 * 插入排序伪代码:
 * x[n]的数组进行插入排序.
 *
 * <pre>
 *   for i = [1, n)
 *      t = x[i]
 *      for (j = i; j > 0 && t < x[j-1]; j++)
 *        x[j] = x[j-1]
 *      x[j] = t
 * </pre>
 */
public class InsertSort extends PopSort{

  static void print(int[] arr) {
    for (int item: arr) {
      System.out.printf("%d ", item);
    }
  }

  static void sort(int[] unsort) {

    for (int i=1; i<unsort.length; i++) {
      int temp = unsort[i];
      int j=i;
      for (;j>0 && temp < unsort[j-1]; j--) {
        unsort[j] = unsort[j-1];
      }
      unsort[j] = temp;
    }
  }

  public static void main(String[] args) {

    int[] unsorted = new int[]{1, 4, 6, 2, 7, 3, 9, 8, 5, 0};
    long time = System.nanoTime();
    sort(unsorted);
    System.out.printf("insert sort execute time = %d\n", System.nanoTime() - time);

    unsorted = new int[]{1, 4, 6, 2, 7, 3, 9, 8, 5, 0};
    time = System.nanoTime();
    InsertSort.popSort(unsorted);
    System.out.printf("pop sort execute time = %d\n", System.nanoTime() - time);
    print(unsorted);
  }
}
