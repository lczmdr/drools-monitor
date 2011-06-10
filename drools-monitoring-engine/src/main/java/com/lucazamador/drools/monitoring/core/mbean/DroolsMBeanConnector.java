package com.lucazamador.drools.monitoring.core.mbean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMBeanConnector {

    private static final String JMX_URL = "service:jmx:rmi:///jndi/rmi://$address:$port/jmxrmi";
    private static final String DROOLS_MANAGEMENT_RESOURCE_NAMESPACE = "org.drools:type=DroolsManagementAgent";

    private String address;
    private int port;
    private MBeanServerConnection connection;
    private boolean connected;

    public DroolsMBeanConnector() {
    }

    public DroolsMBeanConnector(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Create the connection with the JVM and start the discovery of the
     * registered knowledges resources
     * 
     * @return The connection created with the JVM MBeanServer
     * @throws DroolsMonitoringException
     */
    public void connect() throws DroolsMonitoringException {
        if (connected) {
            throw new IllegalStateException("Drools MBean Agent already connected");
        }
        String connectionURL = JMX_URL.replace("$address", address).replace("$port", String.valueOf(port));
        try {
            JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(connectionURL));
            this.connection = jmxConnector.getMBeanServerConnection();
            this.connected = true;
            if (!isDroolsMonitored()) {
                throw new DroolsMonitoringException("Drools Management MBeans aren't enabled in this application");
            }
        } catch (MalformedURLException e) {
            throw new DroolsMonitoringException("Incorrect JVM connection parameters. adress: " + address + " port: "
                    + port);
        } catch (IOException e) {
            throw new DroolsMonitoringException("Error connecting to JVM. address: " + address + " port: " + port, e);
        }
    }

    /**
     * Checks the existence of a DroolsManagmentAgent instance in the connected
     * JVM
     * 
     * @return
     * @throws DroolsMonitoringException
     */
    private boolean isDroolsMonitored() throws DroolsMonitoringException {
        try {
            Set<ObjectName> queryNames = connection.queryNames(new ObjectName(DROOLS_MANAGEMENT_RESOURCE_NAMESPACE),
                    null);
            return queryNames.size() > 0;
        } catch (IOException e) {
            throw new DroolsMonitoringException("JVM connection error", e);
        } catch (MalformedObjectNameException e) {
            throw new DroolsMonitoringException("JVM connection error", e);
        }
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MBeanServerConnection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connected;
    }

}
