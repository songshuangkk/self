package com.songshuang.springboot.self.serializers.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.meipingmi.cloud.rocketmq.serializer.KryoSerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created By songshuang on 2018/3/26
 * <p>
 * Talk is cheap. Show me the code.
 */
public class KryoSerializerFactoryDemo {

  private static final Logger logger = LoggerFactory.getLogger(KryoSerializerFactory.class);

  private static volatile KryoPool kryoPool = null;

  public static Kryo get() {
    return getKryoPool().borrow();
  }

  public static void releaseKryo(Kryo kryo) {
    getKryoPool().release(kryo);
  }

  private static KryoPool getKryoPool() {
    if (kryoPool == null) {

      synchronized(KryoSerializerFactory.class) {

        if (kryoPool == null) {

          KryoFactory factory = () -> {
            Kryo kryo = new Kryo();
            // configure kryo instance, customize settings
            return kryo;
          };
          // Build pool with SoftReferences enabled (optional)
          kryoPool = new KryoPool.Builder(factory).softReferences().build();
        }

        return kryoPool;
      }
    }

    return kryoPool;
  }

  /**
   * 进行发序列化对象.
   *
   * @param bytes
   * @return
   */
  public static Object deSerialization(byte[] bytes, Class clazz) {
    ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
    Input input = new Input(inputStream);
    Kryo kryo = KryoSerializerFactory.get();
    try{
      return kryo.readObject(input, clazz);
    } finally {
      input.close();
      KryoSerializerFactory.releaseKryo(kryo);
    }
  }

  /**
   * 进行序列化对象.
   *
   * @param object
   * @return
   */
  public static byte[] serialization(Object object) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    Output output = new Output(byteArrayOutputStream);

    Kryo kryo = KryoSerializerFactory.get();

    try {
      kryo.writeObject(output, object);
    } finally {
      output.flush();
      output.close();
    }

    return byteArrayOutputStream.toByteArray();
  }
}
