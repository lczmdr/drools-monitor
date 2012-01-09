package com.lucazamador.drools.monitoring.persistence;

import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.DroolsResourceScanner;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.persistence.api.MetricsPersistence;

/**
 * Scheduler task that persist all the metrics retrieved.
 * 
 * @author Lucas Amador
 * 
 */
public class MetricsPersistenceSchedulerTask extends TimerTask {

    private static final Logger logger = LoggerFactory.getLogger(MetricsPersistenceSchedulerTask.class);

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
            logger.error("MetricsPersistence must be provided");
            return;
        }
        if (scanner == null) {
            logger.error("DroolsMBeanScanner must be provided");
            return;
        }
        List<AbstractMetric> metrics = scanner.getMetricsClone();
        logger.info("Starting metrics persistence task");
        long startTime = System.currentTimeMillis();
        for (AbstractMetric metric : metrics) {
            persistence.save(metric);
        }
        long endTime = System.currentTimeMillis();
        logger.info("Metrics persistence task done. Elapsed time: " + (endTime - startTime) + " ms.");
    }

    public void stop() {
        persistence.stop();
    }

}
