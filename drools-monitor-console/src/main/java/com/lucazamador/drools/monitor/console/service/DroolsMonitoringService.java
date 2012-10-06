package com.lucazamador.drools.monitor.console.service;

import com.lucazamador.drools.monitor.console.model.KnowledgeBaseDataList;
import com.lucazamador.drools.monitor.console.model.KnowledgeSessionDataList;
import com.lucazamador.drools.monitor.console.model.MonitoringAgentDataList;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;

public interface DroolsMonitoringService {

    MonitoringAgent getMonitoringAgent(String id);

    MonitoringAgentDataList getMonitoringAgents();

    KnowledgeBaseDataList getKnowledgeBaseInformation(String monitoringAgentId);

    KnowledgeSessionDataList getKnowledgeSessionInformation(String monitoringAgentId);

    KnowledgeSessionDataList getKnowledgeSessions(String monitoringAgentId, String knowledgeBaseId);

}
