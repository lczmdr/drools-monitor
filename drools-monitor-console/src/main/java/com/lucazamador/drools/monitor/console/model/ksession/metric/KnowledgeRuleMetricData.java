package com.lucazamador.drools.monitor.console.model.ksession.metric;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lucazamador.drools.monitor.model.ksession.KnowledgeRuleMetric;

@XmlRootElement(name = "ruleMetric")
public class KnowledgeRuleMetricData {

    private Long id;
    private String name;
    private Double firingTime;
    private Long activationsCancelled;
    private Long activationsCreated;
    private Long activationsFired;

    public KnowledgeRuleMetricData() {
    }

    public KnowledgeRuleMetricData(KnowledgeRuleMetric ruleMetric) {
        this.id = ruleMetric.getId();
        this.name = ruleMetric.getName();
        this.firingTime = ruleMetric.getFiringTime();
        this.activationsCancelled = ruleMetric.getActivationsCancelled();
        this.activationsCreated = ruleMetric.getActivationsCreated();
        this.activationsFired = ruleMetric.getActivationsFired();
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Double getFiringTime() {
        return firingTime;
    }

    public void setFiringTime(Double firingTime) {
        this.firingTime = firingTime;
    }

    @XmlElement
    public Long getActivationsCancelled() {
        return activationsCancelled;
    }

    public void setActivationsCancelled(Long activationsCancelled) {
        this.activationsCancelled = activationsCancelled;
    }

    @XmlElement
    public Long getActivationsCreated() {
        return activationsCreated;
    }

    public void setActivationsCreated(Long activationsCreated) {
        this.activationsCreated = activationsCreated;
    }

    @XmlElement
    public Long getActivationsFired() {
        return activationsFired;
    }

    public void setActivationsFired(Long activationsFired) {
        this.activationsFired = activationsFired;
    }

}
