package com.spring.template.silver.app.controller;

import com.spring.template.silver.app.annotation.LogMethod;
import com.spring.template.silver.app.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
@AllArgsConstructor
public class TestController {

  private final WebClient webClientService1;
  private final WebClient webClientService2;

  private RabbitTemplate rabbitTemplate;


  @GetMapping("/call/service1")
  @LogMethod
  public Mono<Map> test1() {
    return webClientService1.get().uri("/api/call/service2").retrieve().bodyToMono(Map.class);
  }

  @GetMapping("/call/service2")
  @LogMethod
  public Mono<Map> test2() {
    return webClientService2.get().uri("/api/randomData").retrieve().bodyToMono(Map.class);
  }

  @GetMapping("/randomData")
  @LogMethod
  public Mono<Map<String, String>> randomStuff() {
    Map<String, String> data = new HashMap<>();
    data.put("data", "random data");
    return Mono.just(data);
  }

  @PostMapping("/sendmsg")
  @LogMethod
  public void messing() {
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SEV1, "Hello from service 1");
    log.info("send message success");
  }

}
