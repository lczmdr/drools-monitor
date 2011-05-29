package com.lucazamador.drools.monitoring;

import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.AbstractMetric;

public class MyDroolsMonitoringListener implements DroolsMonitoringListener {

    @Override
    public void newMetric(AbstractMetric metric) {
        System.out.println(this + " new metric obtained: " + metric.getTimestamp());
    }

}