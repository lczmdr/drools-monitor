package com.lucazamador.drools.monitor.scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitor.metrics.parser.ProcessInstanceMetricParser;
import com.lucazamador.drools.monitor.metrics.parser.ProcessMetricParser;
import com.lucazamador.drools.monitor.metrics.parser.RuleMetricParser;
import com.lucazamador.drools.monitor.model.Metric;
import com.lucazamador.drools.monitor.model.builder.KnowledgeSessionMetricBuilder;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeProcessInstanceMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeProcessMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeRuleMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

/**
 * Knowledge session scanner to update the metrics of the associated knowledge
 * session.
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeSessionScanner extends MetricScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeSessionScanner.class);

    private ProcessMetricParser processParser;
    private ProcessInstanceMetricParser processInstanceParser;
    private RuleMetricParser ruleParser;
    private final String agentId;

    public KnowledgeSessionScanner(String agentId, ObjectName resource, DroolsMBeanConnector connector) {
        this.agentId = agentId;
        this.setResource(resource);
        this.setConnector(connector);
        this.processInstanceParser = ProcessInstanceMetricParser.getInstance();
        this.processParser = ProcessMetricParser.getInstance();
        this.ruleParser = RuleMetricParser.getInstance();
    }

    public Metric scan() throws IOException {
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
        Map<String, String> rulesStats = (HashMap<String, String>) getAttribute("StatsByRule");
        List<KnowledgeRuleMetric> rulesMetrics = new ArrayList<KnowledgeRuleMetric>();
        for (String ruleId : rulesStats.keySet()) {
            String status = rulesStats.get(ruleId);
            rulesMetrics.add(new KnowledgeRuleMetric(ruleId, ruleParser.getFiringTime(status), ruleParser
                    .getActivationsCancelled(status), ruleParser.getActivationsCreated(status), ruleParser
                    .getActivationsFired(status)));
        }

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
                .processInstanceStats(processInstanceMetrics).ruleStats(rulesMetrics).build();

        LOGGER.debug(agentId + " metric: " + metric);
        return metric;
    }
}
