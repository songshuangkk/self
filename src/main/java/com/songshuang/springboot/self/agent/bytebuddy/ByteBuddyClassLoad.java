package com.songshuang.springboot.self.agent.bytebuddy;

import com.google.gson.Gson;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * 策略定义在 ClassLoadingStrategy.Default 中.
 * WRAPPER 策略创建一个新的包装 ClassLoader；
 * CHILD_FIRST 策略创建一个类似于第一个子类优先的类加载器；
 * INJECTION 策略使用反射注入动态类型。
 *
 * WRAPPER 和 CHILD_FIRST 策略也可以在所谓的清单版本中使用，即使在加载类之后，类型的二进制格式也被保留。
 * 这些替代版本使得类加载器的类的二进制表示可以通过 `ClassLoader
 */
public class ByteBuddyClassLoad {

  static void wrapperClassLoadingStrategy() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
    ByteBuddy byteBuddy = new ByteBuddy();

    Class clazz = byteBuddy
        .subclass(ByteBuddyDemo.class)
        .make()
        .load(ByteBuddyClassLoad.class.getClassLoader(), ClassLoadingStrategy.Default.CHILD_FIRST)
        .getLoaded();

    System.out.printf("create class\nload class is %s\n", clazz.getName());

    Object object = clazz.newInstance();

    System.out.printf("%s\n", object.toString());
  }

  static void method() throws IllegalAccessException, InstantiationException {
    ByteBuddy byteBuddy = new ByteBuddy();

    Class clazz = byteBuddy
        .subclass(ByteBuddyDemo.class)
        .method(ElementMatchers.named("toString"))
        .intercept(FixedValue.value("Hello world"))
        .method(ElementMatchers.named("getName"))
        .intercept(FixedValue.value("song shuang"))
        .make()
        .load(ByteBuddyClassLoad.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
        .getLoaded();

    System.out.printf("method intercept %s\n", clazz.newInstance());
  }

  /**
   * 调用方法.
   */
  static void invokeMethod() {
  }

  public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
    wrapperClassLoadingStrategy();

    method();

    invokeMethod();
  }

  public static String interceptMethod() {
    System.out.printf("Intercept Method invoked.\n");
    return "Intercept Method invoked.";
  }
}
