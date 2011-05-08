package com.lucazamador.drools.monitoring.scanner;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;

/**
 * 
 * @author Lucas Amador
 * 
 */
public abstract class MetricScanner implements ResourceScanner {

    private static final Logger logger = LoggerFactory.getLogger(MetricScanner.class);

    protected ObjectName resource;
    protected DroolsMBeanConnector connector;

    protected Object getAttribute(String attributeName) throws IOException {
        try {
            return connector.getConnection().getAttribute(resource, attributeName);
        } catch (AttributeNotFoundException e) {
            logger.error(getResourceName() + "=Attribute not founded: " + attributeName, e);
        } catch (InstanceNotFoundException e) {
            logger.error(getResourceName() + "=Mbean not founded", e);
        } catch (MBeanException e) {
            logger.error(getResourceName() + "=MBean error", e);
        } catch (ReflectionException e) {
            logger.error(getResourceName() + "=Invocation error when getting attribute: " + attributeName, e);
        }
        return null;
    }

    public String getResourceName() {
        return resource.getCanonicalName();
    }

}
