package com.lucazamador.drools.monitor.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.model.Metric;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

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

    private ConcurrentHashMap<String, KnowledgeBaseMetric> knowledgeBaseMetrics;
    private ConcurrentHashMap<String, ConcurrentHashMap<Integer, Buffer>> knowledgeSessionsMetrics;

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
        this.knowledgeBaseMetrics = new ConcurrentHashMap<String, KnowledgeBaseMetric>();
        this.knowledgeSessionsMetrics = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Buffer>>();
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

    public void addKnowledgeMetric(Metric metric) {
        if (metric instanceof KnowledgeBaseMetric) {
            addKnowledgeBaseMetric((KnowledgeBaseMetric) metric);
        } else if (metric instanceof KnowledgeSessionMetric) {
            addKnowledgeSessionMetric((KnowledgeSessionMetric) metric);
        }
    }

    private void addKnowledgeBaseMetric(KnowledgeBaseMetric metric) {
        this.knowledgeBaseMetrics.put(metric.getKnowledgeBaseId(), metric);
    }

    @SuppressWarnings("unchecked")
    private void addKnowledgeSessionMetric(KnowledgeSessionMetric metric) {
        Integer knowledgeSessionId = ((KnowledgeSessionMetric) metric).getKnowledgeSessionId();
        String knowledgeBaseId = ((KnowledgeSessionMetric) metric).getKnowledgeBaseId();
        Buffer buffer = null;
        if (this.knowledgeSessionsMetrics.containsKey(knowledgeBaseId)) {
            ConcurrentHashMap<Integer, Buffer> knowledgeSessionMetrics = this.knowledgeSessionsMetrics
                    .get(knowledgeBaseId);
            if (knowledgeSessionMetrics.containsKey(knowledgeSessionId)) {
                buffer = knowledgeSessionMetrics.get(knowledgeSessionId);
            } else {
                buffer = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(this.metricsBufferSize));
                knowledgeSessionMetrics.put(knowledgeSessionId, buffer);
            }
        } else {
            buffer = createKnowledgeBaseAndSessionMetricBuffer(knowledgeSessionId, knowledgeBaseId);
        }
        buffer.add(metric);
        System.err.println("metric added to " + knowledgeBaseId + " ksessionId: " + knowledgeSessionId + " size: "
                + buffer.size());
    }

    private Buffer createKnowledgeBaseAndSessionMetricBuffer(Integer knowledgeSessionId, String knowledgeBaseId) {
        Buffer buffer = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(this.metricsBufferSize));
        ConcurrentHashMap<Integer, Buffer> knowledgeSessionMetric = new ConcurrentHashMap<Integer, Buffer>();
        knowledgeSessionMetric.put(knowledgeSessionId, buffer);
        this.knowledgeSessionsMetrics.put(knowledgeBaseId, knowledgeSessionMetric);
        return buffer;
    }

    public List<KnowledgeBaseMetric> getKnowledgeBaseMetric() {
        List<KnowledgeBaseMetric> knowledgeBaseMetrics = new ArrayList<KnowledgeBaseMetric>();
        synchronized (this.knowledgeBaseMetrics) {
            for (String knowledgeBaseId : this.knowledgeBaseMetrics.keySet()) {
                knowledgeBaseMetrics.add(this.knowledgeBaseMetrics.get(knowledgeBaseId));
            }
        }
        return knowledgeBaseMetrics;
    }

    public KnowledgeBaseMetric getKnowledgeBaseMetric(String knowledgeBaseId) {
        return this.knowledgeBaseMetrics.get(knowledgeBaseId);
    }

    @SuppressWarnings("unchecked")
    public List<KnowledgeSessionMetric> getKnowledgeSessionMetric(String knowledgeBaseId, int knowledgeSessionId,
            int size) {
        List<KnowledgeSessionMetric> responseMetrics = new ArrayList<KnowledgeSessionMetric>();
        if (!this.knowledgeSessionsMetrics.containsKey(knowledgeBaseId)) {
            return null;
        }
        ConcurrentHashMap<Integer, Buffer> knowledgeSessionMetric = this.knowledgeSessionsMetrics.get(knowledgeBaseId);
        if (!knowledgeSessionMetric.containsKey(knowledgeSessionId)) {
            return null;
        }
        Buffer buffer = knowledgeSessionMetric.get(knowledgeSessionId);
        Iterator<KnowledgeSessionMetric> iterator = buffer.iterator();
        int i = 0;
//        for (int j = buffer.size(); j > 0 && i < size; j++, i++) {
//            responseMetrics.add(metric);
//        }
        while (iterator.hasNext() && i < size) {
            KnowledgeSessionMetric metric = iterator.next();
            responseMetrics.add(metric);
            i++;
        }
        return responseMetrics;
    }

    public List<KnowledgeBaseMetric> getKnowledgeBaseMetricsClone() {
        List<KnowledgeBaseMetric> knowledgeBaseMetrics = new ArrayList<KnowledgeBaseMetric>();
        synchronized (this.knowledgeBaseMetrics) {
            synchronized (this.knowledgeBaseMetrics) {
                for (String knowledgeBaseId : this.knowledgeBaseMetrics.keySet()) {
                    knowledgeBaseMetrics.add(this.knowledgeBaseMetrics.get(knowledgeBaseId));
                }
            }
            this.knowledgeBaseMetrics.clear();
        }
        return knowledgeBaseMetrics;
    }

    // TODO: implement
    public List<KnowledgeSessionMetric> getKnowledgeSessionMetricsClone() {
        List<KnowledgeSessionMetric> responseMetrics = new ArrayList<KnowledgeSessionMetric>();
//        for (Integer key : this.knowledgeSessionsMetrics.keySet()) {
//            Buffer buffer = this.knowledgeSessionsMetrics.get(key);
//            Iterator<KnowledgeSessionMetric> iterator = buffer.iterator();
//            while (iterator.hasNext()) {
//                KnowledgeSessionMetric metric = iterator.next();
//                responseMetrics.add(metric);
//            }
//            buffer.clear();
//        }
        return responseMetrics;
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

}
