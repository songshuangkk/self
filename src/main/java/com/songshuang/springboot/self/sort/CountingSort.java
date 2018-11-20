package com.songshuang.springboot.self.sort;

/**
 * 计数排序.
 */
public class CountingSort {

  static void print(int[] arr) {
    for (int item: arr) {
      System.out.printf("%d ", item);
    }
    System.out.printf("\n");
  }

  static int[] countingSort(int[] A) {
    int[] B = new int[A.length];
    // 假设A中的数据a'有，0<=a' && a' < k并且k=100
    int k = 100;
    countingSort(A, B, k);
    return B;
  }

  static void countingSort(int[] A, int[] B, int k) {
    int[] C = new int[k];
    // 计数
    for (int j = 0; j < A.length; j++) {
      int a = A[j];
      C[a] += 1;
    }
    print(C);
    // 求计数和
    for (int i = 1; i < k; i++) {
      C[i] = C[i] + C[i - 1];
    }
    print(C);
    // 整理
    for (int j = A.length - 1; j >= 0; j--) {
      int a = A[j];
      B[C[a] - 1] = a;
      // 为了防止相同的数据在同一个位置，所以每次计算完了之后就进行一个响应的减一.
      C[a] -= 1;
    }
  }

  static void normalSort(int[] unsorted) {
    for (int i=0; i<unsorted.length; i++) {
      for (int j = i+1; j<unsorted.length; j++) {
        if (unsorted[i] > unsorted[j]) {
          int temp = unsorted[j];
          unsorted[j] = unsorted[i];
          unsorted[i] = temp;
        }
      }
    }

    print(unsorted);
  }

  public static void main(String[] args) {
    // 计数排序.
    long time = System.nanoTime();
    int[] A = CountingSort.countingSort(new int[]{16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1, 16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1, 16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1, 16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1});
    System.out.printf("execute time = %d \n", System.nanoTime()-time);
    print(A);

    // 使用冒泡排序进行一个速度校验对比.
    int[] temp = new int[]{16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1, 16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1, 16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1, 16, 4, 4, 10, 14, 7, 9, 3, 2, 8, 1};
    time = System.nanoTime();
    normalSort(temp);
    System.out.printf("execute time = %d\n", System.nanoTime()-time);
  }
}
