package com.songshuang.springboot.self.rocketmq;

import com.meipingmi.cloud.rocketmq.core.ProducerTemplate;
import com.meipingmi.cloud.rocketmq.enums.MessageDelayLevelEnum;
import com.songshuang.springboot.self.rocketmq.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created By songshuang on 2018/3/27
 * <p>
 * Talk is cheap. Show me the code.
 */
@Component
@Slf4j
public class ProducerDemo {

  @Resource
  private ProducerTemplate producerTemplate;

  public void sendMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
    DemoMessage message = new DemoMessage();
    message.setName("SongShuang");
    message.setMessage("Hello RocketMq");

    SendResult sendResult = producerTemplate.sendWithTag("TOPIC-songshuang", message, "test-tags");
    log.info("send message result is {}", sendResult);

    SendResult delayResult = producerTemplate.sendDelayMessageWithTags("TOPIC-songshuang", message,"test-tags", MessageDelayLevelEnum.FIVE_MINUTES.getLevel());
    log.info("send delay message result is {}", delayResult);
  }
}
