package com.lucazamador.drools.monitoring.scanner;

import java.io.IOException;

import com.lucazamador.drools.monitoring.model.AbstractMetric;

/**
 * Interface used by the knowledge scanners.
 * 
 * @author Lucas Amador
 * 
 */
public interface ResourceScanner {

	public AbstractMetric scan() throws IOException;

}
