package com.lucazamador.drools.monitoring.eclipse.model;

import java.util.Date;
import java.util.List;

public class Graphic {

    private long id;
    private String name;
    private KnowledgeSession parent;
    private List<MonitoringMetric> metrics;

    public Graphic() {
        id = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KnowledgeSession getParent() {
        return parent;
    }

    public void setParent(KnowledgeSession parent) {
        this.parent = parent;
    }

    public List<MonitoringMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<MonitoringMetric> metrics) {
        this.metrics = metrics;
    }

}
