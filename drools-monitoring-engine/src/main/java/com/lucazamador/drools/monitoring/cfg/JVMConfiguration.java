package com.lucazamador.drools.monitoring.cfg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author Lucas Amador
 * 
 */
@XStreamAlias(value = "jvm")
public class JVMConfiguration {

    @XStreamAsAttribute
    private String id;
    @XStreamAsAttribute
    private String address;
    @XStreamAsAttribute
    private int port;
    @XStreamAsAttribute
    private int scanInterval = 1000;
    @XStreamAsAttribute
    private int persistenceInterval = 10000;

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

    public int getPersistenceInterval() {
        return persistenceInterval;
    }

    public void setPersistenceInterval(int persistenceInterval) {
        this.persistenceInterval = persistenceInterval;
    }

    @Override
    public String toString() {
        return "id=" + id + " address=" + address + " port=" + port + " scanInterval=" + scanInterval
                + " persistenceInterval=" + persistenceInterval;
    }

}
