#!/usr/bin/env sh

docker exec messaging-example-kafka-1 \
  /opt/bitnami/kafka/bin/kafka-topics.sh --create --topic example_topic2 --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
