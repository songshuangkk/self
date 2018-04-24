package com.songshuang.springboot.self.reactive.webFlux;



import com.songshuang.springboot.self.reactive.webFlux.domin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Created By songshuang on 2018/4/19
 * <p>
 * Talk is cheap. Show me the code.
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("")
  public Flux<User> list() {
    return userService.list();
  }

  @GetMapping("{id}")
  public Mono<User> getById(@PathVariable("id") String id) {
    return userService.getById(id);
  }

  @PostMapping("")
  public Flux<User> create(@RequestBody final Flux<User> users) {
    return userService.createOrUpdate(users);
  }

  @PutMapping("/{id}")
  public Mono<User> update(@PathVariable("id") String id, @RequestBody User user) {
    Objects.requireNonNull(user);
    user.setId(id);
    return this.userService.createOrUpdate(user);
  }

  @DeleteMapping("/{id}")
  public Mono<User> delete(@PathVariable("id") String id) {
    return userService.delete(id);
  }
}
