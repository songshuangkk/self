package com.songshuang.springboot.self.sort;

public class PopSort {

  public static void popSort(int[] unsorted) {
    for (int i=0; i<unsorted.length; i++) {
      for (int j = i+1; j<unsorted.length; j++) {
        if (unsorted[i] > unsorted[j]) {
          int temp = unsorted[j];
          unsorted[j] = unsorted[i];
          unsorted[i] = temp;
        }
      }
    }
  }
}
