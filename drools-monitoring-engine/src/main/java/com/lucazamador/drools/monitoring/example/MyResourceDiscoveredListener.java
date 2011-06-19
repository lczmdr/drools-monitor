package com.lucazamador.drools.monitoring.example;

import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;

public class MyResourceDiscoveredListener implements ResourceDiscoveredListener {

    @Override
    public void discovered(String agentId) {
        System.out.println("resources discovered in agent: " + agentId);
    }

}