package com.lucazamador.drools.monitor.console.model.kbase.metric;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lucazamador.drools.monitor.model.kbase.KnowledgeGlobalMetric;

@XmlRootElement(name = "globalMetric")
public class KnowledgeGlobalMetricData {

    private Long id;
    private String name;
    private String classType;

    public KnowledgeGlobalMetricData() {
    }

    public KnowledgeGlobalMetricData(KnowledgeGlobalMetric globalMetric) {
        this.id = globalMetric.getId();
        this.name = globalMetric.getName();
        this.classType = globalMetric.getClassType();
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

}
