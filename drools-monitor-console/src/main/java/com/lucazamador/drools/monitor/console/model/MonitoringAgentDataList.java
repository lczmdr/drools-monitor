package com.lucazamador.drools.monitor.console.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "monitoring-agents")
public class MonitoringAgentDataList {

    private List<MonitoringAgentData> monitoringAgents;

    public MonitoringAgentDataList() {
    }

    public MonitoringAgentDataList(List<MonitoringAgentData> monitoringAgents) {
        this.monitoringAgents = monitoringAgents;
    }

    @XmlElement(name = "monitoring-agent")
    public List<MonitoringAgentData> getMonitoringAgents() {
        return monitoringAgents;
    }

    public void setMonitoringAgents(List<MonitoringAgentData> monitoringAgents) {
        this.monitoringAgents = monitoringAgents;
    }

    public boolean isEmpty() {
        return this.monitoringAgents == null || this.monitoringAgents.size() == 0;
    }

}
