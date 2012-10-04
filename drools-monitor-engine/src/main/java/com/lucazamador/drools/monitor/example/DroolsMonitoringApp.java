package com.lucazamador.drools.monitor.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;

/**
 * An drools monitoring example using a configuration file and customs
 * listeners.
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringApp {

//    private static Logger logger = LoggerFactory.getLogger(DroolsMonitoringApp.class);

    public static void main(String[] args) throws DroolsMonitoringException {

        new ClassPathXmlApplicationContext("/applicationContext.xml");

//        MonitoringConfigurationReader configurationReader = DroolsMonitoringFactory
//                .newMonitoringConfigurationReader("/configuration.xml");
//        MonitoringConfiguration configuration = configurationReader.read();
//
//        MyResourceDiscoveredListener discoveredListener = new MyResourceDiscoveredListener();
//        MyMonitoringRecoveryListener recoveryListener = new MyMonitoringRecoveryListener();
//        final DroolsMonitoring monitor = DroolsMonitoringFactory.newDroolsMonitoring(configuration, recoveryListener,
//                discoveredListener);
//        DroolsMonitoringListener listener = new MyDroolsMonitoringListener();
//        monitor.registerListener(listener);
//
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                try {
//                    monitor.stop();
//                    logger.info("drools monitoring stopped");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        monitor.start();
//
//        logger.info("drools monitoring started... (ctrl-c to stop it)");

    }

}
