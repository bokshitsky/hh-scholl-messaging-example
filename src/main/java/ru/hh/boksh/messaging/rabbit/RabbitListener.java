package ru.hh.boksh.messaging.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.boksh.messaging.http.SendMessage;

public class RabbitListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendMessage.class);
  private final ConnectionFactory connectionFactory;

  public RabbitListener(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
    Stream.of("queue_1", "queue_2", "queue_3").forEach(this::listenToQueue);
  }

  private void listenToQueue(String queueName) {
    try {
      Connection connection = connectionFactory.newConnection();
      Channel channel = connection.createChannel();

      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        LOGGER.info("got message in queue {}: '{}'", queueName, message);
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      };

      channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});
    } catch (TimeoutException | IOException e) {
      throw new RuntimeException(e);
    }
  }

}
