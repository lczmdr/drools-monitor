package com.lucazamador.drools.monitoring.model.listener;

import java.util.List;

import com.lucazamador.drools.monitoring.model.AbstractMetric;

/**
 * 
 * @author Lucas Amador
 *
 */
public class EventListenerMetric extends AbstractMetric {

    private String jvmName;
    private String eventListenerId;
    private List<EventListenerValueMetric> values;

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

    public String getEventListenerId() {
        return eventListenerId;
    }

    public void setEventListenerId(String eventListenerId) {
        this.eventListenerId = eventListenerId;
    }

    public List<EventListenerValueMetric> getValues() {
        return values;
    }

    public void setValues(List<EventListenerValueMetric> values) {
        this.values = values;
    }

}
