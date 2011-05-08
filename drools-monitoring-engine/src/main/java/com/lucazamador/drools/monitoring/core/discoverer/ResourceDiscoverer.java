package com.lucazamador.drools.monitoring.core.discoverer;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.scanner.MetricScanner;

/**
 * Discoverer wrapper
 * 
 * @author Lucas Amador
 * 
 */
public class ResourceDiscoverer {

    private String jvmId;
    private DroolsMBeanConnector connector;
    private List<MetricScanner> resourceScanners = new ArrayList<MetricScanner>();
    private KnowledgeDiscoverer knowledgeResourceDiscoverer;

    public void discover() throws DroolsMonitoringException {
        // instantiate discoverer implementations
        knowledgeResourceDiscoverer = new KnowledgeDiscoverer();
        knowledgeResourceDiscoverer.setJvmId(jvmId);
        knowledgeResourceDiscoverer.setConnector(connector);
        knowledgeResourceDiscoverer.discover();

        resourceScanners.addAll(knowledgeResourceDiscoverer.getResourceScanners());
    }

//    public void reconnect(DroolsMBeanConnector connector) {
//        knowledgeResourceDiscoverer.setConnector(connector);
//        knowledgeResourceDiscoverer.discover();
//    }

    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }

    public List<MetricScanner> getResourceScanners() {
        return resourceScanners;
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

    public String getJvmId() {
        return this.jvmId;
    }

}
