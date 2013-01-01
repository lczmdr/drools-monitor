package com.lucazamador.drools.monitor.core.agent;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitor.core.DroolsMonitoringScannerTask;
import com.lucazamador.drools.monitor.core.DroolsResourceScanner;
import com.lucazamador.drools.monitor.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitor.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.listener.ResourceDiscoveredListener;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

/**
 * 
 * @author Lucas Amador
 * 
 */
public abstract class DroolsMonitoringAgentBase implements MonitoringAgent {

    private String id;
    private String address;
    private int port;
    private int scanInterval;
    private int metricsBufferSize;
    private int recoveryInterval;
    private DroolsMBeanConnector connector;
    private DroolsResourceScanner scanner;
    private ResourceDiscoverer resourceDiscoverer;
    private DroolsMonitoringScannerTask scannerTask;
    private MonitoringRecoveryAgent reconnectionAgent;
    private ResourceDiscoveredListener resourceDiscoveredListener;
    private List<DroolsMonitoringListener> listeners = new ArrayList<DroolsMonitoringListener>();
    private boolean started;

    @Override
    public void setMonitoringRecoveryAgent(MonitoringRecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public int getMetricsBufferSize() {
        return metricsBufferSize;
    }

    public void setMetricsBufferSize(int metricsBufferSize) {
        this.metricsBufferSize = metricsBufferSize;
    }

    @Override
    public int getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    @Override
    public DroolsMBeanConnector getConnector() {
        return connector;
    }

    @Override
    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }

    public DroolsResourceScanner getScanner() {
        return scanner;
    }

    public void setScanner(DroolsResourceScanner scanner) {
        this.scanner = scanner;
    }

    public ResourceDiscoverer getResourceDiscoverer() {
        return resourceDiscoverer;
    }

    public void setResourceDiscoverer(ResourceDiscoverer resourceDiscoverer) {
        this.resourceDiscoverer = resourceDiscoverer;
    }

    public DroolsMonitoringScannerTask getScannerTask() {
        return scannerTask;
    }

    public void setScannerTask(DroolsMonitoringScannerTask scannerTask) {
        this.scannerTask = scannerTask;
    }

    public MonitoringRecoveryAgent getReconnectionAgent() {
        return reconnectionAgent;
    }

    public void setReconnectionAgent(MonitoringRecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
    }

    public ResourceDiscoveredListener getResourceDiscoveredListener() {
        return resourceDiscoveredListener;
    }

    public void setResourceDiscoveredListener(ResourceDiscoveredListener resourceDiscoveredListener) {
        this.resourceDiscoveredListener = resourceDiscoveredListener;
    }

    public List<DroolsMonitoringListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<DroolsMonitoringListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void registerListener(DroolsMonitoringListener listener) {
        listeners.add(listener);
        if (started) {
            scannerTask.registerListener(listener);
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public List<KnowledgeBaseInfo> getDiscoveredKnowledgeBases() {
        return resourceDiscoverer.getDiscoveredKnowledgeBases();
    }

    @Override
    public List<KnowledgeSessionInfo> getDiscoveredKnowledgeSessions() {
        return resourceDiscoverer.getDiscoveredKnowledgeSessions();
    }

    @Override
    public List<KnowledgeBaseMetric> getKnowledgeBaseMetric() {
        return scanner.getKnowledgeBaseMetric();
    }

    @Override
    public KnowledgeBaseMetric getKnowledgeBaseMetric(String knowledgeBaseId) {
        return scanner.getKnowledgeBaseMetric(knowledgeBaseId);
    }

    @Override
    public List<KnowledgeSessionMetric> getKnowledgeSessionMetrics(String knowledgeBaseId, int knowledgeSessionId,
            int size) {
        return scanner.getKnowledgeSessionMetric(knowledgeBaseId, knowledgeSessionId, size);
    }

    @Override
    public boolean isConnected() {
        if (connector == null) {
            return false;
        }
        return connector.isConnected();
    }

    @Override
    public synchronized void stop() {
        if (scanner != null) {
            scanner.stop();
        }
        reconnectionAgent.removeRecoveryTask(id);
    }

    public interface ConnectionLost {
        void stop();
    }

}
