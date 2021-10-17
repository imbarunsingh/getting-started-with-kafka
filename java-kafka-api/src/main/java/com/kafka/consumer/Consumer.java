package com.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.kafka.config.KafkaConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer {
	public static void main(String[] args) {

		String topic = "first_topic";

		log.info("-----------------------Kafka Consumer : With Consumer Group-------------------");

		// create consumer
		KafkaConsumer<String, String> kafkaConsumer = KafkaConfig.initKafkaConsumer();

		// subscribe consumer to topics
		kafkaConsumer.subscribe(Arrays.asList(topic));

		// poll for data
		while (true) {
			ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
			log.info("Recieved: " + consumerRecords.count() + " records");
			consumerRecords.forEach(record -> {
				System.out.println("Key : " + record.key() 
									+ ", value: " + record.value() 
									+ ", partition : " + record.partition() 
									+ ", offset : " + record.offset());
			});
			log.info("Commiting Offsets....");
			kafkaConsumer.commitSync(); // manual auto commit
			log.info("Offsets have been committed");
		}

		// flush data
		// kafkaConsumer.close();
	}

}
