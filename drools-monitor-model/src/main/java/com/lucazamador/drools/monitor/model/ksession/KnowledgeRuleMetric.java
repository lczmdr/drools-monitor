package com.lucazamador.drools.monitor.model.ksession;

import java.io.Serializable;

import com.lucazamador.drools.monitor.model.builder.KnowledgeSessionMetricBuilder;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeRuleMetric implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Double firingTime;
    private Long activationsCancelled;
    private Long activationsCreated;
    private Long activationsFired;

    public KnowledgeRuleMetric() {
    }

    public KnowledgeRuleMetric(String name, Double firingTime, long activationsCancelled, long activationsCreated,
            long activationsFired) {
        this.name = name;
        this.firingTime = firingTime;
        this.activationsCancelled = activationsCancelled;
        this.activationsCreated = activationsCreated;
        this.activationsFired = activationsFired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFiringTime() {
        return firingTime;
    }

    public void setFiringTime(Double firingTime) {
        this.firingTime = firingTime;
    }

    public Long getActivationsCancelled() {
        return activationsCancelled;
    }

    public void setActivationsCancelled(Long activationsCancelled) {
        this.activationsCancelled = activationsCancelled;
    }

    public Long getActivationsCreated() {
        return activationsCreated;
    }

    public void setActivationsCreated(Long activationsCreated) {
        this.activationsCreated = activationsCreated;
    }

    public Long getActivationsFired() {
        return activationsFired;
    }

    public void setActivationsFired(Long activationsFired) {
        this.activationsFired = activationsFired;
    }

    public KnowledgeRuleMetric(KnowledgeSessionMetricBuilder builder) {
        firingTime = builder.getAverageFiringTime();
        activationsCancelled = builder.getTotalActivationsCancelled();
        activationsCreated = builder.getTotalActivationsCreated();
        activationsFired = builder.getTotalActivationsFired();
    }

    @Override
    public String toString() {
        return "name=" + name + " activationsCreated=" + activationsCreated + " activationsCancelled="
                + activationsCancelled + " activationsFired=" + activationsFired + " firingTime=" + firingTime + "ms";
    }
}
