package ru.hh.boksh.messaging.kafka;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListener.class);
  private static final Duration POOL_TIMEOUT = Duration.ofSeconds(3);
  private final KafkaConsumerFactory kafkaConsumerFactory;
  private final Executor executor = Executors.newCachedThreadPool();

  public KafkaListener(KafkaConsumerFactory kafkaConsumerFactory) {
    this.kafkaConsumerFactory = kafkaConsumerFactory;
    listenToTopic("example_topic", "example_app__group1", true);
    listenToTopic("example_topic", "example_app__group2", true);
    listenToTopic("example_topic", "example_app__group3", true);
  }

  private void listenToTopic(String topicName, String consumeGroup, boolean commitOffsetToKafka) {
    executor.execute(() -> {
      Consumer<String, String> kafkaConsumer = kafkaConsumerFactory.createKafkaConsumer(consumeGroup);
      kafkaConsumer.subscribe(List.of(topicName));
      while (!Thread.currentThread().isInterrupted()) {
        ConsumerRecords<String, String> consumedRecords = kafkaConsumer.poll(POOL_TIMEOUT);
        if (consumedRecords.isEmpty()) {
          continue;
        }
        consumedRecords.forEach(record -> {
          LOGGER.info("got record for consumer group {}: {}", consumeGroup, record);
        });
        if (commitOffsetToKafka) {
          kafkaConsumer.commitSync();
        }
      }
    });

  }
}
