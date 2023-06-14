package com.spring.template.silver.app.config;

import brave.Tracing;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.micrometer.RabbitTemplateObservation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static String QUEUE_NAME_SEV1 = "queue_name_sev1";
  public static String ROUTING_KEY_SEV1 = "rabbitmq.service.1";

  public static String QUEUE_NAME_SEV2 = "queue_name_sev2";
  public static String ROUTING_KEY_SEV2 = "rabbitmq.service.2";

  public static String QUEUE_NAME_SEV3 = "queue_name_sev3";
  public static String ROUTING_KEY_SEV3 = "rabbitmq.service.3";

  public static String EXCHANGE_NAME = "exchange_name";

  @Bean
  public Queue queue_name_sev1() {
    return new Queue(QUEUE_NAME_SEV1, false);
  }

  @Bean
  public Queue queue_name_sev2() {
    return new Queue(QUEUE_NAME_SEV2, false);
  }

  @Bean
  public Queue queue_name_sev3() {
    return new Queue(QUEUE_NAME_SEV3, false);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Binding bindingSev1(Queue queue_name_sev1, TopicExchange exchange) {
    return BindingBuilder.bind(queue_name_sev1).to(exchange).with(ROUTING_KEY_SEV1);
  }

  @Bean
  public Binding bindingSev2(Queue queue_name_sev2, TopicExchange exchange) {
    return BindingBuilder.bind(queue_name_sev2).to(exchange).with(ROUTING_KEY_SEV2);
  }

  @Bean
  public Binding bindingSev3(Queue queue_name_sev3, TopicExchange exchange) {
    return BindingBuilder.bind(queue_name_sev3).to(exchange).with(ROUTING_KEY_SEV3);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Tracing tracing) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setObservationEnabled(true);
    rabbitTemplate.setObservationConvention(new RabbitTemplateObservation.DefaultRabbitTemplateObservationConvention());
    return rabbitTemplate;
  }

}