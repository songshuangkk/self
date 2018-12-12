package com.songshuang.springboot.self.agent.bytebuddy;

public interface Framework {
  <T> T secure(Class<T> type);
}
