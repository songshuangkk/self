package com.songshuang.springboot.self.introspect;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;

/**
 * Created By songshuang on 2018/5/11
 * <p>
 * Talk is cheap. Show me the code.
 */
public class IntrospectDemo {

  private static final Logger logger = LoggerFactory.getLogger(IntrospectDemo.class);

  public static void main(String[] args) throws IntrospectionException {
    BeanInfo beanInfo = Introspector.getBeanInfo(User.class);

    logger.info("info");

  }

  @Data
  private static class User {

    private String name;

    private Integer age;

  }
}
