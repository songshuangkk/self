package com.songshuang.springboot.self.listener.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created By songshuang on 2018/3/22
 * <p>
 * Talk is cheap. Show me the code.
 */
@Data
public class MessageEvent {

  private String msg;

  public MessageEvent(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return "Message is " + msg;
  }
}
