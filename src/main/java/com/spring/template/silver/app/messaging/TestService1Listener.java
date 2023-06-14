package com.spring.template.silver.app.messaging;

import com.spring.template.silver.app.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
@Profile("sev1")
public class TestService1Listener {

  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(
      queues = "queue_name_sev1"
  )
  public void process2(Message message) {

    String requestBody = new String(message.getBody());
    MessageProperties messageProperties = message.getMessageProperties();
    log.info("Receive the message with Routing Key: {}, \n Request Body: {}, \n Headers: {}",
        messageProperties.getReceivedRoutingKey(), requestBody, messageProperties.getHeaders());
    rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SEV2, "Hello from service 2");
    log.info("send message success");

  }

}
