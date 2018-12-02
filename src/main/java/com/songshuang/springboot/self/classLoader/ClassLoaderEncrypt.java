package com.songshuang.springboot.self.classLoader;

import java.io.*;

/**
 * ClassLoader加密解密操作.
 */
public class ClassLoaderEncrypt extends ClassLoader {

  private String byteCode_Path;

  private byte[] key;

  ClassLoaderEncrypt(String byteCode_Path, byte[] key) {
    this.byteCode_Path = byteCode_Path;
    this.key = key;
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

    value = Use3DES.encrypt(key, value);
    return defineClass(value, 0, value.length);
  }
  public static void main(String[] args) {
    BufferedInputStream in = null;

    try {
      in = new BufferedInputStream(new FileInputStream(""));
      byte[] src = new byte[in.available()];
      in.read(src);
      in.close();
      // 对称加密秘钥
      byte[] key = "01234567890".getBytes();
      // 先对字节文件进行加密
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(""));
      out.write(Use3DES.encrypt(key, src));
      out.close();
      ClassLoaderEncrypt classLoaderEncrypt = new ClassLoaderEncrypt("", key);
      System.out.printf("TestDemo%s\n", classLoaderEncrypt.loadClass("").getClassLoader().getClass().getName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
