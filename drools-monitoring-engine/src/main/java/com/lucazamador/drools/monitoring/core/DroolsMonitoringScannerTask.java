package com.lucazamador.drools.monitoring.core;

import java.io.IOException;
import java.util.TimerTask;

import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.scanner.MetricScanner;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringScannerTask extends TimerTask {

    private ResourceDiscoverer resourceDiscoverer;
    private DroolsResourceScanner scanner;

    @Override
    public void run() {
        for (MetricScanner resourceScanner : resourceDiscoverer.getResourceScanners()) {
            try {
                AbstractMetric metric = resourceScanner.scan();
                if (metric != null) {
                    synchronized (scanner.getMetrics()) {
                        scanner.getMetrics().add(metric);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("JVM connection error", e);
            }
        }
    }

    public void setResourceDiscoverer(ResourceDiscoverer resourceDiscoverer) {
        this.resourceDiscoverer = resourceDiscoverer;
    }

    public ResourceDiscoverer getResourceDiscoverer() {
        return resourceDiscoverer;
    }

    public void setScanner(DroolsResourceScanner scanner) {
        this.scanner = scanner;
    }

}
