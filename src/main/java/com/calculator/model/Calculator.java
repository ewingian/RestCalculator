package com.calculator.model;

import org.springframework.stereotype.Component;

/**
 * Created by ian on 2/9/18.
 */

@Component
public class Calculator {

    private long id;
    private String content;
    private Integer newValue;
    private Integer total = 0;

    public Calculator(){

    };

    public Calculator(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    // set the new value to be added
    public void setNewValue(Integer a){
        this.newValue = a;
        total = total + a;
    }

    // return the number to be added
    public Integer getValue(){
        return newValue;
    }

    // return the total
    public Integer getTotal(){
        return total;
    }
}
