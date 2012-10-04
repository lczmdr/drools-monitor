package com.lucazamador.drools.monitor.listener;

import java.util.EventListener;

import com.lucazamador.drools.monitor.model.Metric;

/**
 * Interface used to create custom metric listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface DroolsMonitoringListener extends EventListener {

    void newMetric(Metric metric);

}
