package com.songshuang.springboot.self.listener;

import com.songshuang.springboot.self.listener.event.MessageEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created By songshuang on 2018/3/22
 * <p>
 * Talk is cheap. Show me the code.
 */
@Component
public class MyListener {

  @EventListener
  public void handleMessage(MessageEvent message) {
    System.out.printf("Message is " + message.toString());
  }
}
