package com.lucazamador.drools.monitor.console.model.ksession.metric;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "processMetrics")
public class KnowledgeProcessMetricDataList {

    private List<KnowledgeProcessMetricData> metrics;

    public KnowledgeProcessMetricDataList() {
    }

    public KnowledgeProcessMetricDataList(List<KnowledgeProcessMetricData> metrics) {
        this.metrics = metrics;
    }

    @XmlElement(name = "processMetric")
    public List<KnowledgeProcessMetricData> getKnowledgeProcessMetricData() {
        return metrics;
    }

    public void setKnowledgeProcessMetricData(List<KnowledgeProcessMetricData> metrics) {
        this.metrics = metrics;
    }

    public boolean isEmpty() {
        return this.metrics == null || this.metrics.size() == 0;
    }

}
