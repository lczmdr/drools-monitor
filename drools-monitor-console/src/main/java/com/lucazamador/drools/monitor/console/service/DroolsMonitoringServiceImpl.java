package com.lucazamador.drools.monitor.console.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucazamador.drools.monitor.console.model.MonitoringAgentData;
import com.lucazamador.drools.monitor.console.model.MonitoringAgentDataList;
import com.lucazamador.drools.monitor.console.model.kbase.KnowledgeBaseData;
import com.lucazamador.drools.monitor.console.model.kbase.KnowledgeBaseDataList;
import com.lucazamador.drools.monitor.console.model.kbase.metric.KnowledgeBaseMetricData;
import com.lucazamador.drools.monitor.console.model.ksession.KnowledgeSessionData;
import com.lucazamador.drools.monitor.console.model.ksession.KnowledgeSessionDataList;
import com.lucazamador.drools.monitor.core.DroolsMonitoring;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionInfo;

@Component
public class DroolsMonitoringServiceImpl implements DroolsMonitoringService {

    @Autowired
    private DroolsMonitoring droolsMonitoring;

    @Override
    public MonitoringAgent getMonitoringAgent(String id) {
        return droolsMonitoring.getMonitoringAgent(id);
    }

    @Override
    public MonitoringAgentDataList getMonitoringAgents() {
        List<MonitoringAgentData> monitoringAgents = new ArrayList<MonitoringAgentData>();
        for (MonitoringAgent monitoringAgent : droolsMonitoring.getMonitoringAgents()) {
            MonitoringAgentData data = new MonitoringAgentData();
            data.setId(monitoringAgent.getId());
            data.setAddress(monitoringAgent.getAddress());
            data.setPort(monitoringAgent.getPort());
            data.setConnected(monitoringAgent.isConnected());
            data.setScanInterval(monitoringAgent.getScanInterval());
            data.setRecoveryInterval(monitoringAgent.getRecoveryInterval());
            monitoringAgents.add(data);
        }
        return new MonitoringAgentDataList(monitoringAgents);
    }

    @Override
    public KnowledgeBaseDataList getKnowledgeBaseInformation(String monitoringAgentId) {
        MonitoringAgent monitoringAgent = droolsMonitoring.getMonitoringAgent(monitoringAgentId);
        if (monitoringAgent == null) {
            return null;
        }
        List<KnowledgeBaseData> knowledgeBases = new ArrayList<KnowledgeBaseData>();
        for (KnowledgeBaseInfo knowledgeBaseInfo : monitoringAgent.getDiscoveredKnowledgeBases()) {
            KnowledgeBaseData data = new KnowledgeBaseData();
            data.setAgentId(knowledgeBaseInfo.getAgentId());
            data.setId(knowledgeBaseInfo.getId());
            data.setKnowledgeBaseId(knowledgeBaseInfo.getKnowledgeBaseId());
            knowledgeBases.add(data);
        }
        return new KnowledgeBaseDataList(knowledgeBases);
    }

    @Override
    public KnowledgeBaseMetricData getKnowledgeBaseMetric(String monitoringAgentId, String knowledgeBaseId) {
        MonitoringAgent monitoringAgent = droolsMonitoring.getMonitoringAgent(monitoringAgentId);
        if (monitoringAgent == null) {
            return null;
        }
        KnowledgeBaseMetric knowledgeBaseMetric = monitoringAgent.getKnowledgeBaseMetric(knowledgeBaseId);
        if (knowledgeBaseMetric == null) {
            return null;
        }
        return new KnowledgeBaseMetricData(knowledgeBaseMetric);
    }

    @Override
    public KnowledgeSessionDataList getKnowledgeSessionInformation(String monitoringAgentId) {
        MonitoringAgent monitoringAgent = droolsMonitoring.getMonitoringAgent(monitoringAgentId);
        if (monitoringAgent == null) {
            return null;
        }
        List<KnowledgeSessionData> knowledgeSessions = new ArrayList<KnowledgeSessionData>();
        for (KnowledgeSessionInfo knowledgeSessionInfo : monitoringAgent.getDiscoveredKnowledgeSessions()) {
            KnowledgeSessionData data = new KnowledgeSessionData();
            data.setId(knowledgeSessionInfo.getId());
            data.setAgentId(knowledgeSessionInfo.getAgentId());
            data.setKnowledgeBaseId(knowledgeSessionInfo.getKnowledgeBaseId());
            data.setKnowledgeSessionId(knowledgeSessionInfo.getKnowledgeSessionId());
            knowledgeSessions.add(data);
        }
        return new KnowledgeSessionDataList(knowledgeSessions);
    }

    @Override
    public KnowledgeSessionDataList getKnowledgeSessions(String monitoringAgentId, String knowledgeBaseId) {
        MonitoringAgent monitoringAgent = droolsMonitoring.getMonitoringAgent(monitoringAgentId);
        if (monitoringAgent == null) {
            return null;
        }
        List<KnowledgeSessionData> knowledgeSessions = new ArrayList<KnowledgeSessionData>();
        for (KnowledgeSessionInfo knowledgeSessionInfo : monitoringAgent.getDiscoveredKnowledgeSessions()) {
            if (knowledgeSessionInfo.getKnowledgeBaseId().equals(knowledgeBaseId)) {
                KnowledgeSessionData data = new KnowledgeSessionData();
                data.setId(knowledgeSessionInfo.getId());
                data.setAgentId(knowledgeSessionInfo.getAgentId());
                data.setKnowledgeBaseId(knowledgeSessionInfo.getKnowledgeBaseId());
                data.setKnowledgeSessionId(knowledgeSessionInfo.getKnowledgeSessionId());
                knowledgeSessions.add(data);
            }
        }
        return new KnowledgeSessionDataList(knowledgeSessions);
    }

    public void setDroolsMonitoring(DroolsMonitoring droolsMonitoring) {
        this.droolsMonitoring = droolsMonitoring;
    }

}
