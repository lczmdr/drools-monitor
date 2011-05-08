package com.lucazamador.drools.monitoring.model.ksession;

import java.io.Serializable;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeProcessMetric implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long processStarted;
    private Long processCompleted;
    private Long processNodeTriggered;

    public KnowledgeProcessMetric() {
    }

    public KnowledgeProcessMetric(String name, Long processStarted, Long processCompleted, Long processNodeTriggered) {
        this.name = name;
        this.processStarted = processStarted;
        this.processCompleted = processCompleted;
        this.processNodeTriggered = processNodeTriggered;
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

    public Long getProcessStarted() {
        return processStarted;
    }

    public void setProcessStarted(Long processStarted) {
        this.processStarted = processStarted;
    }

    public Long getProcessCompleted() {
        return processCompleted;
    }

    public void setProcessCompleted(Long processCompleted) {
        this.processCompleted = processCompleted;
    }

    public Long getProcessNodeTriggered() {
        return processNodeTriggered;
    }

    public void setProcessNodeTriggered(Long processNodeTriggered) {
        this.processNodeTriggered = processNodeTriggered;
    }

}
