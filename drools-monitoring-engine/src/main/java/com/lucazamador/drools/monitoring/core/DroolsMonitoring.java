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

    private WhitePages whitePages;
    private MonitoringConfiguration configuration;
    private MonitoringRecoveryAgent reconnectionAgent;

    public void configure() throws DroolsMonitoringException {
        for (MonitoringAgentConfiguration monitoringAgentConfiguration : configuration.getConnections()) {
            createMonitoringAgent(monitoringAgentConfiguration);
        }
    }

    private void createMonitoringAgent(MonitoringAgentConfiguration configuration) throws DroolsMonitoringException {
        DroolsMonitoringAgent monitoringAgent = new DroolsMonitoringAgent();
        DroolsMBeanConnector connector = new DroolsMBeanConnector();
        connector.setAddress(configuration.getAddress());
        connector.setPort(configuration.getPort());
        connector.connect();
        monitoringAgent.setJvmId(configuration.getId());
        monitoringAgent.setScanInterval(configuration.getScanInterval());
        monitoringAgent.setRecoveryInterval(configuration.getRecoveryInterval());
        monitoringAgent.setConnector(connector);
        monitoringAgent.setReconnectionAgent(reconnectionAgent);
        registerMonitoringAgent(monitoringAgent);
    }

    public void start() throws DroolsMonitoringException {
        for (DroolsMonitoringAgent monitoringAgent : whitePages.getMonitoringAgents()) {
            monitoringAgent.start();
        }
    }

    public void stop() {
        for (DroolsMonitoringAgent monitoringAgent : whitePages.getMonitoringAgents()) {
            monitoringAgent.stop();
        }
    }

    public void addMonitoringAgent(MonitoringAgentConfiguration configuration) throws DroolsMonitoringException {
        createMonitoringAgent(configuration);
    }

    public void addMonitoringAgent(DroolsMonitoringAgent droolsMonitoringAgent) {
        droolsMonitoringAgent.setReconnectionAgent(reconnectionAgent);
        registerMonitoringAgent(droolsMonitoringAgent);
    }

    public void remove(DroolsMonitoringAgent droolsMonitoringAgent) {
        unregisterMonitoringAgent(droolsMonitoringAgent);
    }

    private void registerMonitoringAgent(DroolsMonitoringAgent agent) {
        whitePages.register(agent.getJvmId(), agent);
    }

    private void unregisterMonitoringAgent(DroolsMonitoringAgent agent) {
        whitePages.unregister(agent.getJvmId());
    }

    public MonitoringConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MonitoringConfiguration configuration) {
        this.configuration = configuration;
    }

    public WhitePages getWhitePages() {
        return whitePages;
    }

    public void setWhitePages(WhitePages whitePages) {
        this.whitePages = whitePages;
    }

    public MonitoringRecoveryAgent getReconnectionAgent() {
        return reconnectionAgent;
    }

    public void setReconnectionAgent(MonitoringRecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
    }

    public void registerListener(DroolsMonitoringListener listener) {
        Collection<DroolsMonitoringAgent> agents = whitePages.getMonitoringAgents();
        for (DroolsMonitoringAgent agent : agents) {
            agent.registerListener(listener);
        }
    }

}
