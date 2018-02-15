package com.calculator.kafka.services;

/**
 * Created by ian on 2/2/18.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    // store values to be produces later
    private Integer input;
    private Integer total = 0;

    @Autowired
    private KafkaProducer producer;

    @KafkaListener(topics="input")
    public void processMessage(Integer i1) {
        System.out.println("received content = " + i1);
        total = total + i1;
        this.producer.sendTotal(total);
    }
}
