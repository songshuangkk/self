package com.songshuang.springboot.self.snappy;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * 数据的压缩与解压
 */
public class CompressionDemo {

  public static void main(String[] args) throws IOException {
    String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.";

    System.out.printf("Before compress size = %d\n", input.getBytes().length);
    byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
    System.out.printf("After compressed size = %d\n", compressed.length);
    byte[] uncompressed = Snappy.uncompress(compressed);

    System.out.printf("result = %s\n", new String(uncompressed, "UTF-8"));
  }
}
