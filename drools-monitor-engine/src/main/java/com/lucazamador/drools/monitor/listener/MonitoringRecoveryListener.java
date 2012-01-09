package com.lucazamador.drools.monitor.listener;

import java.util.EventListener;

/**
 * Interface used to create custom monitoring recovery listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface MonitoringRecoveryListener extends EventListener {

    public void disconnected(String agentId);

    public void reconnected(String agentId);

}
