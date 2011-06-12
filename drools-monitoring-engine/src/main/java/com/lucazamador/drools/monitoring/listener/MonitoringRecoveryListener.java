package com.lucazamador.drools.monitoring.listener;

public interface MonitoringRecoveryListener {

    public void disconnected(String agentId);
    
    public void reconnected(String agentId);

}
