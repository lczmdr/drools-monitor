package com.lucazamador.drools.monitoring.core;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.cfg.Configuration;
import com.lucazamador.drools.monitoring.cfg.JVMConfiguration;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringAgentConfigurer {

    private Configuration configuration;
    private List<DroolsMonitoringAgent> agents = new ArrayList<DroolsMonitoringAgent>();

    public void start() throws DroolsMonitoringException {
        for (JVMConfiguration jvmConfiguration : configuration.getConnections()) {
            DroolsMonitoringAgent monitoringAgent = new DroolsMonitoringAgent();
            DroolsMBeanConnector connector = new DroolsMBeanConnector();
            connector.setAddress(jvmConfiguration.getAddress());
            connector.setPort(jvmConfiguration.getPort());
            connector.connect();
            monitoringAgent.setJvmId(jvmConfiguration.getId());
            monitoringAgent.setScanInterval(jvmConfiguration.getScanInterval());
            monitoringAgent.setConnector(connector);
            monitoringAgent.start();
            agents.add(monitoringAgent);
        }
    }

    public void add(DroolsMonitoringAgent agent) {
        this.agents.add(agent);
    }

    public void stop() {
        for (DroolsMonitoringAgent mbeanAgent : agents) {
            mbeanAgent.stop();
        }
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
