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

    private DroolsMBeanConnector connector;
    private DroolsResourceScanner scanner;
    private ResourceDiscoverer resourceDiscoverer;
    private DroolsMonitoringScannerTask scannerTask;
    private String jvmId;
    private int scanInterval;

    public void start() throws DroolsMonitoringException {
        resourceDiscoverer = new ResourceDiscoverer();
        resourceDiscoverer.setJvmId(getJvmId());
        resourceDiscoverer.setConnector(connector);
        resourceDiscoverer.discover();

        scanner = new DroolsResourceScanner();
        scanner.setInterval(scanInterval);

        scannerTask = new DroolsMonitoringScannerTask();
        scannerTask.setResourceDiscoverer(resourceDiscoverer);
        scannerTask.setScanner(scanner);

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

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

    public String getJvmId() {
        return jvmId;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

}
