package com.lucazamador.drools.monitor.example;

import com.lucazamador.drools.monitor.listener.ResourceDiscoveredListener;

/**
 * A custom resource discoverer listener
 * 
 * @author Lucas Amador
 * 
 */
public class MyResourceDiscoveredListener implements ResourceDiscoveredListener {

    @Override
    public void discovered(String agentId) {
        System.out.println("resource discovery listener: resources discovered in agent: " + agentId);
    }

}