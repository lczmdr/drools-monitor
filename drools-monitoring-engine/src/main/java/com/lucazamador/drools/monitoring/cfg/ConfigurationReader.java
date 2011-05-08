package com.lucazamador.drools.monitoring.cfg;

import java.io.InputStream;

import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class ConfigurationReader {

    private XStream xstream;
    private String configurationFile;

    public ConfigurationReader() {
        xstream = new XStream();
        xstream.processAnnotations(Configuration.class);
        xstream.processAnnotations(JVMConfiguration.class);
    }

    public Configuration read() throws DroolsMonitoringException {
        if (configurationFile==null) {
            throw new DroolsMonitoringException("configuration file not specified");
        }
        InputStream inputStream = ConfigurationReader.class.getResourceAsStream(configurationFile);
        return (Configuration) xstream.fromXML(inputStream);
    }

    public String getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
    }

}
