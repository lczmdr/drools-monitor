package com.lucazamador.drools.monitoring.core;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;

public class DroolsMonitoringAgentFactory {

    public static DroolsMonitoringAgent newDroolsMonitoringAgent(MonitoringAgentConfiguration configuration,
            DroolsMBeanConnector connector, MonitoringRecoveryAgent reconnectionAgent,
            ResourceDiscoveredListener discoveredListener) {
        DroolsMonitoringAgent monitoringAgent = new DroolsMonitoringAgent();
        monitoringAgent.setId(configuration.getId());
        monitoringAgent.setScanInterval(configuration.getScanInterval());
        monitoringAgent.setRecoveryInterval(configuration.getRecoveryInterval());
        monitoringAgent.setReconnectionAgent(reconnectionAgent);
        monitoringAgent.setConnector(connector);
        monitoringAgent.setResourceDiscoveredListener(discoveredListener);
        return monitoringAgent;
    }

}
