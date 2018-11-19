package com.songshuang.springboot.self.classLoader;

public class ClassLoaderDemo {

  public static void main(String[] args) {
    ClassLoaderDemo demo = new ClassLoaderDemo();
    System.out.printf("slef class loader is %s\n", demo.getClass().getClassLoader());
    System.out.printf("parent class loader is %s\n", demo.getClass().getClassLoader().getParent());
    System.out.printf("parent parent class loader is %s\n", demo.getClass().getClassLoader().getParent().getParent());
  }
}
