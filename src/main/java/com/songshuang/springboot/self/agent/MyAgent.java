package com.songshuang.springboot.self.agent;

import java.lang.instrument.Instrumentation;

public class MyAgent {

  public static void premain(String agentArgs, Instrumentation inst) {
    System.out.printf("this is an agent. args: %s\n", agentArgs);
  }
}