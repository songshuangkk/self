package com.songshuang.springboot.self.jni;

public class JNITest {
  public native void sayHello();

  static {
    // 设置java.library.path的路径，使之能够调用到动态库
    System.setProperty("java.library.path", ".");
    // 调用动态库
    System.loadLibrary("test");
  }

  public static void main(String[] args) {
    JNITest jniTest = new JNITest();
    jniTest.sayHello();
  }
}
