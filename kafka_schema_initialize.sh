#!/usr/bin/env sh

docker run -it --rm \
  --network messaging-example_default \
  -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181 \
  bitnami/kafka:2 \
  kafka-topics.sh --create  --zookeeper zookeeper:2181 --replication-factor 1 --partitions 3 --topic example_topic