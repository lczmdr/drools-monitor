package com.lucazamador.drools.monitor.model.ksession;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lucazamador.drools.monitor.model.AbstractMetric;
import com.lucazamador.drools.monitor.model.builder.KnowledgeSessionMetricBuilder;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeSessionMetric extends AbstractMetric implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private List<KnowledgeProcessMetric> processStats;
    private List<KnowledgeProcessInstanceMetric> processInstancesStats;
    private List<KnowledgeRuleMetric> ruleStats;

    public KnowledgeSessionMetric() {
    }

    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public Double getAverageFiringTime() {
        return averageFiringTime;
    }

    public void setAverageFiringTime(Double averageFiringTime) {
        this.averageFiringTime = averageFiringTime;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    public Integer getKnowledgeSessionId() {
        return knowledgeSessionId;
    }

    public void setKnowledgeSessionId(Integer knowledgeSessionId) {
        this.knowledgeSessionId = knowledgeSessionId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        return agentId;
    }

    public Date getLastReset() {
        return lastReset;
    }

    public void setLastReset(Date lastReset) {
        this.lastReset = lastReset;
    }

    public Long getTotalActivationsCancelled() {
        return totalActivationsCancelled;
    }

    public void setTotalActivationsCancelled(Long totalActivationsCancelled) {
        this.totalActivationsCancelled = totalActivationsCancelled;
    }

    public Long getTotalActivationsCreated() {
        return totalActivationsCreated;
    }

    public void setTotalActivationsCreated(Long totalActivationsCreated) {
        this.totalActivationsCreated = totalActivationsCreated;
    }

    public Long getTotalActivationsFired() {
        return totalActivationsFired;
    }

    public void setTotalActivationsFired(Long totalActivationsFired) {
        this.totalActivationsFired = totalActivationsFired;
    }

    public Long getTotalFactCount() {
        return totalFactCount;
    }

    public void setTotalFactCount(Long totalFactCount) {
        this.totalFactCount = totalFactCount;
    }

    public Long getTotalFiringTime() {
        return totalFiringTime;
    }

    public void setTotalFiringTime(Long totalFiringTime) {
        this.totalFiringTime = totalFiringTime;
    }

    public Long getTotalProcessInstancesCompleted() {
        return totalProcessInstancesCompleted;
    }

    public void setTotalProcessInstancesCompleted(Long totalProcessInstancesCompleted) {
        this.totalProcessInstancesCompleted = totalProcessInstancesCompleted;
    }

    public Long getTotalProcessInstancesStarted() {
        return totalProcessInstancesStarted;
    }

    public void setTotalProcessInstancesStarted(Long totalProcessInstancesStarted) {
        this.totalProcessInstancesStarted = totalProcessInstancesStarted;
    }

    public List<KnowledgeProcessMetric> getProcessStats() {
        return processStats;
    }

    public void setProcessStats(List<KnowledgeProcessMetric> processStats) {
        this.processStats = processStats;
    }

    public List<KnowledgeProcessInstanceMetric> getProcessInstancesStats() {
        return processInstancesStats;
    }

    public void setProcessInstancesStats(List<KnowledgeProcessInstanceMetric> processInstances) {
        this.processInstancesStats = processInstances;
    }

    public List<KnowledgeRuleMetric> getRuleStats() {
        return ruleStats;
    }

    public void setRuleStats(List<KnowledgeRuleMetric> ruleStats) {
        this.ruleStats = ruleStats;
    }

    @Override
    public String toString() {
        return "KnowledgeSession=" + knowledgeSessionId + " averageFiringTime: " + averageFiringTime
                + " totalFactCount: " + totalFactCount + " knowledgeBaseId: " + knowledgeBaseId;
    }

    public KnowledgeSessionMetric(KnowledgeSessionMetricBuilder builder) {
        averageFiringTime = builder.getAverageFiringTime();
        knowledgeBaseId = builder.getKnowledgeBaseId();
        knowledgeSessionId = builder.getKnowledgeSessionId();
        agentId = builder.getJvmName();
        lastReset = builder.getLastReset();
        totalActivationsCancelled = builder.getTotalActivationsCancelled();
        totalActivationsCreated = builder.getTotalActivationsCreated();
        totalActivationsFired = builder.getTotalActivationsFired();
        totalFactCount = builder.getTotalFactCount();
        totalFiringTime = builder.getTotalFiringTime();
        totalProcessInstancesCompleted = builder.getTotalProcessInstancesCompleted();
        totalProcessInstancesStarted = builder.getTotalProcessInstancesStarted();
        processStats = builder.getProcessStats();
        processInstancesStats = builder.getProcessInstanceStats();
        ruleStats = builder.getRuleStats();
    }
}
