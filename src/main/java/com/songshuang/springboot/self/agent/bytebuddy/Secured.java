package com.songshuang.springboot.self.agent.bytebuddy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {

  String user();
}
