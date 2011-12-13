package com.lucazamador.drools.monitoring.core.discoverer;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitoring.scanner.MetricScanner;

/**
 * Resouces discoverer wrapper used by the MonitoringAgent. Initialize the
 * knowledge discoverer and his timer.
 * 
 * @author Lucas Amador
 * 
 */
public class ResourceDiscoverer {

    private String agentId;
    private DroolsMBeanConnector connector;
    private KnowledgeResourceDiscoverer knowledgeResourceDiscoverer;
    private ResourceDiscoveredListener discoveredListener;
    private KnowledgeDiscovererTask knowledgeDiscovererTask;

    /**
     * Initialize the knowledge resource discoverer timer and his knowledge
     * discoverer timer task.
     */
    public void discover() {
        knowledgeResourceDiscoverer = new KnowledgeResourceDiscoverer();
        knowledgeResourceDiscoverer.setAgentId(agentId);
        knowledgeResourceDiscoverer.setConnector(connector);
        knowledgeResourceDiscoverer.setResourceDiscoveredListener(discoveredListener);

        knowledgeDiscovererTask = new KnowledgeDiscovererTask();
        knowledgeDiscovererTask.setKnowledgeResourceDiscoverer(knowledgeResourceDiscoverer);

        Timer discovererTimer = new Timer();
        discovererTimer.scheduleAtFixedRate(knowledgeDiscovererTask, 0, 2000);

    }

    public List<KnowledgeSessionInfo> getDiscoveredKnowledgeSessions() {
        return knowledgeResourceDiscoverer.getKnowledgeSessionInfos();
    }

    public List<KnowledgeBaseInfo> getDiscoveredKnowledgeBases() {
        return knowledgeResourceDiscoverer.getKnowledgeBaseInfos();
    }

    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }

    public DroolsMBeanConnector getConnector() {
        return this.connector;
    }

    public Map<String, MetricScanner> getResourceScanners() {
        return knowledgeResourceDiscoverer.getResourceScanners();
    }

    public void setResourceDiscoveredListener(ResourceDiscoveredListener discoveredListener) {
        this.discoveredListener = discoveredListener;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void stop() {
        this.knowledgeDiscovererTask.cancel();
    }
}
