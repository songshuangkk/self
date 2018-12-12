package com.songshuang.springboot.self.agent.bytebuddy;

public class SecuredService extends Service {

  @Override
  void deleteEverything() {
    if (UserHolder.user.equals("ADMIN")) {
      super.deleteEverything();
    } else {
      throw new IllegalStateException("Not authorized.");
    }
  }
}
