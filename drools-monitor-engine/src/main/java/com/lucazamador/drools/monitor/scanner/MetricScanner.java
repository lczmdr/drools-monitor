package com.lucazamador.drools.monitor.scanner;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;

/**
 * Basic metric scanner functionality.
 * 
 * @author Lucas Amador
 * 
 */
public abstract class MetricScanner implements ResourceScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricScanner.class);

    private ObjectName resource;
    private DroolsMBeanConnector connector;

    protected Object getAttribute(String attributeName) throws IOException {
        try {
            return connector.getConnection().getAttribute(resource, attributeName);
        } catch (AttributeNotFoundException e) {
            LOGGER.error(getResourceName() + "=Attribute not founded: " + attributeName, e);
        } catch (InstanceNotFoundException e) {
            LOGGER.error(getResourceName() + "=Mbean not founded", e);
        } catch (MBeanException e) {
            LOGGER.error(getResourceName() + "=MBean error", e);
        } catch (ReflectionException e) {
            LOGGER.error(getResourceName() + "=Invocation error when getting attribute: " + attributeName, e);
        }
        return null;
    }

    public ObjectName getResource() {
        return resource;
    }

    public void setResource(ObjectName resource) {
        this.resource = resource;
    }

    public String getResourceName() {
        return resource.getCanonicalName();
    }

    public DroolsMBeanConnector getConnector() {
        return connector;
    }

    public void setConnector(DroolsMBeanConnector connector) {
        this.connector = connector;
    }

}
