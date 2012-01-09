package com.lucazamador.drools.monitoring.eclipse.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DroolsMonitor {

    private Map<String, MonitoringAgentInfo> monitoringAgents = new HashMap<String, MonitoringAgentInfo>();

    public Collection<MonitoringAgentInfo> getMonitoringAgents() {
        return monitoringAgents.values();
    }

    public void addMonitoringAgent(MonitoringAgentInfo agent) {
        agent.setParent(this);
        this.monitoringAgents.put(agent.getId(), agent);
    }

    public MonitoringAgentInfo getMonitoringAgent(String id) {
        return monitoringAgents.get(id);
    }

    public MonitoringAgentInfo removeMonitorAgent(String id) {
        return this.monitoringAgents.remove(id);
    }

}
