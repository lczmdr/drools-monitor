package com.lucazamador.drools.monitoring.scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.metrics.parser.ProcessInstanceMetricParser;
import com.lucazamador.drools.monitoring.metrics.parser.ProcessMetricParser;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.model.builder.KnowledgeSessionMetricBuilder;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeProcessInstanceMetric;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeProcessMetric;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionMetric;

/**
 * Scanner to update the metrics of the associated KnowledgeSession
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeSessionScanner extends MetricScanner {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeSessionScanner.class);
    private ProcessMetricParser processParser;
    private ProcessInstanceMetricParser processInstanceParser;
    private final String agentId;

    public KnowledgeSessionScanner(String agentId, ObjectName resource, DroolsMBeanConnector connector) {
        this.agentId = agentId;
        this.resource = resource;
        this.connector = connector;
        this.processInstanceParser = ProcessInstanceMetricParser.getInstance();
        this.processParser = ProcessMetricParser.getInstance();
    }

    public AbstractMetric scan() throws IOException {
        double averageFiringTime = (Double) getAttribute("AverageFiringTime");
        String knowledgeBaseId = (String) getAttribute("KnowledgeBaseId");
        Integer knowledgeSessionId = (Integer) getAttribute("KnowledgeSessionId");

        Date lastReset = (Date) getAttribute("LastReset");

        Long totalActivationsCancelled = (Long) getAttribute("TotalActivationsCancelled");
        Long totalActivationsCreated = (Long) getAttribute("TotalActivationsCreated");
        Long totalActivationsFired = (Long) getAttribute("TotalActivationsFired");

        Long totalFactCount = (Long) getAttribute("TotalFactCount");
        Long totalFiringTime = (Long) getAttribute("TotalFiringTime");
        Long totalProcessInstancesCompleted = (Long) getAttribute("TotalProcessInstancesCompleted");
        Long totalProcessInstancesStarted = (Long) getAttribute("TotalProcessInstancesStarted");

        @SuppressWarnings("unchecked")
        Map<String, String> processStats = (HashMap<String, String>) getAttribute("StatsByProcess");
        List<KnowledgeProcessMetric> processMetrics = new ArrayList<KnowledgeProcessMetric>();
        for (String processId : processStats.keySet()) {
            String status = processStats.get(processId);
            processMetrics.add(new KnowledgeProcessMetric(processId, processParser.getProcessCompleted(status),
                    processParser.getProcessCompleted(status), processParser.getProcessNodeTriggered(status)));
        }

        @SuppressWarnings("unchecked")
        Map<Long, String> processInstanceStats = (HashMap<Long, String>) getAttribute("StatsByProcessInstance");
        List<KnowledgeProcessInstanceMetric> processInstanceMetrics = new ArrayList<KnowledgeProcessInstanceMetric>();
        for (Long processInstanceId : processInstanceStats.keySet()) {
            String status = processInstanceStats.get(processInstanceId);
            processInstanceMetrics.add(new KnowledgeProcessInstanceMetric(processInstanceId, processInstanceParser
                    .getProcessCompleted(status), processInstanceParser.getProcessCompleted(status),
                    processInstanceParser.getProcessNodeTriggered(status)));
        }

        KnowledgeSessionMetric metric = new KnowledgeSessionMetricBuilder(agentId, knowledgeBaseId)
                .averageFiringTime(averageFiringTime).knowledgeSessionId(knowledgeSessionId).lastReset(lastReset)
                .totalActivationsCancelled(totalActivationsCancelled).totalActivationsCreated(totalActivationsCreated)
                .totalActivationsFired(totalActivationsFired).totalFactCount(totalFactCount)
                .totalFiringTime(totalFiringTime).totalProcessInstancesCompleted(totalProcessInstancesCompleted)
                .totalProcessInstancesStarted(totalProcessInstancesStarted).processStats(processMetrics)
                .processInstanceStats(processInstanceMetrics).build();

        logger.info(agentId + " metric: " + metric);
        return metric;
    }

}
