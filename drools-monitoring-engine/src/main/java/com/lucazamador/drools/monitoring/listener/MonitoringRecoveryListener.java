package com.lucazamador.drools.monitoring.listener;

/**
 * Interface used to create custom monitoring recovery listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface MonitoringRecoveryListener {

    public void disconnected(String agentId);

    public void reconnected(String agentId);

}
