package ru.hh.boksh.messaging.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

public class RabbitPublisher {

  private final Connection connection;

  public RabbitPublisher(ConnectionFactory connectionFactory) {
    try {
      connection = connectionFactory.newConnection();
    } catch (TimeoutException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> send(String exchange, String routingKey, byte[] body) {
    CompletableFuture<Void> future = new CompletableFuture<>();
    try {
      Channel channel = connection.createChannel();
      channel.confirmSelect();
      channel.addConfirmListener(
          (sequenceNumber, multiple) -> future.complete(null),
          (sequenceNumber, multiple) -> future.completeExceptionally(new RuntimeException(String.format("failed to get ack for %s", sequenceNumber)))
      );
      channel.basicPublish(exchange, routingKey, null, body);
      return future;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}