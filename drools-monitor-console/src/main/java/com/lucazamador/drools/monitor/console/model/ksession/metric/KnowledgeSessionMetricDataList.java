package com.lucazamador.drools.monitor.console.model.ksession.metric;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ksessionMetrics")
public class KnowledgeSessionMetricDataList {

    private List<KnowledgeSessionMetricData> knowledgeSessions;

    public KnowledgeSessionMetricDataList() {
    }

    public KnowledgeSessionMetricDataList(List<KnowledgeSessionMetricData> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

    @XmlElement(name = "metric")
    public List<KnowledgeSessionMetricData> getKnowledgeSession() {
        return knowledgeSessions;
    }

    public void setKnowledgeSession(List<KnowledgeSessionMetricData> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

    public boolean isEmpty() {
        return this.knowledgeSessions == null || this.knowledgeSessions.size() == 0;
    }

}
