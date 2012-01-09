package com.lucazamador.drools.monitoring.cfg;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Lucas Amador
 * 
 */
@XStreamAlias(value = "configuration")
public class MonitoringConfiguration {

    private List<MonitoringAgentConfiguration> connections = new ArrayList<MonitoringAgentConfiguration>();

    public List<MonitoringAgentConfiguration> getConnections() {
        return connections;
    }

    public void setConnections(List<MonitoringAgentConfiguration> connections) {
        this.connections = connections;
    }

}
