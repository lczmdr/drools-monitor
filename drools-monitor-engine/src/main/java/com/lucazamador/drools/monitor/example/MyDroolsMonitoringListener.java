package com.lucazamador.drools.monitor.example;

import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.model.AbstractMetric;

/**
 * A custom metric listener.
 * 
 * @author Lucas Amador
 * 
 */
public class MyDroolsMonitoringListener implements DroolsMonitoringListener {

    @Override
    public void newMetric(AbstractMetric metric) {
        System.out.println("custom metric listener: new metric obtained at " + metric.getTimestamp());
    }

}