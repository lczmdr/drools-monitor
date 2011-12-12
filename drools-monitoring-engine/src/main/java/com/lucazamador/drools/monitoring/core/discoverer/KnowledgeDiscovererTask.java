package com.lucazamador.drools.monitoring.core.discoverer;

import java.util.TimerTask;

import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeDiscovererTask extends TimerTask {

    private KnowledgeDiscoverer discoverer;

    @Override
    public void run() {
        try {
            discoverer.discover();
        } catch (DroolsMonitoringException e) {
            // e.printStackTrace();
            // TODO: discovering
        }
    }

    public void setKnowledgeResourceDiscoverer(KnowledgeDiscoverer knowledgeResourceDiscoverer) {
        this.discoverer = knowledgeResourceDiscoverer;
    }

}
