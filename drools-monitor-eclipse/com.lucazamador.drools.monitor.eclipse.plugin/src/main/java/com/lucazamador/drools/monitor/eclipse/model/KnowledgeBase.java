package com.lucazamador.drools.monitor.eclipse.model;

import java.util.HashMap;
import java.util.Map;

import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseMetric;

public class KnowledgeBase {

    private String id;
    private MonitoringAgentInfo parent;
    private KnowledgeBaseMetric lastMetric;
    private Map<String, KnowledgeSession> knowledgeSessions = new HashMap<String, KnowledgeSession>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MonitoringAgentInfo getParent() {
        return parent;
    }

    public void setParent(MonitoringAgentInfo parent) {
        this.parent = parent;
    }

    public KnowledgeBaseMetric getLastMetric() {
        return lastMetric;
    }

    public void setLastMetric(KnowledgeBaseMetric lastMetric) {
        this.lastMetric = lastMetric;
    }

    public Map<String, KnowledgeSession> getKnowledgeSessions() {
        return knowledgeSessions;
    }

    public void addKnowledgeSession(KnowledgeSession knowledgeSession) {
        this.knowledgeSessions.put(knowledgeSession.getId(), knowledgeSession);
    }

    public void setKnowledgeSessions(Map<String, KnowledgeSession> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

}
