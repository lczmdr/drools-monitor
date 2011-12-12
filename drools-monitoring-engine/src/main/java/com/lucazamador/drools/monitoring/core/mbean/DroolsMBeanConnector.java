package com.lucazamador.drools.monitoring.core.mbean;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
    private int recoveryInterval;
    private MBeanServerConnection connection;
    private boolean connected;

    public DroolsMBeanConnector() {
    }

    public DroolsMBeanConnector(String address, int port, int recoveryInterval) {
        this.address = address;
        this.port = port;
        this.recoveryInterval = recoveryInterval;
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

    public void connectWithTimeout(long timeout, TimeUnit unit) throws IOException {
        final String connectionURL = JMX_URL.replace("$address", address).replace("$port", String.valueOf(port));
        final BlockingQueue<Object> mailbox = new ArrayBlockingQueue<Object>(1);
        ExecutorService executor = Executors.newSingleThreadExecutor(daemonThreadFactory);
        executor.submit(new Runnable() {
            public void run() {
                try {
                    JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(connectionURL));
                    if (!mailbox.offer(connector)) {
                        connector.close();
                    }
                } catch (Throwable t) {
                    mailbox.offer(t);
                }
            }
        });
        Object result;
        try {
            result = mailbox.poll(timeout, unit);
            if (result == null) {
                if (!mailbox.offer(""))
                    result = mailbox.take();
            }
        } catch (InterruptedException e) {
            throw initCause(new InterruptedIOException(e.getMessage()), e);
        } finally {
            executor.shutdown();
        }
        if (result == null) {
            throw new SocketTimeoutException("Connect timed out: " + address + ":" + port);
        }
        if (result instanceof JMXConnector) {
            this.connection = ((JMXConnector) result).getMBeanServerConnection();
            return;
        }
        try {
            throw (Throwable) result;
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Error e) {
            throw e;
        } catch (Throwable e) {
            // In principle this can't happen but we wrap it anyway
            throw new IOException(e.toString(), e);
        }
    }

    private static <T extends Throwable> T initCause(T wrapper, Throwable wrapped) {
        wrapper.initCause(wrapped);
        return wrapper;
    }

    private static class DaemonThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    }

    private static final ThreadFactory daemonThreadFactory = new DaemonThreadFactory();

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

    public int getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public MBeanServerConnection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connected;
    }

}
