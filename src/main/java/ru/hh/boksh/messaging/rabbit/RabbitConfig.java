package ru.hh.boksh.messaging.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    RabbitListener.class,
    RabbitPublisher.class
})
public class RabbitConfig {

  @Value("${rabbitmq.host}")
  private String host;

  @Value("${rabbitmq.port}")
  private Integer port;

  @Value("${rabbitmq.user}")
  private String user;

  @Value("${rabbitmq.passoword}")
  private String password;

  @Bean
  ConnectionFactory connectionFactory() throws IOException, TimeoutException {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setUsername(user);
    connectionFactory.setPassword(password);
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setConnectionTimeout(3000);
    connectionFactory.setHandshakeTimeout(3000);
    return connectionFactory;
  }

}
