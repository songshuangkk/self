package com.songshuang.springboot.self;

import com.songshuang.springboot.self.annotation.AnnotationDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SelfApplication {

  @Autowired
  private AnnotationDemo annotationDemo;

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SelfApplication.class, args);
	}

	@GetMapping("/annotation")
  public void annotationTest() {
	  annotationDemo.test();
  }
}
