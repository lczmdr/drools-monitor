package com.lucazamador.drools.monitor.persistence.api;

/**
 * 
 * @author Lucas Amador
 * 
 */
public interface MetricsPersistence {

    void save(Object object);

    void stop();

}
