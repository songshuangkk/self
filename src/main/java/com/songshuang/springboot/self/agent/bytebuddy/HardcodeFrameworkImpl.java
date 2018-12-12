package com.songshuang.springboot.self.agent.bytebuddy;

public class HardcodeFrameworkImpl implements Framework {
  @Override
  public <T> T secure(Class<T> type) {
    if (type == Service.class) {
      return (T) new SecuredService();
    } else {
      throw new IllegalArgumentException("Unknown: " + type);
    }
  }
}
