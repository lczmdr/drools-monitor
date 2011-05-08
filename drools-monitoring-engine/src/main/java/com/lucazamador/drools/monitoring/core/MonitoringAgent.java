package com.lucazamador.drools.monitoring.core;

import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * @author Lucas Amador
 * 
 */
public interface MonitoringAgent {

    public void start() throws DroolsMonitoringException;

    public void stop();

}
