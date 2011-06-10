package com.lucazamador.drools.monitoring.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class MonitoringAgentRegistry {

    private Map<String, DroolsMonitoringAgent> agents = new HashMap<String, DroolsMonitoringAgent>();

    public void register(String id, DroolsMonitoringAgent agent) {
        agents.put(id, agent);
    }

    public DroolsMonitoringAgent unregister(String id) {
        return agents.remove(id);
    }

    public DroolsMonitoringAgent getMonitoringAgent(String id) {
        if (agents.get(id) != null) {
            return agents.get(id);
        }
        return null;
    }

    public Collection<DroolsMonitoringAgent> getMonitoringAgents() {
        return agents.values();
    }

}
