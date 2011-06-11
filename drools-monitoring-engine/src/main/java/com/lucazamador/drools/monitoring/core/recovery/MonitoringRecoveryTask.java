package com.lucazamador.drools.monitoring.core.recovery;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.core.MonitoringAgentRegistry;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class MonitoringRecoveryTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(MonitoringRecoveryTask.class);

    private String agentId;
    private String address;
    private int port;
    private int recoveryInterval;
    private MonitoringAgentRegistry registry;
    private MonitoringRecoveryListener recoveryListener;

    @Override
    public void run() {
        DroolsMBeanConnector connector = new DroolsMBeanConnector();
        connector.setAddress(address);
        connector.setPort(port);
        try {
            connector.connect();
        } catch (DroolsMonitoringException e) {
            logger.debug("reconnection with " + agentId + " at " + address + ":" + port + " failed. Trying again in "
                    + (recoveryInterval / 1000) + " seconds...");
            return;
        }
        logger.info("reconnected with " + agentId);
        if (recoveryListener != null) {
            recoveryListener.reconnected(agentId);
        }
        DroolsMonitoringAgent monitoringAgent = registry.getMonitoringAgent(agentId);
        monitoringAgent.setConnector(connector);
        try {
            monitoringAgent.start();
        } catch (DroolsMonitoringException e) {
            e.printStackTrace();
            return;
        }
        cancel();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
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

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public void setMonitoringAgentRegistry(MonitoringAgentRegistry registry) {
        this.registry = registry;
    }

    public void setRecoveryListener(MonitoringRecoveryListener recoveryListener) {
        this.recoveryListener = recoveryListener;
    }

}
