package com.lucazamador.drools.monitoring.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.model.AbstractMetric;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsResourceScanner {

    private static final Logger logger = LoggerFactory.getLogger(DroolsResourceScanner.class);
    private static final int DEFAULT_INTERVAL = 1000;

    private List<AbstractMetric> metrics = Collections.synchronizedList(new ArrayList<AbstractMetric>());
    private int interval;
    private Timer scannerScheduler;
    private DroolsMonitoringScannerTask scannerTask;

    public void start() {
        this.scannerScheduler = new Timer();
        if (scannerTask != null) {
            if (interval <= 0) {
                logger.error("Time interval wasn't provided or less-equal to zero. Using default scanner interval: "
                        + DEFAULT_INTERVAL);
                interval = DEFAULT_INTERVAL;
            }
            scannerScheduler.scheduleAtFixedRate(scannerTask, 0, interval);
        }
    }

    public synchronized void stop() {
        if (scannerTask != null) {
            scannerScheduler.schedule(scannerTask, 0);
        }
        scannerScheduler.cancel();
    }

    public List<AbstractMetric> getMetricsClone() {
        ArrayList<AbstractMetric> metricsClone = new ArrayList<AbstractMetric>();
        synchronized (metrics) {
            for (AbstractMetric metric : metrics) {
                metricsClone.add(metric);
            }
            metrics.clear();
        }
        return metricsClone;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setScannerTask(DroolsMonitoringScannerTask scannerTask) {
        this.scannerTask = scannerTask;
    }

    public DroolsMonitoringScannerTask getScannerTask() {
        return scannerTask;
    }

    public void setMetrics(List<AbstractMetric> metrics) {
        this.metrics = metrics;
    }

    public List<AbstractMetric> getMetrics() {
        return metrics;
    }

}
