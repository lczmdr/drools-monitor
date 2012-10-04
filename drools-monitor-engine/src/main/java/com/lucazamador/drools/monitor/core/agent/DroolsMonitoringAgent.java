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
        ResourceDiscoverer resourceDiscoverer = new ResourceDiscoverer();
        resourceDiscoverer.setAgentId(getId());
        resourceDiscoverer.setResourceDiscoveredListener(getResourceDiscoveredListener());
        resourceDiscoverer.setConnector(getConnector());
        resourceDiscoverer.discover();
        setResourceDiscoverer(resourceDiscoverer);

        DroolsResourceScanner scanner = new DroolsResourceScanner();
        scanner.setInterval(getScanInterval());
        scanner.setMetricsBufferSize(getMetricsBufferSize());
        setScanner(scanner);

        DroolsMonitoringScannerTask scannerTask = new DroolsMonitoringScannerTask();
        scannerTask.setResourceDiscoverer(resourceDiscoverer);
        scannerTask.setScanner(scanner);
        scannerTask.setReconnectionAgent(getReconnectionAgent());
        scannerTask.setOnConnectionLost(new ConnectionLost() {
            @Override
            public void stop() {
                getResourceDiscoverer().stop();
            }
        });
        for (DroolsMonitoringListener listener : getListeners()) {
            scannerTask.registerListener(listener);
        }
        setScannerTask(scannerTask);

        scanner.setScannerTask(scannerTask);
        scanner.start();
        setStarted(true);
    }

}
