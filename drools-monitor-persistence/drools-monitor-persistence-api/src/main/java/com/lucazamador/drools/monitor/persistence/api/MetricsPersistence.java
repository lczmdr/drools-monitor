package com.lucazamador.drools.monitor.persistence.api;

/**
 * 
 * @author Lucas Amador
 * 
 */
public interface MetricsPersistence {

    public void save(Object object);

    public void stop();

}
