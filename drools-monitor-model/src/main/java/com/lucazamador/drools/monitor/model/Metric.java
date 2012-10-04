package com.lucazamador.drools.monitor.model;

import java.util.Date;

/**
 * 
 * @author Lucas Amador
 * 
 */
public abstract class Metric {

    private Long id;
    private Date timestamp;

    public Metric() {
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
