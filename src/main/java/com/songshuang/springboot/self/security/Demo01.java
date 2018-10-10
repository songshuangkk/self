package com.songshuang.springboot.self.security;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;

public class Demo01 {

  public static void main(String[] args) {
    String os = AccessController.doPrivileged(
        (PrivilegedAction<String>)() -> System.getProperty("os.name"));

    if (!os.toUpperCase(Locale.US).contains("WINDOWS")) {
      System.out.println("Not Windows. Skip test.");
    }

  }
}
