package com.lucazamador.drools.monitor.console.model.ksession.metric;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "processInstanceMetrics")
public class KnowledgeProcessInstanceMetricDataList {

    private List<KnowledgeProcessInstanceMetricData> metrics;

    public KnowledgeProcessInstanceMetricDataList() {
    }

    public KnowledgeProcessInstanceMetricDataList(List<KnowledgeProcessInstanceMetricData> metrics) {
        this.metrics = metrics;
    }

    @XmlElement(name = "processInstanceMetric")
    public List<KnowledgeProcessInstanceMetricData> getKnowledgeProcessInstanceMetricData() {
        return metrics;
    }

    public void setKnowledgeProcessInstanceMetricData(List<KnowledgeProcessInstanceMetricData> metrics) {
        this.metrics = metrics;
    }

    public boolean isEmpty() {
        return this.metrics == null || this.metrics.size() == 0;
    }

}
