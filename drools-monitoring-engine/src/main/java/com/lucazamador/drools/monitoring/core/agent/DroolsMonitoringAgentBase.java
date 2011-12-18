package com.lucazamador.drools.monitoring.core.agent;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringScannerTask;
import com.lucazamador.drools.monitoring.core.DroolsResourceScanner;
import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;

/**
 * 
 * @author Lucas Amador
 * 
 */
public abstract class DroolsMonitoringAgentBase implements MonitoringAgent {

    protected String id;
    protected int scanInterval;
    protected int recoveryInterval;
    protected DroolsMBeanConnector connector;
    protected DroolsResourceScanner scanner;
    protected ResourceDiscoverer resourceDiscoverer;
    protected DroolsMonitoringScannerTask scannerTask;
    protected MonitoringRecoveryAgent reconnectionAgent;
    protected ResourceDiscoveredListener discoveredListener;
    protected List<DroolsMonitoringListener> listeners = new ArrayList<DroolsMonitoringListener>();
    protected boolean started;

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
            scannerTask.registerListener(listener);
        }
    }

    public void setResourceDiscoveredListener(ResourceDiscoveredListener discoveredListener) {
        this.discoveredListener = discoveredListener;
    }

    public interface ConnectionLost {
        public void stop();
    }

}
