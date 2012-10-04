package com.lucazamador.drools.monitor.core.agent;

import java.util.List;

import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitor.core.recovery.MonitoringRecoveryAgent;
import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionInfo;

public interface MonitoringAgent {

    String getId();

    String getAddress();

    int getPort();

    int getRecoveryInterval();

    void registerListener(DroolsMonitoringListener listener);

    void start();

    void stop();

    boolean isConnected();

    DroolsMBeanConnector getConnector();

    void setConnector(DroolsMBeanConnector connector);

    void setMonitoringRecoveryAgent(MonitoringRecoveryAgent reconnectionAgent);

    List<KnowledgeBaseInfo> getDiscoveredKnowledgeBases();

    List<KnowledgeSessionInfo> getDiscoveredKnowledgeSessions();

}
