package com.lucazamador.drools.monitoring.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lucazamador.drools.monitoring.core.agent.MonitoringAgent;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class MonitoringAgentRegistry {

    private Map<String, MonitoringAgent> agents = new HashMap<String, MonitoringAgent>();

    public void register(String id, MonitoringAgent agent) {
        agents.put(id, agent);
    }

    public MonitoringAgent unregister(String id) {
        return agents.remove(id);
    }

    public MonitoringAgent getMonitoringAgent(String id) {
        if (agents.get(id) != null) {
            return agents.get(id);
        }
        return null;
    }

    public Collection<MonitoringAgent> getMonitoringAgents() {
        return agents.values();
    }

}
