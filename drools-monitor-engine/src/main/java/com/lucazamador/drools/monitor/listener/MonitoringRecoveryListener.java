package com.lucazamador.drools.monitor.listener;

import java.util.EventListener;

/**
 * Interface used to create custom monitoring recovery listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface MonitoringRecoveryListener extends EventListener {

    void disconnected(String agentId);

    void reconnected(String agentId);

}
