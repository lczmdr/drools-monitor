package com.lucazamador.drools.monitor.model.builder;

import java.util.Date;
import java.util.List;

import com.lucazamador.drools.monitor.model.ksession.KnowledgeProcessInstanceMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeProcessMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

/**
 * Builder to create KnowledgeSessionMetric's objects
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeSessionMetricBuilder {

    private final String knowledgeBaseId;
    private String jvmName;
    private Double averageFiringTime;
    private Integer knowledgeSessionId;
    private Date lastReset;
    private Long totalActivationsCancelled;
    private Long totalActivationsCreated;
    private Long totalActivationsFired;
    private Long totalFactCount;
    private Long totalFiringTime;
    private Long totalProcessInstancesCompleted;
    private Long totalProcessInstancesStarted;
    private List<KnowledgeProcessMetric> processStats;
    private List<KnowledgeProcessInstanceMetric> processInstanceStats;

    public KnowledgeSessionMetricBuilder(String jvmName, String knowledgeBaseId) {
        this.jvmName = jvmName;
        this.knowledgeBaseId = knowledgeBaseId;
    }

    public KnowledgeSessionMetricBuilder averageFiringTime(Double averageFiringTime) {
        this.averageFiringTime = averageFiringTime;
        return this;
    }

    public KnowledgeSessionMetricBuilder knowledgeSessionId(Integer knowledgeSessionId) {
        this.knowledgeSessionId = knowledgeSessionId;
        return this;
    }

    public KnowledgeSessionMetricBuilder lastReset(Date lastReset) {
        this.lastReset = lastReset;
        return this;
    }

    public KnowledgeSessionMetricBuilder totalActivationsCancelled(Long totalActivationsCancelled) {
        this.totalActivationsCancelled = totalActivationsCancelled;
        return this;
    }

    public KnowledgeSessionMetricBuilder totalActivationsCreated(Long totalActivationsCreated) {
        this.totalActivationsCreated = totalActivationsCreated;
        return this;
    }

    public KnowledgeSessionMetricBuilder totalActivationsFired(Long totalActivationsFired) {
        this.totalActivationsFired = totalActivationsFired;
        return this;
    }

    public KnowledgeSessionMetricBuilder totalFactCount(Long totalFactCount) {
        this.totalFactCount = totalFactCount;
        return this;
    }

    public KnowledgeSessionMetricBuilder totalFiringTime(Long totalFiringTime) {
        this.totalFiringTime = totalFiringTime;
        return this;
    }

    public KnowledgeSessionMetricBuilder totalProcessInstancesCompleted(Long totalProcessInstancesCompleted) {
        this.totalProcessInstancesCompleted = totalProcessInstancesCompleted;
        return this;
    }

    public KnowledgeSessionMetricBuilder totalProcessInstancesStarted(Long totalProcessInstancesStarted) {
        this.totalProcessInstancesStarted = totalProcessInstancesStarted;
        return this;
    }

    public KnowledgeSessionMetricBuilder processStats(List<KnowledgeProcessMetric> processStats) {
        this.processStats = processStats;
        return this;
    }

    public KnowledgeSessionMetricBuilder processInstanceStats(List<KnowledgeProcessInstanceMetric> processInstanceStats) {
        this.processInstanceStats = processInstanceStats;
        return this;
    }

    public KnowledgeSessionMetric build() {
        return new KnowledgeSessionMetric(this);
    }

    public Double getAverageFiringTime() {
        return averageFiringTime;
    }

    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public String getJvmName() {
        return jvmName;
    }

    public Integer getKnowledgeSessionId() {
        return knowledgeSessionId;
    }

    public Date getLastReset() {
        return lastReset;
    }

    public Long getTotalActivationsCancelled() {
        return totalActivationsCancelled;
    }

    public Long getTotalActivationsCreated() {
        return totalActivationsCreated;
    }

    public Long getTotalActivationsFired() {
        return totalActivationsFired;
    }

    public Long getTotalFactCount() {
        return totalFactCount;
    }

    public Long getTotalFiringTime() {
        return totalFiringTime;
    }

    public Long getTotalProcessInstancesCompleted() {
        return totalProcessInstancesCompleted;
    }

    public Long getTotalProcessInstancesStarted() {
        return totalProcessInstancesStarted;
    }

    public List<KnowledgeProcessMetric> getProcessStats() {
        return processStats;
    }

    public List<KnowledgeProcessInstanceMetric> getProcessInstanceStats() {
        return processInstanceStats;
    }

}