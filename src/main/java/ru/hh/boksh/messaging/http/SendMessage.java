package ru.hh.boksh.messaging.http;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hh.boksh.messaging.rabbit.RabbitPublisher;

@RestController
public class SendMessage {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendMessage.class);

  private RabbitPublisher rabbitPublisher;

  public SendMessage(RabbitPublisher rabbitPublisher) {
    this.rabbitPublisher = rabbitPublisher;
  }

  @RequestMapping(value = "/rabbit/exchange/{exchange}/routing_key/{routingKey}", method = RequestMethod.POST)
  public void sendMessageToRabbit(@PathVariable("exchange") String exchange,
                                    @PathVariable("routingKey") String routingKey,
                                    @RequestParam("messageBody") String messageBody) {
    LOGGER.info("got message to send: exchange={}, routing_key={}", exchange, routingKey);
    rabbitPublisher.send(exchange, routingKey, messageBody.getBytes(StandardCharsets.UTF_8)).thenAccept(
        (ignored) -> LOGGER.info("got ack for message message: exchange={}, routing_key={}", exchange, routingKey)
    );
    LOGGER.info("send message: exchange={}, routing_key={}", exchange, routingKey);
  }

}
