package com.lucazamador.drools.monitoring.core;

import java.util.Collection;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoring {

    private MonitoringAgentRegistry registry;
    private MonitoringConfiguration configuration;
    private MonitoringRecoveryAgent reconnectionAgent;
    private boolean started = false;

    public void configure() throws DroolsMonitoringException {
        for (MonitoringAgentConfiguration monitoringAgentConfiguration : configuration.getConnections()) {
            createMonitoringAgent(monitoringAgentConfiguration);
        }
    }

    private void createMonitoringAgent(MonitoringAgentConfiguration configuration) throws DroolsMonitoringException {
        DroolsMBeanConnector connector = null;
        try {
            connector = new DroolsMBeanConnector(configuration.getAddress(), configuration.getPort());
            connector.connect();
        } catch (DroolsMonitoringException e) {
            DroolsMonitoringAgent monitoringAgent = DroolsMonitoringAgentFactory.newDroolsMonitoringAgent(
                    configuration, connector, reconnectionAgent);
            registry.register(monitoringAgent.getId(), monitoringAgent);
            reconnectionAgent.reconnect(configuration.getId(), configuration.getAddress(), configuration.getPort());
            return;
        }
        DroolsMonitoringAgent monitoringAgent = DroolsMonitoringAgentFactory.newDroolsMonitoringAgent(configuration,
                connector, reconnectionAgent);
        registry.register(monitoringAgent.getId(), monitoringAgent);
        if (started) {
            monitoringAgent.start();
        }
    }

    public void start() throws DroolsMonitoringException {
        for (DroolsMonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
            if (monitoringAgent.isConnected()) {
                monitoringAgent.start();
            }
        }
        started = true;
    }

    public void stop() {
        for (DroolsMonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
            monitoringAgent.stop();
        }
        started = false;
    }

    public void addMonitoringAgent(MonitoringAgentConfiguration configuration) throws DroolsMonitoringException {
        createMonitoringAgent(configuration);
    }

    public void addMonitoringAgent(DroolsMonitoringAgent droolsMonitoringAgent) {
        droolsMonitoringAgent.setReconnectionAgent(reconnectionAgent);
        registry.register(droolsMonitoringAgent.getId(), droolsMonitoringAgent);
    }

    public DroolsMonitoringAgent getMonitoringAgent(String id) {
        return registry.getMonitoringAgent(id);
    }

    public void removeMonitoringAgent(String id) {
        DroolsMonitoringAgent unregistered = registry.unregister(id);
        if (unregistered != null) {
            unregistered.stop();
        }
    }

    public MonitoringConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MonitoringConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setMonitoringAgentRegistry(MonitoringAgentRegistry registry) {
        this.registry = registry;
    }

    public void setReconnectionAgent(MonitoringRecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
    }

    public void registerListener(DroolsMonitoringListener listener) {
        Collection<DroolsMonitoringAgent> agents = registry.getMonitoringAgents();
        for (DroolsMonitoringAgent agent : agents) {
            agent.registerListener(listener);
        }
    }

    public boolean isStarted() {
        return started;
    }

}
