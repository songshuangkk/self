package com.songshuang.springboot.self.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducer {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @GetMapping("/kafka")
  public void send() {
    kafkaTemplate.send("aigateway_image_politics", "hello world");
  }
}
