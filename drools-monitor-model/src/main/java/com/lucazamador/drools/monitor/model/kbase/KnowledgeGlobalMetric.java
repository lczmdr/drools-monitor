package com.lucazamador.drools.monitor.model.kbase;

import java.io.Serializable;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeGlobalMetric implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String classType;

    public KnowledgeGlobalMetric() {
    }

    public KnowledgeGlobalMetric(String name, String classType) {
        this.name = name;
        this.classType = classType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

}
