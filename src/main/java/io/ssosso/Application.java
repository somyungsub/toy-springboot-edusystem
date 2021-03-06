package io.ssosso;

import io.ssosso.edu.Edu;
import io.ssosso.edu.EduRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@SpringBootApplication
public class Application {

  @Autowired
  EduRepository eduRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  // TODO 캐시로 설정하여 관리, 일단 당분간은 Bean으로 활용
  @Bean
  @Lazy(value = false)
  public List<Edu> eduAllList() {
    return eduRepository.findAll();
  }

}
