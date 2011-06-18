package com.lucazamador.drools.monitoring.eclipse.model;

public enum MonitoringMetric {

    AVERATE_FIRING_TIME("Average Firing Time"),
    LAST_RESET("Last Reset"),
    TOTAL_ACTIVATIONS_CANCELED("Total Activations Canceled"), 
    TOTAL_ACTIVATIONS_CREATED("Total Activations Created"),
    TOTAL_ACTIVATIONS_FIRED("Total Activations Fired"),
    TOTAL_FACT_COUNT("Total Fact Count"),
    TOTAL_FIRING_TIME("Total Firing Time"),
    TOTAL_PROCESS_INSTANCES_COMPLETED("Total Process Instances Completed"),
    TOTAL_PROCESS_INSTANCES_STARTED("Total Process Instances Started");

    private final String description;

    MonitoringMetric(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
