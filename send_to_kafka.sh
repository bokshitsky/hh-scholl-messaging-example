#!/usr/bin/env sh

curl -X POST --data 'messageBody=Hello&key=exampleKey' 127.0.0.1:8080/kafka/topic/example_topic -v