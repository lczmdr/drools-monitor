package com.lucazamador.drools.monitor.model.listener;

import com.lucazamador.drools.monitor.model.Metric;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class EventListenerValueMetric extends Metric {

    private String key;
    private Long value;

    public String getKey() {
        return key;
    }

    public Long getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Long value) {
        this.value = value;
    }

}
