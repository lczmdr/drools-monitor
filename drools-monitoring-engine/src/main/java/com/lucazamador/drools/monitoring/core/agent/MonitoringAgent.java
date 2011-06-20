package com.lucazamador.drools.monitoring.core.agent;

import java.util.List;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;

public interface MonitoringAgent {

    public String getId();

    public void registerListener(DroolsMonitoringListener listener);

    public void start() throws DroolsMonitoringException;

    public void stop();

    public boolean isConnected();

    public DroolsMBeanConnector getConnector();

    public void setConnector(DroolsMBeanConnector connector);

    public List<KnowledgeBaseInfo> getDiscoveredKnowledgeBases();

    public List<KnowledgeSessionInfo> getDiscoveredKnowledgeSessions();

}
