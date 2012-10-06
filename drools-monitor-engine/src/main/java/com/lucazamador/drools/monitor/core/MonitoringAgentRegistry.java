package com.lucazamador.drools.monitor.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;

/**
 * A simple monitoring agent registry.
 * 
 * @author Lucas Amador
 * 
 */
public class MonitoringAgentRegistry {

    private Map<String, MonitoringAgent> agents = new HashMap<String, MonitoringAgent>();

    /**
     * Register a monitoring agent into the internal registry.
     * 
     * @param id
     *            the monitoring agent id
     * @param agent
     *            the monitoring agent to be registered
     */
    public void register(String id, MonitoringAgent agent) {
        agents.put(id, agent);
    }

    /**
     * Unregister a monitoring agent using the ID.
     * 
     * @param id
     *            the ID of the monitoring agent to be unregistered
     * @return the unregistered monitoring agent. It returns null when a
     *         monitoring agent isn't registered with the ID parameter value.
     */
    public MonitoringAgent unregister(String id) {
        return agents.remove(id);
    }

    public MonitoringAgent getMonitoringAgent(String id) {
        return agents.get(id);
    }

    public Collection<MonitoringAgent> getMonitoringAgents() {
        return agents.values();
    }

}
