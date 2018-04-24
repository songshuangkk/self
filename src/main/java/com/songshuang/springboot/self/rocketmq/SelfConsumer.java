package com.songshuang.springboot.self.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created By songshuang on 2018/3/27
 * <p>
 * Talk is cheap. Show me the code.
 */
@Component
@Slf4j
public class SelfConsumer implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {

    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("DEFAULT_PRODUCER_Test");

    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

    consumer.subscribe("TOPIC-songshuang", "*");

    consumer.registerMessageListener(new MessageListenerOrderly() {
      AtomicLong consumeTimes = new AtomicLong(0);

      @Override
      public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        context.setAutoCommit(false);
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
        this.consumeTimes.incrementAndGet();
        if ((this.consumeTimes.get() % 2) == 0) {
          return ConsumeOrderlyStatus.SUCCESS;
        } else if ((this.consumeTimes.get() % 3) == 0) {
          return ConsumeOrderlyStatus.ROLLBACK;
        } else if ((this.consumeTimes.get() % 4) == 0) {
          return ConsumeOrderlyStatus.COMMIT;
        } else if ((this.consumeTimes.get() % 5) == 0) {
          context.setSuspendCurrentQueueTimeMillis(3000);
          return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }

        return ConsumeOrderlyStatus.SUCCESS;
      }
    });

    consumer.start();

  }
}
