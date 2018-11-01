package com.songshuang.springboot.self;

import java.io.*;

public class SerializableTest implements Serializable {

  private static final long serialVersionUID = 7561170179583679417L;

  private Integer name = 1;

  /**
   * 在进行序列化之前的操作。可以在这个地方进行一个数据的加密.
   * @param stream
   * @throws IOException
   */
  private void writeObject(ObjectOutputStream stream) throws IOException {
    this.name = 2;
    System.out.printf("Begin Write Serializable\n");
    stream.defaultWriteObject();
  }

  /**
   * 在进行反序列化的时候，可以在这个地方进行一个数据的解密.
   * @param stream
   * @throws IOException
   * @throws ClassNotFoundException
   */
  private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
    System.out.printf("Begin Read Serializable\n");
    stream.defaultReadObject();
    this.name = 2;
  }

  public static void main(String[] args) throws IOException {
    FileOutputStream fos = new FileOutputStream("tempdata.ser");
    try(ObjectOutputStream outputStream = new ObjectOutputStream(fos)) {
      outputStream.writeObject(new SerializableTest());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

