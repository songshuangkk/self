package com.songshuang.springboot.self.classLoader;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密操作类.
 */
public class Use3DES {

  private static final String ALGORITHM = "DESede";   // 定义加密算法

  public static byte[] encrypt(byte[] key, byte[] src) {
    byte[] value = null;
    try {
      // 生成秘钥
      SecretKey deskey = new SecretKeySpec(key, ALGORITHM);
      // 对目标数据执行加密操作
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, deskey);
      value = cipher.doFinal();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }

  public static byte[] decrypt(byte[] key, byte[] src) {
    byte[] value= null;
    try {
      // 生成秘钥
      SecretKey deskey = new SecretKeySpec(key, ALGORITHM);
      // 对目标数据执行解密操作
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, deskey);
      value = cipher.doFinal(src);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }
}
