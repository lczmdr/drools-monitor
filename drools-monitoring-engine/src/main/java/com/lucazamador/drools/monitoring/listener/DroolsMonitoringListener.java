package com.lucazamador.drools.monitoring.listener;

import com.lucazamador.drools.monitoring.model.AbstractMetric;

public interface DroolsMonitoringListener {

    public void newMetric(AbstractMetric metric);

}
