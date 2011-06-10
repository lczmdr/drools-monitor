package com.lucazamador.drools.monitoring.core.discoverer;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitoring.scanner.MetricScanner;

/**
 * Discoverer wrapper
 * 
 * @author Lucas Amador
 * 
 */
public class ResourceDiscoverer {

    private String agentId;
    private DroolsMBeanConnector connector;
    private List<MetricScanner> resourceScanners = new ArrayList<MetricScanner>();
    private KnowledgeDiscoverer knowledgeResourceDiscoverer;

    public void discover() throws DroolsMonitoringException {
        knowledgeResourceDiscoverer = new KnowledgeDiscoverer();
        knowledgeResourceDiscoverer.setAgentId(agentId);
        knowledgeResourceDiscoverer.setConnector(connector);
        knowledgeResourceDiscoverer.discover();

        resourceScanners.addAll(knowledgeResourceDiscoverer.getResourceScanners());
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

    public List<MetricScanner> getResourceScanners() {
        return resourceScanners;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        return this.agentId;
    }

}
