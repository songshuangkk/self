package com.songshuang.springboot.self.listener;

import com.songshuang.springboot.self.listener.event.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Created By songshuang on 2018/3/22
 * <p>
 * Talk is cheap. Show me the code.
 */
@Component
public class MyPublisher {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  public void publishListener() {
    applicationEventPublisher.publishEvent(new MessageEvent("Hello World"));
  }
}
