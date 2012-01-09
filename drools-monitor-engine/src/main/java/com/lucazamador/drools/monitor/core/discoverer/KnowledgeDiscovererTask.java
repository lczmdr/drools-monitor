package com.lucazamador.drools.monitor.core.discoverer;

import java.util.TimerTask;

import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;

/**
 * Timer task to discover registered Drools knowledge resources.
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeDiscovererTask extends TimerTask {

    private KnowledgeResourceDiscoverer discoverer;

    @Override
    public void run() {
        try {
            discoverer.discover();
        } catch (DroolsMonitoringException e) {
            // TODO: handle the connection exception
            // e.printStackTrace();
        }
    }

    public void setKnowledgeResourceDiscoverer(KnowledgeResourceDiscoverer knowledgeResourceDiscoverer) {
        this.discoverer = knowledgeResourceDiscoverer;
    }

}
