package com.lucazamador.drools.monitoring.core;

import java.io.IOException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.scanner.MetricScanner;

/**
 * A scanner timer task used to scan the available metrics for a monitoring
 * agent. It also creates a reconnection task when the connection is lost.
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringScannerTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(DroolsMonitoringScannerTask.class);

    private MonitoringRecoveryAgent reconnectionAgent;
    private ResourceDiscoverer resourceDiscoverer;
    private DroolsResourceScanner scanner;
    private List<DroolsMonitoringListener> listeners = Collections
            .synchronizedList(new ArrayList<DroolsMonitoringListener>());

    /**
     * Obtain the current metrics from the available resources and trigger the
     * registered metric listeners.
     */
    @Override
    public void run() {
        Map<String, MetricScanner> resourceScanners = resourceDiscoverer.getResourceScanners();
        synchronized (resourceScanners) {
            for (MetricScanner resourceScanner : resourceScanners.values()) {
                try {
                    AbstractMetric metric = resourceScanner.scan();
                    if (metric != null) {
                        synchronized (scanner.getMetrics()) {
                            scanner.getMetrics().add(metric);
                        }
                        synchronized (listeners) {
                            for (DroolsMonitoringListener listener : listeners) {
                                listener.newMetric(metric);
                            }
                        }
                    }
                } catch (ConnectException e) {
                    logger.error("connection lost... trying again in a few seconds");
                    resourceDiscoverer.stop();
                    reconnectionAgent.reconnect(resourceDiscoverer.getAgentId(), resourceDiscoverer.getConnector()
                            .getAddress(), resourceDiscoverer.getConnector().getPort());
                    cancel();
                    return;
                } catch (IOException e) {
                    logger.error("Error reading metrics " + resourceScanner.getResourceName() + ". " + e.getMessage());
                }
            }
        }
    }

    public MonitoringRecoveryAgent getReconnectionAgent() {
        return reconnectionAgent;
    }

    public void setReconnectionAgent(MonitoringRecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
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

    /**
     * Register a listener to the current scanner task
     * 
     * @param listener
     *            the listener to be registered
     */
    public void registerListener(DroolsMonitoringListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

}
