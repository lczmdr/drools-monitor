package com.lucazamador.drools.monitoring.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.model.AbstractMetric;

/**
 * A metric resource scanner used to store the metrics and initialize the metric
 * scanner scheduler
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsResourceScanner {

    private static final Logger logger = LoggerFactory.getLogger(DroolsResourceScanner.class);
    private static final int DEFAULT_INTERVAL = 1000;

    private Buffer metrics = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(1000));
    private int interval;
    private Timer scannerScheduler;
    private DroolsMonitoringScannerTask scannerTask;

    /**
     * Initialize the metric scanner
     */
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
        scannerScheduler.cancel();
    }

    public List<AbstractMetric> getMetricsClone() {
        ArrayList<AbstractMetric> metricsClone = new ArrayList<AbstractMetric>();
        synchronized (metrics) {
            Iterator<AbstractMetric> iterator = metrics.iterator();
            while (iterator.hasNext()) {
                AbstractMetric metric = iterator.next();
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

    public Buffer getMetrics() {
        return metrics;
    }

}
