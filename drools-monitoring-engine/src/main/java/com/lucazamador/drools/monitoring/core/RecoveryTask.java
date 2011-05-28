package com.lucazamador.drools.monitoring.core;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class RecoveryTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(RecoveryTask.class);

    private String jvmId;
    private String address;
    private int port;
    private WhitePages whitePages;
    private RecoveryAgent reconnectionAgent;

    private int recoveryInterval;

    @Override
    public void run() {
        DroolsMBeanConnector connector = new DroolsMBeanConnector();
        connector.setAddress(address);
        connector.setPort(port);
        try {
            connector.connect();
        } catch (DroolsMonitoringException e) {
            logger.debug("reconnection with " + jvmId + " at " + address + ":" + port + " failed. Trying again in "
                    + (recoveryInterval / 1000) + " seconds...");
            return;
        }
        logger.info("reconnected with " + jvmId);
        DroolsMonitoringAgent monitoringAgent = new DroolsMonitoringAgent();
        monitoringAgent.setJvmId(jvmId);
        monitoringAgent.setScanInterval(1000);
        monitoringAgent.setConnector(connector);
        monitoringAgent.setReconnectionAgent(reconnectionAgent);
        try {
            monitoringAgent.start();
        } catch (DroolsMonitoringException e) {
            return;
        }
        whitePages.register(jvmId, monitoringAgent);
        cancel();
    }

    public String getJvmId() {
        return jvmId;
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
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

    public void setWhitePages(WhitePages whitePages) {
        this.whitePages = whitePages;
    }

    public void setReconnectionAgent(RecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
    }

    public RecoveryAgent getReconnectionAgent() {
        return reconnectionAgent;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

}
