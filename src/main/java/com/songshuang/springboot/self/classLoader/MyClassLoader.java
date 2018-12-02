package com.songshuang.springboot.self.classLoader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义ClassLoader.
 */
public class MyClassLoader extends ClassLoader {

  private String byteCode_Path;

  private MyClassLoader(String byteCode_Path) {
    this.byteCode_Path = byteCode_Path;
  }

  @Override
  protected Class<?> findClass(String className) {
    byte value[] = null;
    BufferedInputStream in = null;

    try {
      in = new BufferedInputStream(new FileInputStream(byteCode_Path + className + ".class"));
      value = new byte[in.available()];
      in.read(value);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != in) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return defineClass(value, 0, value.length);
  }

  public static void main(String[] args) throws ClassNotFoundException {
    MyClassLoader classLoader = new MyClassLoader("");
    System.out.printf("加载目标类加载器-> %s\n", classLoader.loadClass("Test").getClassLoader().getClass().getName());
    System.out.printf("当前类加载器的超类加载器 -> %s\n", classLoader.getParent().getClass().getName());
  }
}
