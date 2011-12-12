package com.lucazamador.drools.monitoring.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitoring.core.agent.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.core.agent.DroolsMonitoringAgentFactory;
import com.lucazamador.drools.monitoring.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;
import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoring {

    private MonitoringAgentRegistry registry;
    private MonitoringConfiguration configuration;
    private MonitoringRecoveryAgent recoveryAgent;
    private boolean started = false;
    private List<DroolsMonitoringListener> monitoringListeners = new ArrayList<DroolsMonitoringListener>();
    private ResourceDiscoveredListener discoveredListener;

    public void configure() {
        for (MonitoringAgentConfiguration monitoringAgentConfiguration : configuration.getConnections()) {
            createMonitoringAgent(monitoringAgentConfiguration);
        }
    }

    private void createMonitoringAgent(MonitoringAgentConfiguration configuration) {
        DroolsMBeanConnector connector = null;
        try {
            connector = new DroolsMBeanConnector(configuration.getAddress(), configuration.getPort(),
                    configuration.getRecoveryInterval());
            connector.connect();
        } catch (DroolsMonitoringException e) {
            MonitoringAgent monitoringAgent = DroolsMonitoringAgentFactory.newDroolsMonitoringAgent(configuration,
                    connector, recoveryAgent, discoveredListener);
            registry.register(monitoringAgent.getId(), monitoringAgent);
            for (DroolsMonitoringListener listener : monitoringListeners) {
                monitoringAgent.registerListener(listener);
            }
            recoveryAgent.reconnect(configuration.getId(), configuration.getAddress(), configuration.getPort());
            return;
        }
        MonitoringAgent monitoringAgent = DroolsMonitoringAgentFactory.newDroolsMonitoringAgent(configuration,
                connector, recoveryAgent, discoveredListener);
        registry.register(monitoringAgent.getId(), monitoringAgent);
        for (DroolsMonitoringListener listener : monitoringListeners) {
            monitoringAgent.registerListener(listener);
        }
        if (started) {
            monitoringAgent.start();
        }
    }

    public void start() {
        for (MonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
            if (monitoringAgent.isConnected()) {
                monitoringAgent.start();
            }
        }
        started = true;
    }

    public void stop() {
        for (MonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
            monitoringAgent.stop();
        }
        started = false;
    }

    public void addMonitoringAgent(MonitoringAgentConfiguration configuration) throws DroolsMonitoringException {
        createMonitoringAgent(configuration);
    }

    public void addMonitoringAgent(DroolsMonitoringAgent droolsMonitoringAgent) {
        droolsMonitoringAgent.setReconnectionAgent(recoveryAgent);
        registry.register(droolsMonitoringAgent.getId(), droolsMonitoringAgent);
    }

    public MonitoringAgent getMonitoringAgent(String id) {
        return registry.getMonitoringAgent(id);
    }

    public void removeMonitoringAgent(String id) {
        MonitoringAgent unregistered = registry.unregister(id);
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

    public void setRecoveryAgent(MonitoringRecoveryAgent recoveryAgent) {
        this.recoveryAgent = recoveryAgent;
    }

    public void registerListener(DroolsMonitoringListener listener) {
        Collection<MonitoringAgent> monitoringAgents = registry.getMonitoringAgents();
        monitoringListeners.add(listener);
        for (MonitoringAgent monitoringAgent : monitoringAgents) {
            monitoringAgent.registerListener(listener);
        }
    }

    public void registerRecoveryAgentListener(MonitoringRecoveryListener recoveryListener) {
        recoveryAgent.registerListener(recoveryListener);
    }

    public void registerResourceDiscoveredListener(ResourceDiscoveredListener discoveredListener) {
        this.discoveredListener = discoveredListener;
    }

    public boolean isStarted() {
        return started;
    }

}
