package com.lucazamador.drools.monitoring.cfg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias(value = "persistence")
public class PersistenceConfiguration {

    @XStreamAsAttribute
    private int interval = 10000;
    @XStreamAsAttribute
    private String className;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
