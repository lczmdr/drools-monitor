package com.lucazamador.drools.monitoring.metrics.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Process instance metrics parser used to parse the Drools JMX metrics custom
 * format.
 * 
 * @author Lucas Amador
 * 
 */
public class ProcessInstanceMetricParser {

    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceMetricParser.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss zzzz yyyy");

    private static final String PROCESS_STARTED_PATTERN = "processStarted=(.*)processCompleted";
    private static final String PROCESS_COMPLETED_PATTERN = "processCompleted=(.*)processNodesTriggered";
    private static final String PROCESS_NODE_TRIGGERED_PATTERN = "processNodesTriggered=(.*)";

    private static ProcessInstanceMetricParser instance;

    private final Pattern processStartedPattern;
    private final Pattern processCompletedPattern;
    private final Pattern processNodeTriggeredPattern;

    public ProcessInstanceMetricParser() {
        processStartedPattern = Pattern.compile(PROCESS_STARTED_PATTERN);
        processCompletedPattern = Pattern.compile(PROCESS_COMPLETED_PATTERN);
        processNodeTriggeredPattern = Pattern.compile(PROCESS_NODE_TRIGGERED_PATTERN);
    }

    public Date getProcessStarted(String status) {
        Matcher matcher = processStartedPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            String date = matched.replace("processStarted=", "").replace("processCompleted", "").trim();
            try {
                return dateFormat.parse(date.substring(4));
            } catch (ParseException e) {
                logger.error("Unparseable Process Instance started date: " + date, e);
                return null;
            }
        }
        return null;
    }

    public Date getProcessCompleted(String status) {
        Matcher matcher = processCompletedPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            String date = matched.replace("processCompleted=", "").replace("processNodesTriggered", "").trim();
            try {
                return dateFormat.parse(date.substring(4));
            } catch (ParseException e) {
                logger.error("Unparseable Process Instance completed date: " + date, e);
                return null;
            }
        }
        return null;
    }

    public int getProcessNodeTriggered(String status) {
        Matcher matcher = processNodeTriggeredPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            return Integer.valueOf(matched.replace("processNodesTriggered=", "").trim());
        }
        return 0;
    }

    public static ProcessInstanceMetricParser getInstance() {
        if (instance == null) {
            instance = new ProcessInstanceMetricParser();
        }
        return instance;
    }

}
