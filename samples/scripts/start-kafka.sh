#!/bin/bash
# kafka 브로커와 주키퍼를 시작하는 스크립트

KAFKA_DIR="/path/to/kafka"  # Kafka 설치 경로
ZOOKEEPER_LOG="$KAFKA_DIR/logs/zookeeper.log"
KAFKA_LOG="$KAFKA_DIR/logs/kafka.log"

echo "Starting Zookeeper..."
$KAFKA_DIR/bin/zookeeper-server-start.sh $KAFKA_DIR/config/zookeeper.properties > $ZOOKEEPER_LOG 2>&1 &
sleep 5

echo "Starting Kafka Broker..."
$KAFKA_DIR/bin/kafka-server-start.sh $KAFKA_DIR/config/server.properties > $KAFKA_LOG 2>&1 &
sleep 5

echo "Kafka and Zookeeper started successfully."