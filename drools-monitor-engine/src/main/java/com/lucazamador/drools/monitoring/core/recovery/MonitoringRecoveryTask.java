package com.lucazamador.drools.monitoring.core.recovery;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.MonitoringAgentRegistry;
import com.lucazamador.drools.monitoring.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;

/**
 * Timer task to reconnect with the remote JVM.
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

    public MonitoringRecoveryTask(String agentId, String address, int port, int recoveryInterval,
            MonitoringAgentRegistry registry, MonitoringRecoveryListener recoveryListener) {
        this.agentId = agentId;
        this.address = address;
        this.port = port;
        this.recoveryInterval = recoveryInterval;
        this.registry = registry;
        this.recoveryListener = recoveryListener;
    }

    /**
     * Try to reconnect with the JVM and creates a new monitoring agent in case
     * of a successful connection.
     */
    @Override
    public void run() {
        DroolsMBeanConnector connector = new DroolsMBeanConnector(address, port, recoveryInterval);
        try {
            connector.connect();
            logger.info("reconnected with " + agentId + " at " + address + ":" + port);
            MonitoringAgent monitoringAgent = registry.getMonitoringAgent(agentId);
            monitoringAgent.setConnector(connector);
            monitoringAgent.start();
            if (recoveryListener != null) {
                recoveryListener.reconnected(agentId);
            }
            cancel();
        } catch (DroolsMonitoringException e) {
            logger.debug("reconnection with " + agentId + " at " + address + ":" + port + " failed. Trying again in "
                    + (recoveryInterval / 1000) + " seconds...");
        }
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
