package ru.hh.boksh.messaging.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    KafkaPublisher.class,
    KafkaListener.class,
})
public class KafkaConfig {

  @Value("${kafka.bootstrap.servers}")
  private String servers;

  @Bean
  public Producer<String, String> kafkaProducer() {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "example_app");
    return new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
  }

  @Bean
  public KafkaConsumerFactory kafkaConsumerFactory() {
    return new KafkaConsumerFactory(servers);
  }

}
