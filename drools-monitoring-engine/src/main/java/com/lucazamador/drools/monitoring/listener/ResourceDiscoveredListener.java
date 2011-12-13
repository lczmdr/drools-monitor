package com.lucazamador.drools.monitoring.listener;

/**
 * Interface used to create custom resource discovered listeners.
 * 
 * @author Lucas Amador
 * 
 */
public interface ResourceDiscoveredListener {

    public void discovered(String agentId);

}
