package com.songshuang.springboot.self.agent.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 定义新的方法和属性字段.
 */
public class MethodFieldDefinition {

  public static void main(String[] args) throws NoSuchMethodException {
    Class<?> type = new ByteBuddy()
        .subclass(Object.class)
        .name("MyClassName")
        .defineMethod("custom", String.class, Modifier.PUBLIC)
        .intercept(MethodDelegation.to(Bar.class))
        .defineField("x", String.class, Modifier.PUBLIC)
        .make()
        .load(MethodFieldDefinition.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
        .getLoaded();

    Method m = type.getDeclaredMethod("custom", null);
  }
}
