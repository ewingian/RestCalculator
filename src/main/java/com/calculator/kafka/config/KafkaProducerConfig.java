package com.calculator.kafka.config;

/**
 * Created by ian on 2/2/18.
 */

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.KafkaEmbedded;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {

    @Value("${" + KafkaEmbedded.SPRING_EMBEDDED_KAFKA_BROKERS + ":localhost:9092}")
    private String bootstrapServer;

    @Bean
    public ProducerFactory<String, Integer> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Integer> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
