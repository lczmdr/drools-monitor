package com.lucazamador.drools.monitoring;

import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;

public class MyMonitoringRecoveryListener implements MonitoringRecoveryListener {

    @Override
    public void reconnected(String agentId) {
        System.out.println("reconnection created with " + agentId);
    }

    @Override
    public void disconnected(String agentId) {
        System.out.println("disconnected from " + agentId);
    }

}