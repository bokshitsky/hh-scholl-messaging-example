#!/usr/bin/env sh

curl -X POST --data 'messageBody=Hello' 127.0.0.1:8080/rabbit/exchange/boksh_fanout/routing_key/anyKey -v
