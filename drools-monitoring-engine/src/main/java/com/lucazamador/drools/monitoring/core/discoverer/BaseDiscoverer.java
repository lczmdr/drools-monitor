package com.lucazamador.drools.monitoring.core.discoverer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.scanner.MetricScanner;

/**
 * 
 * @author Lucas Amador
 *
 */
public abstract class BaseDiscoverer {
    
    protected DroolsMBeanConnector connector;
    protected List<MetricScanner> resourceScanners = new ArrayList<MetricScanner>();

    protected List<ObjectName> discoverResourceType(String resourceFilter) throws MalformedObjectNameException, IOException, DroolsMonitoringException {
        if (connector == null) {
            throw new DroolsMonitoringException("DroolsMBeanConnector must be provided");
        }
        Set<ObjectName> names = connector.getConnection().queryNames(new ObjectName(resourceFilter), null);
        List<ObjectName> resourceName = new ArrayList<ObjectName>();
        for (ObjectName objectName : names) {
            resourceName.add(objectName);
        }
        return resourceName;
    }

    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }
    
    public List<MetricScanner> getResourceScanners() {
        return resourceScanners;
    }

    public abstract void discover() throws DroolsMonitoringException;
    
}
