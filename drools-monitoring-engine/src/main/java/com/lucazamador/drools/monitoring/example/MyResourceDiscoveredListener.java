package com.lucazamador.drools.monitoring.example;

import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;

public class MyResourceDiscoveredListener implements ResourceDiscoveredListener {

    @Override
    public void discovered() {
        System.out.println("resources discovered!");
    }

}