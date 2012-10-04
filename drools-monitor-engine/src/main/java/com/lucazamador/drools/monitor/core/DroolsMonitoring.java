package com.lucazamador.drools.monitor.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.core.agent.DroolsMonitoringAgent;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitor.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.listener.MonitoringRecoveryListener;

/**
 * The DroolsMonitoring class is the main class used to configure and create the
 * monitoring agents, assign them to recovery agent, register the custom user
 * listener and so on.
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoring {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsMonitoring.class);

    private MonitoringAgentRegistry registry;
    private MonitoringRecoveryAgent recoveryAgent;
    private List<DroolsMonitoringListener> monitoringListeners = new ArrayList<DroolsMonitoringListener>();
    private List<MonitoringAgent> monitoringAgents = new ArrayList<MonitoringAgent>();

    public void init() {
        LOGGER.info("Starting " + monitoringAgents.size() + " monitoring agents...");
        for (MonitoringAgent monitoringAgent : monitoringAgents) {
            DroolsMBeanConnector connector = null;
            try {
                connector = new DroolsMBeanConnector(monitoringAgent.getAddress(), monitoringAgent.getPort(),
                        monitoringAgent.getRecoveryInterval());
                connector.connect();
            } catch (DroolsMonitoringException e) {
                registry.register(monitoringAgent.getId(), monitoringAgent);
                for (DroolsMonitoringListener listener : monitoringListeners) {
                    monitoringAgent.registerListener(listener);
                }
                monitoringAgent.setMonitoringRecoveryAgent(recoveryAgent);
                recoveryAgent.reconnect(monitoringAgent.getId(), monitoringAgent.getAddress(),
                        monitoringAgent.getPort());
                return;
            }
            registry.register(monitoringAgent.getId(), monitoringAgent);
            for (DroolsMonitoringListener listener : monitoringListeners) {
                monitoringAgent.registerListener(listener);
            }
            monitoringAgent.setMonitoringRecoveryAgent(recoveryAgent);
            monitoringAgent.setConnector(connector);
            monitoringAgent.start();
        }
    }

    /**
     * Stops all the registered monitoring agents.
     */
    public void stop() {
        for (MonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
            monitoringAgent.stop();
        }
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

    public void setMonitoringAgentRegistry(MonitoringAgentRegistry registry) {
        this.registry = registry;
    }

    public void setRecoveryAgent(MonitoringRecoveryAgent recoveryAgent) {
        this.recoveryAgent = recoveryAgent;
    }

    /**
     * Register a custom metric listener in all the monitoring agents.
     * 
     * @param listener
     *            the custom metric listener
     */
    public void registerListener(DroolsMonitoringListener listener) {
        monitoringListeners.add(listener);
        for (MonitoringAgent monitoringAgent : registry.getMonitoringAgents()) {
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

    public List<MonitoringAgent> getMonitoringAgents() {
        return monitoringAgents;
    }

    public void setMonitoringAgents(List<MonitoringAgent> monitoringAgents) {
        this.monitoringAgents = monitoringAgents;
    }

    public List<DroolsMonitoringListener> getMonitoringListeners() {
        return monitoringListeners;
    }

    public void setMonitoringListeners(List<DroolsMonitoringListener> monitoringListeners) {
        this.monitoringListeners = monitoringListeners;
    }

}
