package com.lucazamador.drools.monitor.console.model.kbase.metric;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "globalMetrics")
public class KnowledgeGlobalMetricDataList {

    private List<KnowledgeGlobalMetricData> metrics;

    public KnowledgeGlobalMetricDataList() {
    }

    public KnowledgeGlobalMetricDataList(List<KnowledgeGlobalMetricData> metrics) {
        this.metrics = metrics;
    }

    @XmlElement(name = "globalMetric")
    public List<KnowledgeGlobalMetricData> getKnowledgeGlobalMetrics() {
        return this.metrics;
    }

    public void setKnowledgeGlobalMetrics(List<KnowledgeGlobalMetricData> metrics) {
        this.metrics = metrics;
    }

}
