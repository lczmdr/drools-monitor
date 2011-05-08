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
public class Configuration {

    private List<JVMConfiguration> connections = new ArrayList<JVMConfiguration>();

    public List<JVMConfiguration> getConnections() {
        return connections;
    }

    public void setConnections(List<JVMConfiguration> connections) {
        this.connections = connections;
    }

}
