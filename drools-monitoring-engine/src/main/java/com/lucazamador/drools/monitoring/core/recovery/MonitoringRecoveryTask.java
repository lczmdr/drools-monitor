package com.lucazamador.drools.monitoring.core.recovery;

import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.core.WhitePages;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class MonitoringRecoveryTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(MonitoringRecoveryTask.class);

    private String jvmId;
    private String address;
    private int port;
    private int scanInterval;
    private int recoveryInterval;
    private WhitePages whitePages;
    private MonitoringRecoveryAgent reconnectionAgent;
    private List<DroolsMonitoringListener> listeners;

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
        monitoringAgent.setScanInterval(scanInterval);
        monitoringAgent.setRecoveryInterval(recoveryInterval);
        monitoringAgent.setConnector(connector);
        monitoringAgent.setReconnectionAgent(reconnectionAgent);
        monitoringAgent.setListeners(listeners);
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

    public void setReconnectionAgent(MonitoringRecoveryAgent reconnectionAgent) {
        this.reconnectionAgent = reconnectionAgent;
    }

    public MonitoringRecoveryAgent getReconnectionAgent() {
        return reconnectionAgent;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public void setListeners(List<DroolsMonitoringListener> listeners) {
        this.listeners = listeners;
    }

}
