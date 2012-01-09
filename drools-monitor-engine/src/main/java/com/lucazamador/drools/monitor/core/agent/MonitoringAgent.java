package com.lucazamador.drools.monitor.core.agent;

import java.util.List;

import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionInfo;

public interface MonitoringAgent {

    public String getId();

    public void registerListener(DroolsMonitoringListener listener);

    public void start();

    public void stop();

    public boolean isConnected();

    public DroolsMBeanConnector getConnector();

    public void setConnector(DroolsMBeanConnector connector);

    public List<KnowledgeBaseInfo> getDiscoveredKnowledgeBases();

    public List<KnowledgeSessionInfo> getDiscoveredKnowledgeSessions();

}
