package com.lucazamador.drools.monitoring.metrics.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Process metrics parser used to parse the Drools JMX metrics custom format.
 * 
 * @author Lucas Amador
 * 
 */
public class ProcessMetricParser {

    private static ProcessMetricParser instance;

    private static final String PROCESS_STARTED_PATTERN = "processInstancesStarted=(.*)processInstancesCompleted";
    private static final String PROCESS_COMPLETED_PATTERN = "processInstancesCompleted=(.*)processNodesTriggered";
    private static final String PROCESS_NODE_TRIGGERED_PATTERN = "processNodesTriggered=(.*)";

    private final Pattern processStartedPattern;
    private final Pattern processCompletedPattern;
    private final Pattern processNodeTriggeredPattern;

    public ProcessMetricParser() {
        processStartedPattern = Pattern.compile(PROCESS_STARTED_PATTERN);
        processCompletedPattern = Pattern.compile(PROCESS_COMPLETED_PATTERN);
        processNodeTriggeredPattern = Pattern.compile(PROCESS_NODE_TRIGGERED_PATTERN);
    }

    public long getProcessStarted(String status) {
        Matcher matcher = processStartedPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            String count = matched.replace("processInstancesStarted=", "").replace("processInstancesCompleted", "")
                    .trim();
            return Long.valueOf(count);
        }
        return 0;
    }

    public long getProcessCompleted(String status) {
        Matcher matcher = processCompletedPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            String count = matched.replace("processInstancesCompleted=", "").replace("processNodesTriggered", "")
                    .trim();
            return Long.valueOf(count);
        }
        return 0;
    }

    public long getProcessNodeTriggered(String status) {
        Matcher matcher = processNodeTriggeredPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            return Long.valueOf(matched.replace("processNodesTriggered=", "").trim());
        }
        return 0;
    }

    public static ProcessMetricParser getInstance() {
        if (instance == null) {
            instance = new ProcessMetricParser();
        }
        return instance;
    }

}
