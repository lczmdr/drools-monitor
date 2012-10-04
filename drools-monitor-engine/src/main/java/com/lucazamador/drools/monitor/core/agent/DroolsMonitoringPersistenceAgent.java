package com.lucazamador.drools.monitor.core.agent;

import com.lucazamador.drools.monitor.core.DroolsMonitoringScannerTask;
import com.lucazamador.drools.monitor.core.DroolsResourceScanner;
import com.lucazamador.drools.monitor.core.discoverer.ResourceDiscoverer;
import com.lucazamador.drools.monitor.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitor.persistence.MetricsPersistenceScheduler;
import com.lucazamador.drools.monitor.persistence.MetricsPersistenceSchedulerTask;
import com.lucazamador.drools.monitor.persistence.api.MetricsPersistence;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringPersistenceAgent extends DroolsMonitoringAgentBase {

    private int persistenceInterval;
    private MetricsPersistence persistence;
    private MetricsPersistenceSchedulerTask persistenceSchedulerTask;
    private MetricsPersistenceScheduler persistenceScheduler;

    public void start() {
        ResourceDiscoverer resourceDiscoverer = new ResourceDiscoverer();
        resourceDiscoverer.setAgentId(getId());
        resourceDiscoverer.setResourceDiscoveredListener(getResourceDiscoveredListener());
        resourceDiscoverer.setConnector(getConnector());
        resourceDiscoverer.discover();
        setResourceDiscoverer(resourceDiscoverer);

        DroolsResourceScanner scanner = new DroolsResourceScanner();
        scanner.setInterval(getScanInterval());
        setScanner(scanner);

        DroolsMonitoringScannerTask scannerTask = new DroolsMonitoringScannerTask();
        scannerTask.setResourceDiscoverer(resourceDiscoverer);
        scannerTask.setScanner(scanner);
        scannerTask.setReconnectionAgent(getReconnectionAgent());
        scannerTask.setOnConnectionLost(new ConnectionLost() {
            @Override
            public void stop() {
                getResourceDiscoverer().stop();
                persistenceScheduler.stop();
            }
        });

        for (DroolsMonitoringListener listener : getListeners()) {
            scannerTask.registerListener(listener);
        }
        setScannerTask(scannerTask);
        scanner.setScannerTask(scannerTask);

        persistenceSchedulerTask = new MetricsPersistenceSchedulerTask();
        persistenceSchedulerTask.setPersistence(persistence);
        persistenceSchedulerTask.setScanner(scanner);

        persistenceScheduler = new MetricsPersistenceScheduler();
        persistenceScheduler.setTask(getPersistenceSchedulerTask());
        persistenceScheduler.setPeriod(persistenceInterval);
        persistenceScheduler.start();

        scanner.start();
        setStarted(true);
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
