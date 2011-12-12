package com.lucazamador.drools.monitoring.example;

import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.AbstractMetric;

public class MyDroolsMonitoringListener implements DroolsMonitoringListener {

    @Override
    public void newMetric(AbstractMetric metric) {
        System.out.println("custom metric listener: new metric obtained at " + metric.getTimestamp());
    }

}