package com.lucazamador.drools.monitor.model;

import java.util.Date;

/**
 * 
 * @author Lucas Amador
 * 
 */
public abstract class AbstractMetric {

    private Long id;
    private Date timestamp;

    public AbstractMetric() {
        timestamp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
