package com.lucazamador.drools.monitor.core.recovery;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.core.MonitoringAgentRegistry;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitor.listener.MonitoringRecoveryListener;

/**
 * An agent to recovery the connection with the JVM.
 * 
 * @author Lucas Amador
 * 
 */
public class MonitoringRecoveryAgent {

    private static final int DEFAULT_RECOVERY_INTERVAL = 10000;

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringRecoveryAgent.class);

    private MonitoringAgentRegistry registry;
    private MonitoringRecoveryListener recoveryListener;
    private Map<String, MonitoringRecoveryTask> recoveryTasks = new HashMap<String, MonitoringRecoveryTask>();

    /**
     * Creates a connection recovery task when the connection is lost.
     * 
     * @param agentId
     *            the monitoring agent id
     * @param address
     *            the monitoring agent IP address
     * @param port
     *            the monitoring agent port.
     */
    public void reconnect(String agentId, String address, int port) {
        MonitoringAgent monitoringAgent = registry.getMonitoringAgent(agentId);
        int recoveryInterval = monitoringAgent.getRecoveryInterval();
        monitoringAgent.stop();
        Timer reconnectionTimer = new Timer();
        int period = recoveryInterval > 0 ? recoveryInterval : DEFAULT_RECOVERY_INTERVAL;
        MonitoringRecoveryTask recoveryTask = new MonitoringRecoveryTask(agentId, address, port, period, registry,
                recoveryListener);
        LOGGER.info("Recovery task created to reconnect with " + agentId + " at " + address + ":" + port);
        recoveryTasks.put(agentId, recoveryTask);
        if (recoveryListener != null) {
            recoveryListener.disconnected(agentId);
        }
        reconnectionTimer.scheduleAtFixedRate(recoveryTask, 0, period);
    }

    /**
     * Remvoes a connection recovery task.
     * 
     * @param agentId
     *            the agent ID of the task to be removed
     */
    public void removeRecoveryTask(String agentId) {
        MonitoringRecoveryTask recoveryTask = recoveryTasks.remove(agentId);
        if (recoveryTask != null) {
            recoveryTask.cancel();
        }
    }

    /**
     * Register a connection recovery listener.
     * 
     * @param recoveryListener
     *            the custom recovery listener to be registered
     */
    public void registerListener(MonitoringRecoveryListener recoveryListener) {
        this.recoveryListener = recoveryListener;
        for (MonitoringRecoveryTask recoveryTask : recoveryTasks.values()) {
            recoveryTask.setRecoveryListener(recoveryListener);
        }
    }

    public MonitoringAgentRegistry getMonitoringAgentRegistry() {
        return registry;
    }

    public void setMonitoringAgentRegistry(MonitoringAgentRegistry registry) {
        this.registry = registry;
    }

}
