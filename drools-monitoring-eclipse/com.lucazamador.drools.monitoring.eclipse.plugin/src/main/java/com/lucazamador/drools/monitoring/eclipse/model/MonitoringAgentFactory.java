package com.lucazamador.drools.monitoring.eclipse.model;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;

public class MonitoringAgentFactory {

    public static MonitoringAgent newMonitoringAgent(MonitoringAgentConfiguration configuration) {
        MonitoringAgent monitoringAgent = new MonitoringAgent();
        monitoringAgent.setId(configuration.getId());
        monitoringAgent.setAddress(configuration.getAddress());
        monitoringAgent.setPort(configuration.getPort());
        monitoringAgent.setScanInterval(configuration.getScanInterval());
        monitoringAgent.setRecoveryInterval(configuration.getRecoveryInterval());
        return monitoringAgent;
    }

    public static MonitoringAgent newMonitoringAgent(String id, String address, int port, int scanInterval,
            int recoveryInterval) {
        MonitoringAgent monitoringAgent = new MonitoringAgent();
        monitoringAgent.setId(id);
        monitoringAgent.setAddress(address);
        monitoringAgent.setPort(port);
        monitoringAgent.setScanInterval(scanInterval);
        monitoringAgent.setRecoveryInterval(recoveryInterval);
        return monitoringAgent;
    }

    public static MonitoringAgentConfiguration newMonitoringAgentConfiguration(MonitoringAgent agent) {
        MonitoringAgentConfiguration configuration = new MonitoringAgentConfiguration();
        configuration.setId(agent.getId());
        configuration.setAddress(agent.getAddress());
        configuration.setPort(agent.getPort());
        configuration.setScanInterval(agent.getScanInterval());
        configuration.setRecoveryInterval(agent.getRecoveryInterval());
        return configuration;
    }

}
