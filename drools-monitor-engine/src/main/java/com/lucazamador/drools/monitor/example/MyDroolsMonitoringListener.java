package com.lucazamador.drools.monitor.example;

import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.model.Metric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeRuleMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

/**
 * A custom metric listener.
 * 
 * @author Lucas Amador
 * 
 */
public class MyDroolsMonitoringListener implements DroolsMonitoringListener {

    @Override
    public void newMetric(Metric metric) {
        System.out.println("custom metric listener: new metric obtained at " + metric.getTimestamp());
        if (metric instanceof KnowledgeSessionMetric) {
            KnowledgeSessionMetric ksm = (KnowledgeSessionMetric) metric;
            for (KnowledgeRuleMetric krm : ksm.getRuleStats()) {
                System.out.println(krm);
            }
        }
    }

}
