package com.songshuang.springboot.self.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class InstrumentAgent {

  public static void premain(String args, Instrumentation instrumentation) {
    System.out.printf("This is an agent.\n");
    ClassFileTransformer transformer = new PerformMonitorTransformer();
    instrumentation.addTransformer(transformer);
  }
}
