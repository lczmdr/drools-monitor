package com.lucazamador.drools.monitor.listener;

import java.util.EventListener;

/**
 * Interface used to create custom resource discovered listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface ResourceDiscoveredListener extends EventListener {

    public void discovered(String agentId);

}
