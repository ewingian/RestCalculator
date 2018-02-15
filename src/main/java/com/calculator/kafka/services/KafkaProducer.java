package com.calculator.kafka.services;

/**
 * Created by ian on 2/2/18.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Integer> kafkaTemplate;

    @Value("input")
    String kafkaTopic = "addition";

    public void send(Integer i1) {
        System.out.println("sending data = " + i1);
        kafkaTemplate.send(kafkaTopic, i1);
    }

    // a new method from the producer, for sending the total
    @Value("output")
    String kafkaTotal = "total";
    public void sendTotal(Integer i1){
        System.out.println("sending total = " + i1);
        kafkaTemplate.send(kafkaTotal, i1);
    }
}
