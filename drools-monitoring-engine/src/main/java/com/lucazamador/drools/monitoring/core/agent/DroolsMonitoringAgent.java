package com.lucazamador.drools.monitoring.core.agent;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringScannerTask;
import com.lucazamador.drools.monitoring.core.DroolsResourceScanner;
import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;

/**
 * 
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringAgent extends CommonMonitoringAgent {

    public void start() {
        resourceDiscoverer = new ResourceDiscoverer();
        resourceDiscoverer.setAgentId(id);
        resourceDiscoverer.setResourceDiscoveredListener(discoveredListener);
        resourceDiscoverer.setConnector(connector);
        resourceDiscoverer.discover();

        scanner = new DroolsResourceScanner();
        scanner.setInterval(scanInterval);

        scannerTask = new DroolsMonitoringScannerTask();
        scannerTask.setResourceDiscoverer(resourceDiscoverer);
        scannerTask.setScanner(scanner);
        scannerTask.setReconnectionAgent(reconnectionAgent);
        for (DroolsMonitoringListener listener : listeners) {
            scannerTask.addListener(listener);
        }

        scanner.setScannerTask(scannerTask);
        scanner.start();
        started = true;
    }

    public synchronized void stop() {
        if (scanner != null) {
            scanner.stop();
        }
        reconnectionAgent.removeRecoveryTask(id);
    }

}
