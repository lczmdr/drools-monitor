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

    private DroolsMBeanConnector connector;
    private List<MetricScanner> resourceScanners = new ArrayList<MetricScanner>();
    private String jvmId;

    public void discover() throws DroolsMonitoringException {
        // instantiate discoverer implementations
        KnowledgeDiscoverer knowledgeResourceDiscoverer = new KnowledgeDiscoverer();
        knowledgeResourceDiscoverer.setJvmId(jvmId);
        knowledgeResourceDiscoverer.setConnector(connector);
        knowledgeResourceDiscoverer.discover();

        resourceScanners.addAll(knowledgeResourceDiscoverer.getResourceScanners());
    }

    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }

    public List<MetricScanner> getResourceScanners() {
        return resourceScanners;
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

}
