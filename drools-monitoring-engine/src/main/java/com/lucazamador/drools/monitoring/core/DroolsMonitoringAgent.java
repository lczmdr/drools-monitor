package com.lucazamador.drools.monitoring.core;

import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringAgent implements MonitoringAgent {

    private String jvmId;
    private int scanInterval;
    private int recoveryInterval;
    private DroolsMBeanConnector connector;
    private DroolsResourceScanner scanner;
    private ResourceDiscoverer resourceDiscoverer;
    private DroolsMonitoringScannerTask scannerTask;
    private MonitoringRecoveryAgent reconnectionAgent;

    public void start() throws DroolsMonitoringException {
        resourceDiscoverer = new ResourceDiscoverer();
        resourceDiscoverer.setJvmId(jvmId);
        resourceDiscoverer.setConnector(connector);
        resourceDiscoverer.discover();

        scanner = new DroolsResourceScanner();
        scanner.setInterval(scanInterval);

        scannerTask = new DroolsMonitoringScannerTask();
        scannerTask.setResourceDiscoverer(resourceDiscoverer);
        scannerTask.setScanner(scanner);
        scannerTask.setReconnectionAgent(reconnectionAgent);

        scanner.setScannerTask(scannerTask);
        scanner.start();
    }

    public synchronized void stop() {
        if (scanner != null) {
            scanner.stop();
        }
    }

    public DroolsMBeanConnector getConnector() {
        return connector;
    }

    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }

    public String getJvmId() {
        return jvmId;
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public int getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public MonitoringRecoveryAgent getReconnectionAgent() {
        return reconnectionAgent;
    }

    public void setReconnectionAgent(MonitoringRecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
    }

}
