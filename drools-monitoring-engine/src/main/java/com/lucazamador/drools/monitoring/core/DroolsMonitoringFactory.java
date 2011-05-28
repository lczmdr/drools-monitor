package com.lucazamador.drools.monitoring.core;

import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitoring.cfg.MonitoringConfigurationReader;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

public class DroolsMonitoringFactory {

    public static DroolsMonitoring newDroolsMonitoring() {
        DroolsMonitoring droolsMonitoring = new DroolsMonitoring();
        WhitePages whitePages = new WhitePages();
        RecoveryAgent reconnectionAgent = new RecoveryAgent();
        reconnectionAgent.setWhitePages(whitePages);
        droolsMonitoring.setReconnectionAgent(reconnectionAgent);
        droolsMonitoring.setWhitePages(whitePages);
        return droolsMonitoring;
    }

    public static DroolsMonitoring newDroolsMonitoring(MonitoringConfiguration configuration)
            throws DroolsMonitoringException {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.setConfiguration(configuration);
        droolsMonitoring.configure();
        return droolsMonitoring;
    }

    public static MonitoringConfigurationReader newMonitoringConfigurationReader(String configurationFile) {
        MonitoringConfigurationReader configurationReader = new MonitoringConfigurationReader();
        configurationReader.setConfigurationFile(configurationFile);
        return configurationReader;
    }

}
