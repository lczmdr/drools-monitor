package com.lucazamador.drools.monitor.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.model.Metric;

/**
 * A metric resource scanner used to store the metrics and initialize the metric
 * scanner scheduler
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsResourceScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsResourceScanner.class);
    private static final int DEFAULT_INTERVAL = 1000;
    private static final int DEFAULT_BUFFER_SIZE = 1000;

    private Buffer metrics;
    private int interval;
    private int metricsBufferSize;
    private Timer scannerScheduler;
    private DroolsMonitoringScannerTask scannerTask;

    /**
     * Initialize the metric scanner
     */
    public void start() {
        if (this.metricsBufferSize <= 100) {
            this.metricsBufferSize = DEFAULT_BUFFER_SIZE;
            LOGGER.error("Metrics buffer size less-equal to 100. Using default buffer size: " + DEFAULT_BUFFER_SIZE);
        }
        this.metrics = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(this.metricsBufferSize));
        this.scannerScheduler = new Timer();
        if (scannerTask != null) {
            if (this.interval <= 0) {
                this.interval = DEFAULT_INTERVAL;
                LOGGER.error("Time interval less-equal to zero. Using default scanner interval: " + DEFAULT_INTERVAL);
            }
            scannerScheduler.scheduleAtFixedRate(scannerTask, 0, interval);
        }
    }

    public synchronized void stop() {
        scannerScheduler.cancel();
    }

    @SuppressWarnings("unchecked")
    public List<Metric> getMetricsClone() {
        List<Metric> metricsClone = new ArrayList<Metric>();
        synchronized (metrics) {
            Iterator<Metric> iterator = metrics.iterator();
            while (iterator.hasNext()) {
                Metric metric = iterator.next();
                metricsClone.add(metric);
            }
            metrics.clear();
        }
        return metricsClone;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setMetricsBufferSize(int metricsBufferSize) {
        this.metricsBufferSize = metricsBufferSize;
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
