package com.lucazamador.drools.monitor.metrics.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule metrics parser used to parse the Drools JMX metrics custom format.
 * 
 * @author Lucas Amador
 * 
 */
public class RuleMetricParser {

    private static RuleMetricParser instance;

    private static final String ACTIVATIONS_CREATED_PATTERN = "activationsCreated=(.*)activationsCancelled";
    private static final String ACTIVATIONS_CANCELLED_PATTERN = "activationsCancelled=(.*)activationsFired";
    private static final String ACTIVATIONS_FIRED_PATTERN = "activationsFired=(.*)firingTime";
    private static final String FIRING_TIME_PATTERN = "firingTime=(.*)";

    private final Pattern activationsCreatedPattern;
    private final Pattern activationsCancelledPattern;
    private final Pattern activationsFiredPattern;
    private final Pattern firingTimePattern;

    public RuleMetricParser() {
        activationsCreatedPattern = Pattern.compile(ACTIVATIONS_CREATED_PATTERN);
        activationsCancelledPattern = Pattern.compile(ACTIVATIONS_CANCELLED_PATTERN);
        activationsFiredPattern = Pattern.compile(ACTIVATIONS_FIRED_PATTERN);
        firingTimePattern = Pattern.compile(FIRING_TIME_PATTERN);
    }

    public long getActivationsCreated(String status) {
        Matcher matcher = activationsCreatedPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            String count = matched.replace("activationsCreated=", "").replace("activationsCancelled", "").trim();
            return Long.valueOf(count);
        }
        return 0;
    }

    public long getActivationsCancelled(String status) {
        Matcher matcher = activationsCancelledPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            String count = matched.replace("activationsCancelled=", "").replace("activationsFired", "").trim();
            return Long.valueOf(count);
        }
        return 0;
    }

    public long getActivationsFired(String status) {
        Matcher matcher = activationsFiredPattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            String count = matched.replace("activationsFired=", "").replace("firingTime", "").trim();
            return Long.valueOf(count);
        }
        return 0;
    }

    public double getFiringTime(String status) {
        Matcher matcher = firingTimePattern.matcher(status);
        if (matcher.find()) {
            String matched = matcher.group();
            return Double.valueOf(matched.replace("firingTime=", "").replace("ms", "").trim());
        }
        return 0;
    }

    public static RuleMetricParser getInstance() {
        if (instance == null) {
            instance = new RuleMetricParser();
        }
        return instance;
    }

}
