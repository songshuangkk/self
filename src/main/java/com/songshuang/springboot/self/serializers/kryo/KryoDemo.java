package com.songshuang.springboot.self.serializers.kryo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created By songshuang on 2018/3/26
 * <p>
 * Talk is cheap. Show me the code.
 */
public class KryoDemo {

  private static final Logger logger = LoggerFactory.getLogger(KryoDemo.class);

  public static void main(String[] args) throws IOException {

    Message message = new Message();
    message.setMessage("Hello World");

//    Kryo kryo = new Kryo();
//
//    ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
//
//    Output output = new Output(outputStream);
//
//    kryo.writeObject(output, message);
//
//    byte[] bytes = output.toBytes();
//
//    output.flush();
//
//    ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
//
//    Input input = new Input(inputStream);
//
//    Message result = kryo.readObject(input, Message.class);
//
//    input.close();
//
//    output.close();

    byte[] bytes = KryoSerializerFactoryDemo.serialization(message);

    Message result = (Message) KryoSerializerFactoryDemo.deSerialization(bytes, Message.class);
    logger.info("result is {}" , result);
  }

  @Data
  private static class Message {

    private String message;
  }
}
