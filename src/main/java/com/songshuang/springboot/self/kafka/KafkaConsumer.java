package com.songshuang.springboot.self.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  @KafkaListener(topics = "aigateway_image_politics")
  public void message(ConsumerRecord<String, String> record) {
    logger.info("Message is {}", record);
  }
}
