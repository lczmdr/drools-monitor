package com.lucazamador.drools.monitoring.core;

import java.io.IOException;
import java.rmi.ConnectException;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.scanner.MetricScanner;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringScannerTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(DroolsMonitoringScannerTask.class);

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
            } catch (ConnectException e) {
                logger.error("connection lost... trying again in a few seconds");
                break;
            } catch (IOException e) {
                logger.error("Error reading metrics " + resourceScanner.getResourceName() + ". " + e.getMessage());
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
