package ru.hh.boksh.messaging.kafka;

import java.util.concurrent.CompletableFuture;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaPublisher {

  private final Producer<String, String> kafkaProducer;

  public KafkaPublisher(Producer<String, String> kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  public CompletableFuture<RecordMetadata> send(String topic, String key, String data) {
    CompletableFuture<RecordMetadata> future = new CompletableFuture<>();

    try {
      kafkaProducer.send(new ProducerRecord<>(topic, key, data), (metadata, exception) -> {
        if (exception != null) {
          future.completeExceptionally(exception);
        }
        future.complete(metadata);
      });
    } catch (RuntimeException ex) {
      future.completeExceptionally(ex);
    }

    return future;
  }

}
