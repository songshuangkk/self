package com.songshuang.springboot.self.guava.sort;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class SortDemo {

  public static void main(String[] args) {
    List<Map<String, Integer>> list = Lists.newArrayList();
    add(list, 22);
    add(list, 1);
    add(list, 3);
    add(list, 0);
    add(list, 33);
    add(list, 16);

    Ordering<Map<String, Integer>> ordering = new Ordering<Map<String, Integer>>() {
      @Override
      public int compare(@Nullable Map<String, Integer> stringIntegerMap, @Nullable Map<String, Integer> t1) {
        return Integer.compare(stringIntegerMap.get("rate"), t1.get("rate")) ;
      }
    };

    list.sort(ordering.reversed());

    list.forEach(item -> {
      System.out.printf("%d\n", item.get("rate"));
    });
  }

  private static void add(List<Map<String, Integer>> list, Integer rate) {
    Map<String, Integer> item = Maps.newHashMap();
    item.put("rate", rate);
    list.add(item);
  }
}
