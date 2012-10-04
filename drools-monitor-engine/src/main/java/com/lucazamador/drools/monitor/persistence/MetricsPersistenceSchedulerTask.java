package com.lucazamador.drools.monitor.persistence;

import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.core.DroolsResourceScanner;
import com.lucazamador.drools.monitor.model.Metric;
import com.lucazamador.drools.monitor.persistence.api.MetricsPersistence;

/**
 * Scheduler task that persist all the metrics retrieved.
 * 
 * @author Lucas Amador
 * 
 */
public class MetricsPersistenceSchedulerTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsPersistenceSchedulerTask.class);

    private MetricsPersistence persistence;
    private DroolsResourceScanner scanner;

    public void setPersistence(MetricsPersistence persistence) {
        this.persistence = persistence;
    }

    public void setScanner(DroolsResourceScanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        if (persistence == null) {
            LOGGER.error("MetricsPersistence must be provided");
            return;
        }
        if (scanner == null) {
            LOGGER.error("DroolsMBeanScanner must be provided");
            return;
        }
        List<Metric> metrics = scanner.getMetricsClone();
        LOGGER.info("Starting metrics persistence task");
        long startTime = System.currentTimeMillis();
        for (Metric metric : metrics) {
            persistence.save(metric);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("Metrics persistence task done. Elapsed time: " + (endTime - startTime) + " ms.");
    }

    public void stop() {
        persistence.stop();
    }

}
