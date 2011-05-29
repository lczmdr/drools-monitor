package com.lucazamador.drools.monitoring.core.recovery;

import java.util.List;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.core.WhitePages;
import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class MonitoringRecoveryAgent {

    private Logger logger = LoggerFactory.getLogger(MonitoringRecoveryAgent.class);

    private static final int DEFAULT_RECOVERY_INTERVAL = 10000;
    private WhitePages whitePages;

    public void reconnect(String jvmId, DroolsMBeanConnector connector) {
        DroolsMonitoringAgent monitoringAgent = whitePages.getMonitoringAgent(jvmId);
        int recoveryInterval = monitoringAgent.getRecoveryInterval();
        int scanInterval = monitoringAgent.getScanInterval();
        List<DroolsMonitoringListener> listeners = monitoringAgent.getListeners();
        whitePages.unregister(jvmId);
        logger.info("Recovery agent created to reconnect with " + jvmId + " at " + connector.getAddress() + ":"
                + connector.getPort());
        Timer reconnectionTimer = new Timer();
        MonitoringRecoveryTask recoveryTask = new MonitoringRecoveryTask();
        recoveryTask.setJvmId(jvmId);
        recoveryTask.setAddress(connector.getAddress());
        recoveryTask.setPort(connector.getPort());
        recoveryTask.setRecoveryInterval(recoveryInterval);
        recoveryTask.setScanInterval(scanInterval);
        recoveryTask.setListeners(listeners);
        recoveryTask.setWhitePages(whitePages);
        recoveryTask.setReconnectionAgent(this);
        reconnectionTimer.scheduleAtFixedRate(recoveryTask, 0, recoveryInterval > 0 ? recoveryInterval
                : DEFAULT_RECOVERY_INTERVAL);
    }

    public WhitePages getWhitePages() {
        return whitePages;
    }

    public void setWhitePages(WhitePages whitePages) {
        this.whitePages = whitePages;
    }

}
