package ru.hh.boksh.messaging.kafka;

import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerFactory {

  private final String servers;

  public KafkaConsumerFactory(String servers) {
    this.servers = servers;
  }

  public Consumer<String, String> createKafkaConsumer(String consumerGroup) {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "example_app");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer());
  }

}
