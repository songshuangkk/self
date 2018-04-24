package com.songshuang.springboot.self.reactive.webFlux;

import com.songshuang.springboot.self.reactive.webFlux.domin.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By songshuang on 2018/4/19
 * <p>
 * Talk is cheap. Show me the code.
 */
@Service
public class UserService {

  private final Map<String, User> data = new ConcurrentHashMap<>(5);

  public Flux<User> list() {
    return Flux.fromIterable(this.data.values());
  }

  public Flux<User> getById(final Flux<String> ids) {
    return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
  }

  public Mono<User> getById(final String id) {
    return Mono.justOrEmpty(this.data.get(id))
            .switchIfEmpty(Mono.error(new RuntimeException()));
  }

  public Flux<User> createOrUpdate(final Flux<User> users) {
    return users.doOnNext(user -> this.data.put(user.getId(), user));
  }

  public Mono<User> createOrUpdate(final User user) {
    this.data.put(user.getId(), user);
    return Mono.just(user);
  }

  public Mono<User> delete(final String id) {
    return Mono.justOrEmpty(this.data.remove(id));
  }
}
