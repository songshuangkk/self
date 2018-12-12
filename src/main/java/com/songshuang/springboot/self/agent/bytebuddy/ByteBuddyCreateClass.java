package com.songshuang.springboot.self.agent.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

public class ByteBuddyCreateClass {

  public static void main(String[] args) {
    // 创建一个继承Object的类
    DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
        .subclass(Object.class)
        .name("com.songshuang.springboot.self.agent.bytebuddy.ByteBuddyDemo")     // 必须指定一个完整的包名路径下的class
        .make();

    System.out.printf("Class name %s\n", dynamicType.load(ByteBuddyCreateClass.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded().getName());

    DynamicType.Unloaded<?> dynamicTypeNamingStrategy = new ByteBuddy()
        .with(new NamingStrategy.AbstractBase() {
          @Override
          protected String name(TypeDescription superClass) {
            return "com.songshuang.springboot.self.agent.bytebuddy.ByteBuddyDemo";
          }
        })
        .subclass(Object.class).make();
  }
}
