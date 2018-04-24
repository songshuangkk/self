package com.songshuang.springboot.self.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By songshuang on 2018/3/22
 * <p>
 * Talk is cheap. Show me the code.
 */
@RestController
public class MyListenerController {

  @Autowired
  private MyPublisher myPublisher;

  @GetMapping("/myListener")
  public void myListener() {
    myPublisher.publishListener();
  }
}
