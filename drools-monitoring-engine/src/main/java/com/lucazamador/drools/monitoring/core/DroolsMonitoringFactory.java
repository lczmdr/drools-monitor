package com.lucazamador.drools.monitoring.core;

import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitoring.cfg.MonitoringConfigurationReader;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;

public class DroolsMonitoringFactory {

    public static DroolsMonitoring newDroolsMonitoring() {
        DroolsMonitoring droolsMonitoring = new DroolsMonitoring();
        MonitoringAgentRegistry registry = new MonitoringAgentRegistry();
        MonitoringRecoveryAgent recoveryAgent = new MonitoringRecoveryAgent();
        recoveryAgent.setMonitoringAgentRegistry(registry);
        droolsMonitoring.setRecoveryAgent(recoveryAgent);
        droolsMonitoring.setMonitoringAgentRegistry(registry);
        return droolsMonitoring;
    }

    public static DroolsMonitoring newDroolsMonitoring(MonitoringRecoveryListener recoveryListener) {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.registerRecoveryAgentListener(recoveryListener);
        return droolsMonitoring;
    }

    public static DroolsMonitoring newDroolsMonitoring(MonitoringConfiguration configuration)
            throws DroolsMonitoringException {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.setConfiguration(configuration);
        droolsMonitoring.configure();
        return droolsMonitoring;
    }

    public static DroolsMonitoring newDroolsMonitoring(MonitoringConfiguration configuration,
            MonitoringRecoveryListener recoveryListener) throws DroolsMonitoringException {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.setConfiguration(configuration);
        droolsMonitoring.registerRecoveryAgentListener(recoveryListener);
        droolsMonitoring.configure();
        return droolsMonitoring;
    }

    public static MonitoringConfigurationReader newMonitoringConfigurationReader(String configurationFile) {
        MonitoringConfigurationReader configurationReader = new MonitoringConfigurationReader();
        configurationReader.setConfigurationFile(configurationFile);
        return configurationReader;
    }

}
