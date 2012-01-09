package com.lucazamador.drools.monitor.core.agent;

import com.lucazamador.drools.monitor.core.DroolsMonitoringScannerTask;
import com.lucazamador.drools.monitor.core.DroolsResourceScanner;
import com.lucazamador.drools.monitor.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;

/**
 * 
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringAgent extends DroolsMonitoringAgentBase {

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
        scannerTask.setOnConnectionLost(new ConnectionLost() {
            @Override
            public void stop() {
                resourceDiscoverer.stop();
            }
        });
        for (DroolsMonitoringListener listener : listeners) {
            scannerTask.registerListener(listener);
        }

        scanner.setScannerTask(scannerTask);
        scanner.start();
        started = true;
    }

}
