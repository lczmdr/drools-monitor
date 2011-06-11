package com.lucazamador.drools.monitoring.core;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;

/**
 * 
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringAgent implements MonitoringAgent {

    private String id;
    private int scanInterval;
    private int recoveryInterval;
    private DroolsMBeanConnector connector;
    private DroolsResourceScanner scanner;
    private ResourceDiscoverer resourceDiscoverer;
    private DroolsMonitoringScannerTask scannerTask;
    private MonitoringRecoveryAgent reconnectionAgent;
    private List<DroolsMonitoringListener> listeners = new ArrayList<DroolsMonitoringListener>();
    private boolean started;

    public void start() throws DroolsMonitoringException {
        resourceDiscoverer = new ResourceDiscoverer();
        resourceDiscoverer.setAgentId(id);
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

    public boolean isConnected() {
        if (connector == null) {
            return false;
        }
        return connector.isConnected();
    }

    public DroolsMBeanConnector getConnector() {
        return connector;
    }

    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setListeners(List<DroolsMonitoringListener> listeners) {
        this.listeners = listeners;
    }

    public List<KnowledgeBaseInfo> getDiscoveredKnowledgeBases() {
        return resourceDiscoverer.getDiscoveredKnowledgeBases();
    }

    public List<KnowledgeSessionInfo> getDiscoveredKnowledgeSessions() {
        return resourceDiscoverer.getDiscoveredKnowledgeSessions();
    }

    public List<DroolsMonitoringListener> getListeners() {
        return listeners;
    }

    public void registerListener(DroolsMonitoringListener listener) {
        listeners.add(listener);
        if (started) {
            scannerTask.addListener(listener);
        }
    }
}
