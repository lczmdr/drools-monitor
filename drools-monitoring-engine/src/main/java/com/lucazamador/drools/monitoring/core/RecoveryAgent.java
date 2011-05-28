package com.lucazamador.drools.monitoring.core;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;

public class RecoveryAgent {

    private Logger logger = LoggerFactory.getLogger(RecoveryAgent.class);

    private static final int DEFAULT_RECOVERY_INTERVAL = 10000;
    private WhitePages whitePages;

    public void reconnect(String jvmId, DroolsMBeanConnector connector) {
        int recoveryInterval = whitePages.getRecoveryInterval(jvmId);
        whitePages.unregister(jvmId);
        logger.info("Recovery agent created to reconnect with " + jvmId + " at " + connector.getAddress() + ":"
                + connector.getPort());
        Timer reconnectionTimer = new Timer();
        RecoveryTask recoveryTask = new RecoveryTask();
        recoveryTask.setJvmId(jvmId);
        recoveryTask.setAddress(connector.getAddress());
        recoveryTask.setPort(connector.getPort());
        recoveryTask.setRecoveryInterval(recoveryInterval);
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
