package com.lucazamador.drools.monitoring.core.agent;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;

public class DroolsMonitoringAgentFactory {

    public static MonitoringAgent newDroolsMonitoringAgent(MonitoringAgentConfiguration configuration,
            DroolsMBeanConnector connector, MonitoringRecoveryAgent reconnectionAgent,
            ResourceDiscoveredListener discoveredListener) {
        CommonMonitoringAgent monitoringAgent;
        if (configuration.persistenceEnabled()) {
            monitoringAgent = new DroolsPersistenceMonitoringAgent();
        } else {
            monitoringAgent = new DroolsMonitoringAgent();
        }
        monitoringAgent.setId(configuration.getId());
        monitoringAgent.setScanInterval(configuration.getScanInterval());
        monitoringAgent.setRecoveryInterval(configuration.getRecoveryInterval());
        monitoringAgent.setReconnectionAgent(reconnectionAgent);
        monitoringAgent.setConnector(connector);
        monitoringAgent.setResourceDiscoveredListener(discoveredListener);
        if (configuration.persistenceEnabled()) {
            ((DroolsPersistenceMonitoringAgent) monitoringAgent).setPersistence(null);
            ((DroolsPersistenceMonitoringAgent) monitoringAgent).setPersistenceInterval(configuration.getPersistence()
                    .getInterval());
        }
        return monitoringAgent;
    }

}
