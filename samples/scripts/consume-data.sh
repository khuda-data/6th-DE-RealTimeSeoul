#!/bin/bash
# Kafka 토픽의 데이터를 consume하는 스크립트

BROKER="localhost:9092"
TOPIC="all.region"

echo "Consuming data from Kafka topic: $TOPIC"
kafka-console-consumer.sh --bootstrap-server $BROKER --topic $TOPIC --from-beginning