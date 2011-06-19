package com.lucazamador.drools.monitoring.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
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
                    configuration, connector, recoveryAgent, discoveredListener);
            registry.register(monitoringAgent.getId(), monitoringAgent);
            for (DroolsMonitoringListener listener : monitoringListeners) {
                monitoringAgent.registerListener(listener);
            }
            recoveryAgent.reconnect(configuration.getId(), configuration.getAddress(), configuration.getPort());
            return;
        }
        DroolsMonitoringAgent monitoringAgent = DroolsMonitoringAgentFactory.newDroolsMonitoringAgent(configuration,
                connector, recoveryAgent, discoveredListener);
        registry.register(monitoringAgent.getId(), monitoringAgent);
        for (DroolsMonitoringListener listener : monitoringListeners) {
            monitoringAgent.registerListener(listener);
        }
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
        droolsMonitoringAgent.setReconnectionAgent(recoveryAgent);
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

    public void setRecoveryAgent(MonitoringRecoveryAgent recoveryAgent) {
        this.recoveryAgent = recoveryAgent;
    }

    public void registerListener(DroolsMonitoringListener listener) {
        Collection<DroolsMonitoringAgent> agents = registry.getMonitoringAgents();
        monitoringListeners.add(listener);
        for (DroolsMonitoringAgent agent : agents) {
            agent.registerListener(listener);
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
