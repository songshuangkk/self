package com.songshuang.springboot.self;

import com.songshuang.springboot.self.rocketmq.ProducerDemo;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SelfApplication {

	@Autowired
	private ProducerDemo producerDemo;

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SelfApplication.class, args);
	}

	@GetMapping("/send")
	public void send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
		producerDemo.sendMessage();
	}
}
