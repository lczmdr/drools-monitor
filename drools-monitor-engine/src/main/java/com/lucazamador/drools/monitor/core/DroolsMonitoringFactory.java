package com.lucazamador.drools.monitor.core;

import com.lucazamador.drools.monitor.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitor.cfg.MonitoringConfigurationReader;
import com.lucazamador.drools.monitor.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitor.listener.MonitoringRecoveryListener;
import com.lucazamador.drools.monitor.listener.ResourceDiscoveredListener;
import com.lucazamador.drools.monitor.persistence.api.MetricsPersistence;

/**
 * Factory class used to create a DroolsMonitoring object with all the necessary
 * registry and recover agents.
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringFactory {

    /**
     * Creates a default and without configure DroolsMonitoring object.
     * 
     * @return a DroolsMonitoring object
     */
    private static DroolsMonitoring newDroolsMonitoring() {
        DroolsMonitoring droolsMonitoring = new DroolsMonitoring();
        MonitoringAgentRegistry registry = new MonitoringAgentRegistry();
        MonitoringRecoveryAgent recoveryAgent = new MonitoringRecoveryAgent();
        recoveryAgent.setMonitoringAgentRegistry(registry);
        droolsMonitoring.setRecoveryAgent(recoveryAgent);
        droolsMonitoring.setMonitoringAgentRegistry(registry);
        return droolsMonitoring;
    }

    /**
     * Creates a DroolsMonitoring object with a custom recovery listener.
     * 
     * @param recoveryListener
     *            a custom recovery listener
     * @return a DroolsMonitoring object
     */
    public static DroolsMonitoring newDroolsMonitoring(MonitoringRecoveryListener recoveryListener) {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.registerRecoveryAgentListener(recoveryListener);
        return droolsMonitoring;
    }

    /**
     * Creates a DroolsMonitoring object with a custom resource discovery
     * listener.
     * 
     * @param discoveredListener
     *            a custom resource recovery listener
     * @return a DroolsMonitoring object
     */
    public static DroolsMonitoring newDroolsMonitoring(ResourceDiscoveredListener discoveredListener) {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.registerResourceDiscoveredListener(discoveredListener);
        return droolsMonitoring;
    }

    public static DroolsMonitoring newDroolsMonitoring(MonitoringRecoveryListener recoveryListener,
            ResourceDiscoveredListener discoveredListener) {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.registerRecoveryAgentListener(recoveryListener);
        droolsMonitoring.registerResourceDiscoveredListener(discoveredListener);
        return droolsMonitoring;
    }

    public static DroolsMonitoring newDroolsMonitoring(MonitoringConfiguration configuration)
            throws DroolsMonitoringException {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.setConfiguration(configuration);
        droolsMonitoring.configure();
        return droolsMonitoring;
    }

    /**
     * 
     * 
     * @param configuration
     *            the monitoring configuration
     * @param persistence
     *            the metrics persistence implementation
     * @return
     */
    public static DroolsMonitoring newDroolsMonitoring(MonitoringConfiguration configuration,
            MetricsPersistence persistence) {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.registerPersistenceImpl(persistence);
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

    public static DroolsMonitoring newDroolsMonitoring(MonitoringConfiguration configuration,
            MonitoringRecoveryListener recoveryListener, ResourceDiscoveredListener discoveredListener)
            throws DroolsMonitoringException {
        DroolsMonitoring droolsMonitoring = newDroolsMonitoring();
        droolsMonitoring.setConfiguration(configuration);
        droolsMonitoring.registerRecoveryAgentListener(recoveryListener);
        droolsMonitoring.registerResourceDiscoveredListener(discoveredListener);
        droolsMonitoring.configure();
        return droolsMonitoring;
    }

    /**
     * Creates a new monitoring configuration reader configured with a
     * configution file.
     * 
     * @param configurationFile
     *            a configuration file
     * @return a monitoring configuration reader
     */
    public static MonitoringConfigurationReader newMonitoringConfigurationReader(String configurationFile) {
        MonitoringConfigurationReader configurationReader = new MonitoringConfigurationReader();
        configurationReader.setConfigurationFile(configurationFile);
        return configurationReader;
    }

}
