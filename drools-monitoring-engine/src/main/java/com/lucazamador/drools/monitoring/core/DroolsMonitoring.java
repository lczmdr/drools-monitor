package com.lucazamador.drools.monitoring.core;

import com.lucazamador.drools.monitoring.cfg.JVMConfiguration;
import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

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
        for (JVMConfiguration jvmConfiguration : configuration.getConnections()) {
            DroolsMonitoringAgent monitoringAgent = new DroolsMonitoringAgent();
            DroolsMBeanConnector connector = new DroolsMBeanConnector();
            connector.setAddress(jvmConfiguration.getAddress());
            connector.setPort(jvmConfiguration.getPort());
            connector.connect();
            monitoringAgent.setJvmId(jvmConfiguration.getId());
            monitoringAgent.setScanInterval(jvmConfiguration.getScanInterval());
            monitoringAgent.setRecoveryInterval(jvmConfiguration.getRecoveryInterval());
            monitoringAgent.setConnector(connector);
            monitoringAgent.setReconnectionAgent(reconnectionAgent);
            registerMonitoringAgent(monitoringAgent);
        }
    }

    public void start() throws DroolsMonitoringException {
        for (DroolsMonitoringAgent monitoringAgent : whitePages.getMonitorAgents()) {
            monitoringAgent.start();
        }
    }

    public void stop() {
        for (DroolsMonitoringAgent monitoringAgent : whitePages.getMonitorAgents()) {
            monitoringAgent.stop();
        }
    }

    public void add(DroolsMonitoringAgent droolsMonitoringAgent) {
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

}
