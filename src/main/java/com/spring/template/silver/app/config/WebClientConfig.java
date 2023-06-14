package com.spring.template.silver.app.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

  @Bean
  public HttpClient httpClient() {
    return HttpClient.create()
        .tcpConfiguration(client -> client.option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000000)
            .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(1000, TimeUnit.SECONDS))
                .addHandlerLast(new WriteTimeoutHandler(1000, TimeUnit.SECONDS))));
  }

  @Bean
  public ReactorClientHttpConnector connector(HttpClient httpClient) {
    return new ReactorClientHttpConnector(httpClient);
  }

  @Bean
  public WebClient webClientService1(WebClient.Builder webClientBuilder, ReactorClientHttpConnector connector) {
    return webClientBuilder
        .clientConnector(connector)
        .baseUrl("http://localhost:8090")
        .build();
  }

  @Bean
  public WebClient webClientService2(WebClient.Builder webClientBuilder, ReactorClientHttpConnector connector) {
    return webClientBuilder
        .clientConnector(connector)
        .baseUrl("http://localhost:8099")
        .build();
  }

}
