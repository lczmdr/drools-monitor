package com.lucazamador.drools.monitor.console.model.ksession.metric;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lucazamador.drools.monitor.console.model.MetricData;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeProcessInstanceMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeProcessMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeRuleMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

@XmlRootElement(name = "ksessionMetric")
public class KnowledgeSessionMetricData extends MetricData {

    private String knowledgeBaseId;
    private Double averageFiringTime;
    private Integer knowledgeSessionId;
    private String agentId;
    private Date lastReset;
    private Long totalActivationsCancelled;
    private Long totalActivationsCreated;
    private Long totalActivationsFired;
    private Long totalFactCount;
    private Long totalFiringTime;
    private Long totalProcessInstancesCompleted;
    private Long totalProcessInstancesStarted;
    private KnowledgeRuleMetricDataList ruleStats;
    private KnowledgeProcessMetricDataList processStats;
    private KnowledgeProcessInstanceMetricDataList processInstancesStats;

    public KnowledgeSessionMetricData() {
    }

    public KnowledgeSessionMetricData(KnowledgeSessionMetric knowledgeSessionMetric) {
        this.setTimestamp(knowledgeSessionMetric.getTimestamp());
        this.knowledgeBaseId = knowledgeSessionMetric.getKnowledgeBaseId();
        this.averageFiringTime = knowledgeSessionMetric.getAverageFiringTime();
        this.knowledgeSessionId = knowledgeSessionMetric.getKnowledgeSessionId();
        this.agentId = knowledgeSessionMetric.getAgentId();
        this.lastReset = knowledgeSessionMetric.getLastReset();
        this.totalActivationsCancelled = knowledgeSessionMetric.getTotalActivationsCancelled();
        this.totalActivationsCreated = knowledgeSessionMetric.getTotalActivationsCreated();
        this.totalActivationsFired = knowledgeSessionMetric.getTotalActivationsFired();
        this.totalFactCount = knowledgeSessionMetric.getTotalFactCount();
        this.totalFiringTime = knowledgeSessionMetric.getTotalFiringTime();
        this.totalProcessInstancesCompleted = knowledgeSessionMetric.getTotalProcessInstancesCompleted();
        this.totalProcessInstancesStarted = knowledgeSessionMetric.getTotalProcessInstancesStarted();
        List<KnowledgeRuleMetricData> ruleMetricsData = new ArrayList<KnowledgeRuleMetricData>();
        for (KnowledgeRuleMetric ruleMetric : knowledgeSessionMetric.getRuleStats()) {
            ruleMetricsData.add(new KnowledgeRuleMetricData(ruleMetric));
        }
        this.ruleStats = new KnowledgeRuleMetricDataList(ruleMetricsData);
        List<KnowledgeProcessMetricData> processMetricsData = new ArrayList<KnowledgeProcessMetricData>();
        for (KnowledgeProcessMetric processMetric : knowledgeSessionMetric.getProcessStats()) {
            processMetricsData.add(new KnowledgeProcessMetricData(processMetric));
        }
        this.processStats = new KnowledgeProcessMetricDataList(processMetricsData);
        List<KnowledgeProcessInstanceMetricData> processInstanceMetricsData = new ArrayList<KnowledgeProcessInstanceMetricData>();
        for (KnowledgeProcessInstanceMetric processInstanceMetric : knowledgeSessionMetric.getProcessInstancesStats()) {
            processInstanceMetricsData.add(new KnowledgeProcessInstanceMetricData(processInstanceMetric));
        }
        this.processInstancesStats = new KnowledgeProcessInstanceMetricDataList(processInstanceMetricsData);
    }

    @XmlElement
    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    @XmlElement
    public Double getAverageFiringTime() {
        return averageFiringTime;
    }

    public void setAverageFiringTime(Double averageFiringTime) {
        this.averageFiringTime = averageFiringTime;
    }

    @XmlElement
    public Integer getKnowledgeSessionId() {
        return knowledgeSessionId;
    }

    public void setKnowledgeSessionId(Integer knowledgeSessionId) {
        this.knowledgeSessionId = knowledgeSessionId;
    }

    @XmlElement
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @XmlElement
    public Date getLastReset() {
        return lastReset;
    }

    public void setLastReset(Date lastReset) {
        this.lastReset = lastReset;
    }

    @XmlElement
    public Long getTotalActivationsCancelled() {
        return totalActivationsCancelled;
    }

    public void setTotalActivationsCancelled(Long totalActivationsCancelled) {
        this.totalActivationsCancelled = totalActivationsCancelled;
    }

    @XmlElement
    public Long getTotalActivationsCreated() {
        return totalActivationsCreated;
    }

    public void setTotalActivationsCreated(Long totalActivationsCreated) {
        this.totalActivationsCreated = totalActivationsCreated;
    }

    @XmlElement
    public Long getTotalActivationsFired() {
        return totalActivationsFired;
    }

    public void setTotalActivationsFired(Long totalActivationsFired) {
        this.totalActivationsFired = totalActivationsFired;
    }

    @XmlElement
    public Long getTotalFactCount() {
        return totalFactCount;
    }

    public void setTotalFactCount(Long totalFactCount) {
        this.totalFactCount = totalFactCount;
    }

    @XmlElement
    public Long getTotalFiringTime() {
        return totalFiringTime;
    }

    public void setTotalFiringTime(Long totalFiringTime) {
        this.totalFiringTime = totalFiringTime;
    }

    @XmlElement
    public Long getTotalProcessInstancesCompleted() {
        return totalProcessInstancesCompleted;
    }

    public void setTotalProcessInstancesCompleted(Long totalProcessInstancesCompleted) {
        this.totalProcessInstancesCompleted = totalProcessInstancesCompleted;
    }

    @XmlElement
    public Long getTotalProcessInstancesStarted() {
        return totalProcessInstancesStarted;
    }

    public void setTotalProcessInstancesStarted(Long totalProcessInstancesStarted) {
        this.totalProcessInstancesStarted = totalProcessInstancesStarted;
    }

    @XmlElement
    public KnowledgeRuleMetricDataList getRuleStats() {
        return ruleStats;
    }

    public void setRuleStats(KnowledgeRuleMetricDataList ruleStats) {
        this.ruleStats = ruleStats;
    }

    @XmlElement
    public KnowledgeProcessMetricDataList getProcessStats() {
        return processStats;
    }

    public void setProcessStats(KnowledgeProcessMetricDataList processStats) {
        this.processStats = processStats;
    }

    @XmlElement
    public KnowledgeProcessInstanceMetricDataList getProcessInstancesStats() {
        return processInstancesStats;
    }

    public void setProcessInstancesStats(KnowledgeProcessInstanceMetricDataList processInstancesStats) {
        this.processInstancesStats = processInstancesStats;
    }

}
