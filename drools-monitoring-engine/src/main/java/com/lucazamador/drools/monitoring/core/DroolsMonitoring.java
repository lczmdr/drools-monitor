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
import com.lucazamador.drools.monitoring.persistence.api.MetricsPersistence;

/**
 * The DroolsMonitoring class is the main class used to configure and create the
 * monitoring agents, assign them to recovery agent, register the custom user
 * listener and so on.
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoring {

    private MonitoringAgentRegistry registry;
    private MetricsPersistence persistence;
    private MonitoringConfiguration configuration;
    private MonitoringRecoveryAgent recoveryAgent;
    private boolean started = false;
    private List<DroolsMonitoringListener> monitoringListeners = new ArrayList<DroolsMonitoringListener>();
    private ResourceDiscoveredListener discoveredListener;

    /**
     * Used to create the monitoring agents configured in the configuration.
     * file
     */
    public void configure() {
        for (MonitoringAgentConfiguration monitoringAgentConfiguration : configuration.getConnections()) {
            createMonitoringAgent(monitoringAgentConfiguration);
        }
    }

    /**
     * Creates a monitoring agent for each drools instance configured to monitor
     * in the configuration file. It also configures the recovery agent for each
     * agent.
     * 
     * @param configuration
     *            the jvm instance connection configuration
     * @throws DroolsMonitoringException
     *             asda
     */
    private void createMonitoringAgent(MonitoringAgentConfiguration configuration) {
        DroolsMBeanConnector connector = null;
        try {
            connector = new DroolsMBeanConnector(configuration.getAddress(), configuration.getPort(),
                    configuration.getRecoveryInterval());
            connector.connect();
        } catch (DroolsMonitoringException e) {
            MonitoringAgent monitoringAgent = DroolsMonitoringAgentFactory.newDroolsMonitoringAgent(configuration,
                    connector, recoveryAgent, discoveredListener, persistence);
            registry.register(monitoringAgent.getId(), monitoringAgent);
            for (DroolsMonitoringListener listener : monitoringListeners) {
                monitoringAgent.registerListener(listener);
            }
            recoveryAgent.reconnect(configuration.getId(), configuration.getAddress(), configuration.getPort());
            return;
        }
        MonitoringAgent monitoringAgent = DroolsMonitoringAgentFactory.newDroolsMonitoringAgent(configuration,
                connector, recoveryAgent, discoveredListener, persistence);
        registry.register(monitoringAgent.getId(), monitoringAgent);
        for (DroolsMonitoringListener listener : monitoringListeners) {
            monitoringAgent.registerListener(listener);
        }
        if (started) {
            monitoringAgent.start();
        }
    }

    /**
     * Initializes all the registered monitoring agents.
     * 
     * @throws DroolsMonitoringException
     */
    public void start() {
        for (MonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
            if (monitoringAgent.isConnected()) {
                monitoringAgent.start();
            }
        }
        started = true;
    }

    /**
     * Stops all the registered monitoring agents.
     */
    public void stop() {
        for (MonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
            monitoringAgent.stop();
        }
        started = false;
    }

    /**
     * Add a manually configured jvm configuration.
     * 
     * @param configuration
     *            the jvm instance connection configuration
     * @throws DroolsMonitoringException
     */
    public void addMonitoringAgent(MonitoringAgentConfiguration configuration) throws DroolsMonitoringException {
        createMonitoringAgent(configuration);
    }

    /**
     * Register a monitoring agent created programatically.
     * 
     * @param droolsMonitoringAgent
     */
    public void addMonitoringAgent(DroolsMonitoringAgent droolsMonitoringAgent) {
        droolsMonitoringAgent.setReconnectionAgent(recoveryAgent);
        registry.register(droolsMonitoringAgent.getId(), droolsMonitoringAgent);
    }

    /**
     * Obtain a registered monitoring agent using their ID.
     * 
     * @param id
     *            the monitoring agent ID
     * @return
     */
    public MonitoringAgent getMonitoringAgent(String id) {
        return registry.getMonitoringAgent(id);
    }

    /**
     * Unregister and stop a monitoring agent using their ID.
     * 
     * @param id
     *            the monitoring agent ID
     */
    public void removeMonitoringAgent(String id) {
        MonitoringAgent unregistered = registry.unregister(id);
        if (unregistered != null) {
            unregistered.stop();
        }
    }

    /**
     * Obtain the monitoring configuration object used to create the monitoring
     * agents.
     * 
     * @return the monitoring configuration object
     */
    public MonitoringConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Set the monitoring configuration object used later to create the
     * monitoring agents.
     * 
     * @param configuration
     *            the monitoring configuration object
     */
    public void setConfiguration(MonitoringConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setMonitoringAgentRegistry(MonitoringAgentRegistry registry) {
        this.registry = registry;
    }

    public void setRecoveryAgent(MonitoringRecoveryAgent recoveryAgent) {
        this.recoveryAgent = recoveryAgent;
    }

    public void registerPersistenceImpl(MetricsPersistence persistence) {
        this.persistence = persistence;
    }

    /**
     * Register a custom metric listener in all the monitoring agents.
     * 
     * @param listener
     *            the custom metric listener
     */
    public void registerListener(DroolsMonitoringListener listener) {
        Collection<MonitoringAgent> monitoringAgents = registry.getMonitoringAgents();
        monitoringListeners.add(listener);
        for (MonitoringAgent monitoringAgent : monitoringAgents) {
            monitoringAgent.registerListener(listener);
        }
    }

    /**
     * Register a custom recovery agent listener in the recovery agent.
     * 
     * @param recoveryListener
     *            the custom recovery listener
     */
    public void registerRecoveryAgentListener(MonitoringRecoveryListener recoveryListener) {
        recoveryAgent.registerListener(recoveryListener);
    }

    /**
     * Register a custom discovery resource listener.
     * 
     * @param discoveredListener
     *            the custom discovery resource listener
     */
    public void registerResourceDiscoveredListener(ResourceDiscoveredListener discoveredListener) {
        this.discoveredListener = discoveredListener;
    }

    /**
     * Returns the status of the drools monitoring instance.
     * 
     * @return true or false
     */
    public boolean isStarted() {
        return started;
    }

}
