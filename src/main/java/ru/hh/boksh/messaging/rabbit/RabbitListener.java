package ru.hh.boksh.messaging.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitListener {

  private ConnectionFactory connectionFactory;

  public RabbitListener(ConnectionFactory connectionFactory) {
    try {
      Connection connection = connectionFactory.newConnection();

    } catch (TimeoutException | IOException e) {
      throw new RuntimeException(e);
    }
  }


}
