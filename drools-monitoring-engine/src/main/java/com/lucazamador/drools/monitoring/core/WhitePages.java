package com.lucazamador.drools.monitoring.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class WhitePages {

    private Map<String, DroolsMonitoringAgent> agents = new HashMap<String, DroolsMonitoringAgent>();

    public void register(String id, DroolsMonitoringAgent agent) {
        agents.put(id, agent);
    }

    public void unregister(String id) {
        agents.remove(id);
    }

    public int getRecoveryInterval(String id) {
        if (agents.get(id) != null) {
            return agents.get(id).getRecoveryInterval();
        }
        return -1;
    }

    public Collection<DroolsMonitoringAgent> getMonitorAgents() {
        return agents.values();
    }

}
