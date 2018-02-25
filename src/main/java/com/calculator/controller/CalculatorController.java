package com.calculator.controller;

/**
 * Created by ian on 2/9/18.
 */

import com.calculator.kafka.services.KafkaProducer;
import com.calculator.model.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;


@RequestMapping(value= "calculator/", method = {RequestMethod.GET, RequestMethod.PUT})
@RestController
public class CalculatorController {

    // from a tutorial, good rest practice
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private Calculator calc1;
    @Autowired
    private KafkaProducer producer;

    // get practice
//    @RequestMapping("greeting")
//    public Calculator calculator(@RequestParam(value="name", defaultValue="World") String name) {
//        return new Calculator(counter.incrementAndGet(), String.format(template, name));
//    }

    // put practice
    @RequestMapping(value="add", method = RequestMethod.PUT)
    @ResponseBody
    public int calculator(@RequestParam(value="val") int val, Calculator calc1, KafkaProducer producer){
        this.calc1.setNewValue(val);
        this.producer.send(this.calc1.getValue());
        return this.calc1.getValue();
    }

    @RequestMapping(value="total", method = RequestMethod.GET)
    public Integer calculator(){
        return this.calc1.getTotal();
    }
}
