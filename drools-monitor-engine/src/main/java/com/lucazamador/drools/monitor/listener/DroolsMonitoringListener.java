package com.lucazamador.drools.monitor.listener;

import java.util.EventListener;

import com.lucazamador.drools.monitor.model.AbstractMetric;

/**
 * Interface used to create custom metric listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface DroolsMonitoringListener extends EventListener {

    public void newMetric(AbstractMetric metric);

}
