#!/usr/bin/env sh

curl -X POST --data 'messageBody=Hello&blocking=false' 127.0.0.1:8080/rabbit/exchange/boksh_fanout/routing_key/anyKey -v

curl -X POST --data 'blocking=false' 127.0.0.1:8080/rabbit/exchange/boksh_fanout/routing_key/anyKey -v
