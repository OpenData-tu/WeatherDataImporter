#!/bin/sh

KAFKA_TOPIC="${KAFKA_TOPIC:-weatherData}"

java -Dresource.url=${RESOURCE_URL} -Dkafka.bootstrap-servers=${KAFKA_BOOTSTRAP_SERVERS} -Dkafka.topic=${KAFKA_TOPIC} -jar app.jar
