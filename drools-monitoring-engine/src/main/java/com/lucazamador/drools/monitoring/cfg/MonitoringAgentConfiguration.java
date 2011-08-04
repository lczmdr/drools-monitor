package com.lucazamador.drools.monitoring.cfg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author Lucas Amador
 * 
 */
@XStreamAlias(value = "agent")
public class MonitoringAgentConfiguration {

    @XStreamAsAttribute
    private String id;
    @XStreamAsAttribute
    private String address;
    @XStreamAsAttribute
    private int port;
    @XStreamAsAttribute
    private int scanInterval = 1000;
    @XStreamAsAttribute
    private int recoveryInterval = 10000;
    private PersistenceConfiguration persistence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public int getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public PersistenceConfiguration getPersistence() {
        return persistence;
    }

    public void setPersistence(PersistenceConfiguration persistence) {
        this.persistence = persistence;
    }

    public boolean persistenceEnabled() {
        return persistence != null && persistence.getClassName().trim().length() > 0;
    }

    @Override
    public String toString() {
        return "id=" + id + " address=" + address + " port=" + port + " scanInterval=" + scanInterval
                + " recoveryInterval=" + recoveryInterval;
    }

}
