package com.lucazamador.drools.monitoring.listener;

import com.lucazamador.drools.monitoring.model.AbstractMetric;

/**
 * Interface used to create custom metric listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface DroolsMonitoringListener {

    public void newMetric(AbstractMetric metric);

}
