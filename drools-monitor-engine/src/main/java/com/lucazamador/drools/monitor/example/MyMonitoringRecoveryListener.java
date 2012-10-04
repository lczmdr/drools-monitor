package com.lucazamador.drools.monitor.example;

import com.lucazamador.drools.monitor.listener.MonitoringRecoveryListener;

/**
 * A custom connection recovery listener.
 * 
 * @author Lucas Amador
 * 
 */
public class MyMonitoringRecoveryListener implements MonitoringRecoveryListener {

    @Override
    public void reconnected(String agentId) {
        System.out.println("recovery listener: reconnection created with " + agentId);
    }

    @Override
    public void disconnected(String agentId) {
        System.out.println("recovery listener: disconnected from " + agentId);
    }

}
