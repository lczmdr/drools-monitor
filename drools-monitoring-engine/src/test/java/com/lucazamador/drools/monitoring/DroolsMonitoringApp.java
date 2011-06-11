package com.lucazamador.drools.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.cfg.MonitoringConfiguration;
import com.lucazamador.drools.monitoring.cfg.MonitoringConfigurationReader;
import com.lucazamador.drools.monitoring.core.DroolsMonitoring;
import com.lucazamador.drools.monitoring.core.DroolsMonitoringFactory;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;

/**
 * 
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringApp {

    private static Logger logger = LoggerFactory.getLogger(DroolsMonitoringApp.class);

    public static void main(String[] args) throws DroolsMonitoringException {

        MonitoringConfigurationReader configurationReader = DroolsMonitoringFactory
                .newMonitoringConfigurationReader("/configuration.xml");
        MonitoringConfiguration configuration = configurationReader.read();

        final DroolsMonitoring monitor = DroolsMonitoringFactory.newDroolsMonitoring(configuration);
        DroolsMonitoringListener listener = new MyDroolsMonitoringListener();
        MonitoringRecoveryListener recoveryListener = new MyMonitoringRecoveryListener();
        monitor.registerListener(listener);
        monitor.registerRecoveryAgentListener(recoveryListener);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    monitor.stop();
                    logger.info("drools monitoring stopped");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        monitor.start();

        DroolsMonitoringListener listener2 = new MyDroolsMonitoringListener();
        monitor.registerListener(listener2);

        logger.info("drools monitoring started... (ctrl-c to stop it)");

    }

}
