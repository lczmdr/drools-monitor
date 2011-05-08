package com.lucazamador.drools.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.cfg.Configuration;
import com.lucazamador.drools.monitoring.cfg.ConfigurationReader;
import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgentConfigurer;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringApp {

    private static Logger logger = LoggerFactory.getLogger(DroolsMonitoringApp.class);

    public static void main(String[] args) throws DroolsMonitoringException {

        ConfigurationReader configurationReader = new ConfigurationReader();
        configurationReader.setConfigurationFile("/configuration.xml");
        Configuration configuration = configurationReader.read();

        final DroolsMonitoringAgentConfigurer configurer = new DroolsMonitoringAgentConfigurer();
        configurer.setConfiguration(configuration);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    configurer.stop();
                    logger.info("drools monitoring stopped");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        configurer.start();
        logger.info("drools monitoring started... (ctrl-c to stop it)");

    }

}
