package com.songshuang.springboot.self.agent.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import net.bytebuddy.matcher.ElementMatchers;

/**
 *  @BindingPriority: 注解，数值越大，就调用最大的方法.
 *  必须使用static的方法.
 */
public class Bar {

//  public static String sayHellBar() {
//    return "Hello in Bar!";
//  }

  @BindingPriority(1)
  public static String say() {
    return "Hello say";
  }

  @BindingPriority(2)
  public static String sayBar() {
    return "Say Bar";
  }

  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    // 进行方法的覆盖调用.
    String r = new ByteBuddy()
        .subclass(Foo.class)
        .method(ElementMatchers.named("sayHelloFoo")
            .and(ElementMatchers.isDeclaredBy(Foo.class))
            .and(ElementMatchers.returns(String.class)))
        .intercept(MethodDelegation.to(Bar.class))
        .make()
        .load(Bar.class.getClassLoader())
        .getLoaded()
        .newInstance()
        .sayHelloFoo();
    System.out.printf("result is %s\n", r);
  }
}
