package com.lucazamador.drools.monitoring.core;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitoring.cfg.JVMConfiguration;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoring {

    private MonitoringConfiguration configuration;
    private List<DroolsMonitoringAgent> agents = new ArrayList<DroolsMonitoringAgent>();

    public void configure() throws DroolsMonitoringException {
        for (JVMConfiguration jvmConfiguration : configuration.getConnections()) {
            DroolsMonitoringAgent monitoringAgent = new DroolsMonitoringAgent();
            DroolsMBeanConnector connector = new DroolsMBeanConnector();
            connector.setAddress(jvmConfiguration.getAddress());
            connector.setPort(jvmConfiguration.getPort());
            connector.connect();
            monitoringAgent.setJvmId(jvmConfiguration.getId());
            monitoringAgent.setScanInterval(jvmConfiguration.getScanInterval());
            monitoringAgent.setConnector(connector);
            agents.add(monitoringAgent);
        }
    }

    public void start() throws DroolsMonitoringException {
        for (DroolsMonitoringAgent monitoringAgent : agents) {
            monitoringAgent.start();
        }
    }

    public void add(DroolsMonitoringAgent agent) {
        this.agents.add(agent);
    }

    public void stop() {
        for (DroolsMonitoringAgent monitoringAgent : agents) {
            monitoringAgent.stop();
        }
    }

    public void setConfiguration(MonitoringConfiguration configuration) {
        this.configuration = configuration;
    }

    public MonitoringConfiguration getConfiguration() {
        return configuration;
    }

}
