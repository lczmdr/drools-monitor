package com.lucazamador.drools.monitoring.eclipse.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DroolsMonitor {

    private Map<String, MonitoringAgent> monitoringAgents = new HashMap<String, MonitoringAgent>();

    public Collection<MonitoringAgent> getMonitoringAgents() {
        return monitoringAgents.values();
    }

    public void addMonitoringAgent(MonitoringAgent agent) {
        agent.setParent(this);
        this.monitoringAgents.put(agent.getId(), agent);
    }

    public MonitoringAgent getMonitoringAgent(String id) {
        return monitoringAgents.get(id);
    }

    public MonitoringAgent removeMonitorAgent(String id) {
        return this.monitoringAgents.remove(id);
    }

}
