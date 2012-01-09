package com.lucazamador.drools.monitoring.core.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;
import com.lucazamador.drools.monitoring.persistence.api.MetricsPersistence;

/**
 * Factory class to create new monitoring agents with the scan interval,
 * recovery interval and custom listener. The monitoring agent type created
 * depends on the existence or not of a persistence mechanism.
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringAgentFactory {

    private static final Logger logger = LoggerFactory.getLogger(DroolsMonitoringAgentFactory.class);

    public static MonitoringAgent newDroolsMonitoringAgent(MonitoringAgentConfiguration configuration,
            DroolsMBeanConnector connector, MonitoringRecoveryAgent reconnectionAgent,
            ResourceDiscoveredListener discoveredListener, MetricsPersistence persistence) {
        DroolsMonitoringAgentBase monitoringAgent;
        if (configuration.persistenceEnabled() && persistence != null) {
            monitoringAgent = new DroolsMonitoringPersistenceAgent();
        } else {
            monitoringAgent = new DroolsMonitoringAgent();
        }
        monitoringAgent.setId(configuration.getId());
        monitoringAgent.setScanInterval(configuration.getScanInterval());
        monitoringAgent.setRecoveryInterval(configuration.getRecoveryInterval());
        monitoringAgent.setReconnectionAgent(reconnectionAgent);
        monitoringAgent.setConnector(connector);
        monitoringAgent.setResourceDiscoveredListener(discoveredListener);
        if (configuration.persistenceEnabled() && persistence != null) {
            ((DroolsMonitoringPersistenceAgent) monitoringAgent).setPersistence(persistence);
            ((DroolsMonitoringPersistenceAgent) monitoringAgent).setPersistenceInterval(configuration
                    .getPersistenceInterval());
        } else {
            logger.error("Non metrics persistence implementation provided. Persistence disabled.");
        }
        return monitoringAgent;
    }

}
