package ru.hh.boksh.messaging.http;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hh.boksh.messaging.kafka.KafkaPublisher;
import ru.hh.boksh.messaging.rabbit.RabbitPublisher;

@RestController
public class SendMessage {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendMessage.class);

  private final RabbitPublisher rabbitPublisher;
  private final KafkaPublisher kafkaPublisher;

  public SendMessage(RabbitPublisher rabbitPublisher, KafkaPublisher kafkaPublisher) {
    this.rabbitPublisher = rabbitPublisher;
    this.kafkaPublisher = kafkaPublisher;
  }

  @RequestMapping(value = "/rabbit/exchange/{exchange}/routing_key/{routingKey}", method = RequestMethod.POST)
  public void sendMessageToRabbit(@PathVariable("exchange") String exchange,
                                  @PathVariable("routingKey") String routingKey,
                                  @RequestParam("messageBody") String messageBody) {
    LOGGER.info("got rabbit message to send: exchange={}, routing_key={}", exchange, routingKey);
    rabbitPublisher.send(exchange, routingKey, messageBody.getBytes(StandardCharsets.UTF_8)).thenAccept(
        (ignored) -> LOGGER.info("got ack for message: exchange={}, routing_key={}", exchange, routingKey)
    ).exceptionally(exception -> {
      LOGGER.error("got exception for rabbit message: exchange={}, routing_key={}", exchange, routingKey, exception);
      return null;
    });
    LOGGER.info("send rabbit message: exchange={}, routing_key={}", exchange, routingKey);
  }

  @RequestMapping(value = "/kafka/topic/{topic}", method = RequestMethod.POST)
  public void sendMessageToKafka(@PathVariable("topic") String topic,
                                 @RequestParam("key") String key,
                                 @RequestParam("messageBody") String messageBody) {
    LOGGER.info("got kafka message to send: topic={}, key={}", topic, key);
    kafkaPublisher.send(topic, key, messageBody).thenAccept(
        (ignored) -> LOGGER.info("got ack for kafka message: topic={}, key={}", topic, key)
    );
    LOGGER.info("send kafka message: topic={}, key={}", topic, key);
  }

}
