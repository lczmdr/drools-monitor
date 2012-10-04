package com.lucazamador.drools.monitor.scanner;

import java.io.IOException;

import com.lucazamador.drools.monitor.model.Metric;

/**
 * Interface used by the knowledge scanners.
 * 
 * @author Lucas Amador
 * 
 */
public interface ResourceScanner {

    Metric scan() throws IOException;

}
