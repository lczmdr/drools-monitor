package com.lucazamador.drools.monitoring.example;

import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;

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