#!/usr/bin/env sh

docker run -it --rm \
  --network messaging_example\
  bitnami/kafka:3.1.0 \
  kafka-topics.sh --create  --bootstrap-server kafka:29092 --replication-factor 1 --partitions 3 --topic example_topic
