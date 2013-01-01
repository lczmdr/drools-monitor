package com.lucazamador.drools.monitor.console.model.kbase.metric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lucazamador.drools.monitor.console.model.MetricData;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseMetric;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeGlobalMetric;

@XmlRootElement(name = "kbaseMetric")
public class KnowledgeBaseMetricData extends MetricData {

    private String knowledgeBaseId;
    private String agentId;
    private Long sessionCount;
    private String packages;
    private KnowledgeGlobalMetricDataList globals;

    public KnowledgeBaseMetricData() {
    }

    public KnowledgeBaseMetricData(KnowledgeBaseMetric metric) {
        this.setTimestamp(metric.getTimestamp());
        this.knowledgeBaseId = metric.getKnowledgeBaseId();
        this.agentId = metric.getAgentId();
        this.sessionCount = metric.getSessionCount();
        this.packages = metric.getPackages();
        List<KnowledgeGlobalMetricData> globals = new ArrayList<KnowledgeGlobalMetricData>();
        for (KnowledgeGlobalMetric globalMetric : metric.getGlobals()) {
            globals.add(new KnowledgeGlobalMetricData(globalMetric));
        }
        this.globals = new KnowledgeGlobalMetricDataList(globals);
    }

    @XmlElement
    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    @XmlElement
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @XmlElement
    public Long getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Long sessionCount) {
        this.sessionCount = sessionCount;
    }

    @XmlElement
    public String getPackages() {
        return packages;
    }

    public List<String> getPackagesSplited() {
        return Arrays.asList(packages.split(";"));
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    @XmlElement
    public KnowledgeGlobalMetricDataList getGlobals() {
        return globals;
    }

    public void setGlobals(KnowledgeGlobalMetricDataList globals) {
        this.globals = globals;
    }

}
