package com.lucazamador.drools.monitoring.eclipse.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucazamador.drools.monitoring.eclipse.console.ActivityConsoleFactory;
import com.lucazamador.drools.monitoring.eclipse.console.ActivityConsoleListener;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;

public class MonitoringAgent {

    private DroolsMonitor parent;
    private String id;
    private String address;
    private int port;
    private int scanInterval;
    private int recoveryInterval;
    private boolean connected;
    private Map<String, KnowledgeBase> knowledgeBases = new HashMap<String, KnowledgeBase>();

    public void build(com.lucazamador.drools.monitoring.core.agent.MonitoringAgent monitoringAgent) {
        this.connected = true;
        List<KnowledgeBaseInfo> kbases = monitoringAgent.getDiscoveredKnowledgeBases();
        for (KnowledgeBaseInfo kbaseInfo : kbases) {
            if (!knowledgeBases.containsKey(kbaseInfo.getKnowledgeBaseId())) {
                KnowledgeBase kbase = new KnowledgeBase();
                kbase.setParent(this);
                kbase.setId(String.valueOf(kbaseInfo.getKnowledgeBaseId()));
                this.knowledgeBases.put(kbase.getId(), kbase);
            }
        }
        List<KnowledgeSessionInfo> ksessions = monitoringAgent.getDiscoveredKnowledgeSessions();
        for (KnowledgeSessionInfo ksessionInfo : ksessions) {
            KnowledgeBase knowledgeBase = knowledgeBases.get(ksessionInfo.getKnowledgeBaseId());
            if (knowledgeBase != null) {
                KnowledgeSession ksession = new KnowledgeSession();
                ksession.setId(String.valueOf(ksessionInfo.getKnowledgeSessionId()));
                ksession.setParent(knowledgeBase);
                knowledgeBase.addKnowledgeSession(ksession);
                String consoleId = ActivityConsoleFactory.getViewId(ksession);
                String id = ksession.getParent().getId() + ":" + ksession.getId();
                ActivityConsoleListener listener = new ActivityConsoleListener(id, consoleId);
                monitoringAgent.registerListener(listener);
            }
        }
    }

    public DroolsMonitor getParent() {
        return parent;
    }

    public void setParent(DroolsMonitor parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public int getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Map<String, KnowledgeBase> getKnowledgeBases() {
        return knowledgeBases;
    }

    public void addKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBases.put(knowledgeBase.getId(), knowledgeBase);
    }

    public void setKnowledgeBases(Map<String, KnowledgeBase> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }

    public void clear() {
        this.knowledgeBases.clear();
    }

}
