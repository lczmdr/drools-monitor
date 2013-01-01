package com.lucazamador.drools.monitor.console.model.ksession.metric;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ruleMetrics")
public class KnowledgeRuleMetricDataList {

    private List<KnowledgeRuleMetricData> knowledgeRuleMetrics;

    public KnowledgeRuleMetricDataList() {
    }

    public KnowledgeRuleMetricDataList(List<KnowledgeRuleMetricData> knowledgeRuleMetrics) {
        this.knowledgeRuleMetrics = knowledgeRuleMetrics;
    }

    @XmlElement(name = "ruleMetric")
    public List<KnowledgeRuleMetricData> getKnowledgeRuleMetrics() {
        return knowledgeRuleMetrics;
    }

    public void setKnowledgeRuleMetrics(List<KnowledgeRuleMetricData> knowledgeRuleMetrics) {
        this.knowledgeRuleMetrics = knowledgeRuleMetrics;
    }

    public boolean isEmpty() {
        return this.knowledgeRuleMetrics == null || this.knowledgeRuleMetrics.size() == 0;
    }

}
