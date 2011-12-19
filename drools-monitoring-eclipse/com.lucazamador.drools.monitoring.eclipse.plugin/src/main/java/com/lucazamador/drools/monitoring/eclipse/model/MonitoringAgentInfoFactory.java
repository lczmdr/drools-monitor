package com.lucazamador.drools.monitoring.eclipse.model;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;

public class MonitoringAgentInfoFactory {

    public static MonitoringAgentInfo newMonitoringAgent(MonitoringAgentConfiguration configuration) {
        MonitoringAgentInfo monitoringAgentInfo = new MonitoringAgentInfo();
        monitoringAgentInfo.setId(configuration.getId());
        monitoringAgentInfo.setAddress(configuration.getAddress());
        monitoringAgentInfo.setPort(configuration.getPort());
        monitoringAgentInfo.setScanInterval(configuration.getScanInterval());
        monitoringAgentInfo.setRecoveryInterval(configuration.getRecoveryInterval());
        return monitoringAgentInfo;
    }

    public static MonitoringAgentInfo newMonitoringAgent(String id, String address, int port, int scanInterval,
            int recoveryInterval) {
        MonitoringAgentInfo monitoringAgentInfo = new MonitoringAgentInfo();
        monitoringAgentInfo.setId(id);
        monitoringAgentInfo.setAddress(address);
        monitoringAgentInfo.setPort(port);
        monitoringAgentInfo.setScanInterval(scanInterval);
        monitoringAgentInfo.setRecoveryInterval(recoveryInterval);
        return monitoringAgentInfo;
    }

    public static MonitoringAgentConfiguration newMonitoringAgentConfiguration(MonitoringAgentInfo agent) {
        MonitoringAgentConfiguration configuration = new MonitoringAgentConfiguration();
        configuration.setId(agent.getId());
        configuration.setAddress(agent.getAddress());
        configuration.setPort(agent.getPort());
        configuration.setScanInterval(agent.getScanInterval());
        configuration.setRecoveryInterval(agent.getRecoveryInterval());
        return configuration;
    }

}
