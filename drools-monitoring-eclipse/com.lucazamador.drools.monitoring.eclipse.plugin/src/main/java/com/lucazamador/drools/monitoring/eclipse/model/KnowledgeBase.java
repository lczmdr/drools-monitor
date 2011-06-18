package com.lucazamador.drools.monitoring.eclipse.model;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseMetric;

public class KnowledgeBase {

    private String id;
    private MonitoringAgent parent;
    private KnowledgeBaseMetric lastMetric;
    private List<KnowledgeSession> knowledgeSessions = new ArrayList<KnowledgeSession>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MonitoringAgent getParent() {
        return parent;
    }

    public void setParent(MonitoringAgent parent) {
        this.parent = parent;
    }

    public KnowledgeBaseMetric getLastMetric() {
        return lastMetric;
    }

    public void setLastMetric(KnowledgeBaseMetric lastMetric) {
        this.lastMetric = lastMetric;
    }

    public List<KnowledgeSession> getKnowledgeSessions() {
        return knowledgeSessions;
    }

    public void addKnowledgeSession(KnowledgeSession knowledgeSession) {
        this.knowledgeSessions.add(knowledgeSession);
    }

    public void setKnowledgeSessions(List<KnowledgeSession> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

}
