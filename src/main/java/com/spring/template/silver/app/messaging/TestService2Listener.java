package com.spring.template.silver.app.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
@Profile("sev2")
public class TestService2Listener {

  @RabbitListener(
      queues = "queue_name_sev2"
  )
  public void process(Message message) {
    String requestBody = new String(message.getBody());
    MessageProperties messageProperties = message.getMessageProperties();

    log.info("Receive the message with Routing Key: {}, \n Request Body: {}, \n Headers: {}",
        messageProperties.getReceivedRoutingKey(), requestBody, messageProperties.getHeaders());

  }

}
