package com.lucazamador.drools.monitoring.core.agent;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringScannerTask;
import com.lucazamador.drools.monitoring.core.DroolsResourceScanner;
import com.lucazamador.drools.monitoring.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.persistence.MetricsPersistenceScheduler;
import com.lucazamador.drools.monitoring.persistence.MetricsPersistenceSchedulerTask;
import com.lucazamador.drools.monitoring.persistence.api.MetricsPersistence;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsPersistenceMonitoringAgent extends CommonMonitoringAgent {

    private int persistenceInterval;
    private MetricsPersistence persistence;
    private MetricsPersistenceSchedulerTask persistenceSchedulerTask;
    private MetricsPersistenceScheduler persistenceScheduler;

    public void start() {
        resourceDiscoverer = new ResourceDiscoverer();
        resourceDiscoverer.setAgentId(id);
        resourceDiscoverer.setResourceDiscoveredListener(discoveredListener);
        resourceDiscoverer.setConnector(connector);
        resourceDiscoverer.discover();

        scanner = new DroolsResourceScanner();
        scanner.setInterval(scanInterval);

        scannerTask = new DroolsMonitoringScannerTask();
        scannerTask.setResourceDiscoverer(resourceDiscoverer);
        scannerTask.setScanner(scanner);
        scannerTask.setReconnectionAgent(reconnectionAgent);
        for (DroolsMonitoringListener listener : listeners) {
            scannerTask.registerListener(listener);
        }
        scanner.setScannerTask(scannerTask);

        persistenceSchedulerTask = new MetricsPersistenceSchedulerTask();
        persistenceSchedulerTask.setPersistence(persistence);
        persistenceSchedulerTask.setScanner(scanner);

        persistenceScheduler = new MetricsPersistenceScheduler();
        persistenceScheduler.setTask(getPersistenceSchedulerTask());
        persistenceScheduler.setPeriod(persistenceInterval);
        persistenceScheduler.start();

        scanner.start();
        started = true;
    }

    public synchronized void stop() {
        if (scanner != null) {
            scanner.stop();
        }
        reconnectionAgent.removeRecoveryTask(id);
    }

    public int getPersistenceInterval() {
        return persistenceInterval;
    }

    public void setPersistenceInterval(int persistenceInterval) {
        this.persistenceInterval = persistenceInterval;
    }

    public MetricsPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(MetricsPersistence persistence) {
        this.persistence = persistence;
    }

    public MetricsPersistenceSchedulerTask getPersistenceSchedulerTask() {
        return persistenceSchedulerTask;
    }

    public void setPersistenceSchedulerTask(MetricsPersistenceSchedulerTask persistenceSchedulerTask) {
        this.persistenceSchedulerTask = persistenceSchedulerTask;
    }

    public MetricsPersistenceScheduler getPersistenceScheduler() {
        return persistenceScheduler;
    }

    public void setPersistenceScheduler(MetricsPersistenceScheduler persistenceScheduler) {
        this.persistenceScheduler = persistenceScheduler;
    }

}
