package com.songshuang.springboot.self.rocketmq;

import com.meipingmi.cloud.rocketmq.annotation.MQListener;
import com.meipingmi.cloud.rocketmq.core.ConsumerListener;
import com.songshuang.springboot.self.rocketmq.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created By songshuang on 2018/3/26
 * <p>
 * Talk is cheap. Show me the code.
 */
@Component
@MQListener(topic = "spring.rocketmq.consumer.consumers.test.topic", tags = "spring.rocketmq.consumer.consumers.test.tag")
@Slf4j
public class ConsumerDemo implements ConsumerListener<DemoMessage> {

  @Override
  public void processMessages(DemoMessage message) {
    log.info("message is {}", message);
  }
}
