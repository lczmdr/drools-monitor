package com.lucazamador.drools.monitor.core.discoverer;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;

/**
 * Timer task to discover registered Drools knowledge resources.
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeDiscovererTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeDiscovererTask.class);

    private KnowledgeResourceDiscoverer discoverer;

    @Override
    public void run() {
        try {
            discoverer.discover();
        } catch (DroolsMonitoringException e) {
            // TODO: handle the connection exception
            LOGGER.error("Error discovering knowledge...");
        }
    }

    public void setKnowledgeResourceDiscoverer(KnowledgeResourceDiscoverer knowledgeResourceDiscoverer) {
        this.discoverer = knowledgeResourceDiscoverer;
    }

}
