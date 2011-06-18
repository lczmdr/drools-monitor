package com.lucazamador.drools.monitoring.eclipse.cfg;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.eclipse.Activator;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgentFactory;

public class ConfigurationManager {

    private static final String AGENT_CONFIGURATIONS = "agent-configurations";
    private static final String AGENT_ID = "agent-id";
    private static final String AGENT_ADDRESS = "agent-address";
    private static final String AGENT_PORT = "agent-port";
    private static final String AGENT_SCAN_INTERVAL = "agent-scan-interval";
    private static final String AGENT_RECOVERY_INTERVAL = "agent-recovery-interval";

    public void store(MonitoringAgentConfiguration configuration) throws BackingStoreException {
        IEclipsePreferences preferences = new ConfigurationScope().getNode(Activator.PLUGIN_ID);
        Preferences configurations = preferences.node(AGENT_CONFIGURATIONS);
        Preferences agentPreferences = configurations.node(configuration.getId());
        agentPreferences.put(AGENT_ID, configuration.getId());
        agentPreferences.put(AGENT_ADDRESS, configuration.getAddress());
        agentPreferences.put(AGENT_PORT, String.valueOf(configuration.getPort()));
        agentPreferences.put(AGENT_SCAN_INTERVAL, String.valueOf(configuration.getScanInterval()));
        agentPreferences.put(AGENT_RECOVERY_INTERVAL, String.valueOf(configuration.getRecoveryInterval()));
        configurations.flush();
    }

    public List<MonitoringAgent> read() throws BackingStoreException {
        IEclipsePreferences preferences = new ConfigurationScope().getNode(Activator.PLUGIN_ID);
        Preferences configurations = preferences.node(AGENT_CONFIGURATIONS);
        List<MonitoringAgent> agents = new ArrayList<MonitoringAgent>();
        for (int i = 0; i < configurations.childrenNames().length; i++) {
            String agentName = configurations.childrenNames()[i];
            Preferences agentPreferences = configurations.node(agentName);
            String id = agentPreferences.get(AGENT_ID, "");
            String address = agentPreferences.get(AGENT_ADDRESS, "");
            Integer port = Integer.valueOf(agentPreferences.get(AGENT_PORT, ""));
            Integer scanInterval = Integer.valueOf(agentPreferences.get(AGENT_SCAN_INTERVAL, ""));
            Integer recoveryInterval = Integer.valueOf(agentPreferences.get(AGENT_RECOVERY_INTERVAL, ""));
            MonitoringAgent agent = MonitoringAgentFactory.newMonitoringAgent(id, address, port, scanInterval,
                    recoveryInterval);
            agents.add(agent);
        }
        return agents;
    }

    public void remove(String agentId) throws BackingStoreException {
        IEclipsePreferences preferences = new ConfigurationScope().getNode(Activator.PLUGIN_ID);
        Preferences configurations = preferences.node(AGENT_CONFIGURATIONS);
        Preferences agentPreferencesNode = configurations.node(agentId);
        if (agentPreferencesNode != null) {
            agentPreferencesNode.removeNode();
            configurations.flush();
        }
    }

}
