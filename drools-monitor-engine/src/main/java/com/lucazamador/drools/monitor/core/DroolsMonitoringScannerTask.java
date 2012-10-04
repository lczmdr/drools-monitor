package com.lucazamador.drools.monitor.core;

import java.io.IOException;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.core.agent.DroolsMonitoringAgentBase.ConnectionLost;
import com.lucazamador.drools.monitor.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitor.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.model.Metric;
import com.lucazamador.drools.monitor.scanner.MetricScanner;

/**
 * A scanner timer task used to scan the available metrics for a monitoring
 * agent. It also creates a reconnection task when the connection is lost.
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringScannerTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsMonitoringScannerTask.class);

    private MonitoringRecoveryAgent reconnectionAgent;
    private ResourceDiscoverer resourceDiscoverer;
    private DroolsResourceScanner scanner;
    private ConnectionLost connectionLost;
    private List<DroolsMonitoringListener> listeners = Collections
            .synchronizedList(new ArrayList<DroolsMonitoringListener>());

    /**
     * Obtain the current metrics from the available resources and trigger the
     * registered metric listeners. In case of a connection error the resource
     * discoverer is stopped and a recovery task is started.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        Map<String, MetricScanner> resourceScanners = resourceDiscoverer.getResourceScanners();
        synchronized (resourceScanners) {
            for (MetricScanner resourceScanner : resourceScanners.values()) {
                try {
                    Metric metric = resourceScanner.scan();
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
                    LOGGER.error("connection lost... trying again in a few seconds");
                    connectionLost.stop();
                    String agentId = resourceDiscoverer.getAgentId();
                    String address = resourceDiscoverer.getConnector().getAddress();
                    int port = resourceDiscoverer.getConnector().getPort();
                    reconnectionAgent.reconnect(agentId, address, port);
                    cancel();
                    return;
                } catch (IOException e) {
                    LOGGER.error("Error reading metrics " + resourceScanner.getResourceName() + ". " + e.getMessage());
                }
            }
        }
    }

    /**
     * Register a metric listener to the current scanner task
     * 
     * @param listener
     *            the listener to be registered
     */
    public void registerListener(DroolsMonitoringListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
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

    public void setOnConnectionLost(ConnectionLost onConnectionLost) {
        this.connectionLost = onConnectionLost;
    }

}
