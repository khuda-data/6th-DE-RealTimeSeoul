#!/bin/bash
# Kafka 토픽으로 데이터를 produce하는 스크립트

BROKER="localhost:9092"
TOPIC="all.region"
DATA_FILE="./data/sample-region.json"

echo "Producing data to Kafka topic: $TOPIC"
if [ -f "$DATA_FILE" ]; then
  cat $DATA_FILE | kafka-console-producer.sh --broker-list $BROKER --topic $TOPIC
  echo "Data produced successfully."
else
  echo "Error: Data file not found: $DATA_FILE"
fi