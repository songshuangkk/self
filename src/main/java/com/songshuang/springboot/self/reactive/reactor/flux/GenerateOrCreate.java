package com.songshuang.springboot.self.reactive.reactor.flux;

import com.songshuang.springboot.self.reactive.webFlux.domin.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created By songshuang on 2018/4/19
 * <p>
 * Talk is cheap. Show me the code.
 */
public class GenerateOrCreate {

  private static final Logger logger = LoggerFactory.getLogger(GenerateOrCreate.class);

  public static void main(String[] args) {
    Flux.generate(ArrayList<User>::new, (list, sink) -> {
      User user = new User();
      user.setId(UUID.randomUUID().toString().toUpperCase());
      list.add(user);
      sink.next(user);
      if (list.size() == 4) {
        sink.complete();
      }

      return list;
    }).subscribe(System.out::println);
  }

  private static void printUser(User user) {
    logger.info("user id is {}",user.getId());
  }
}
