package com.lucazamador.drools.monitor.console.service;

import com.lucazamador.drools.monitor.console.model.MonitoringAgentDataList;
import com.lucazamador.drools.monitor.console.model.kbase.KnowledgeBaseDataList;
import com.lucazamador.drools.monitor.console.model.kbase.metric.KnowledgeBaseMetricData;
import com.lucazamador.drools.monitor.console.model.ksession.KnowledgeSessionDataList;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;

public interface DroolsMonitoringService {

    MonitoringAgent getMonitoringAgent(String id);

    MonitoringAgentDataList getMonitoringAgents();

    KnowledgeBaseDataList getKnowledgeBaseInformation(String monitoringAgentId);

    KnowledgeBaseMetricData getKnowledgeBaseMetric(String monitoringAgentId, String knowledgeBaseId);

    KnowledgeSessionDataList getKnowledgeSessionInformation(String monitoringAgentId);

    KnowledgeSessionDataList getKnowledgeSessions(String monitoringAgentId, String knowledgeBaseId);

}
