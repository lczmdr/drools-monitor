package com.lucazamador.drools.monitoring.listener;

import java.util.EventListener;

import com.lucazamador.drools.monitoring.model.AbstractMetric;

/**
 * Interface used to create custom metric listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface DroolsMonitoringListener extends EventListener {

    public void newMetric(AbstractMetric metric);

}
