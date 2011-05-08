package com.lucazamador.drools.monitoring.model.kbase;

import java.io.Serializable;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String knowledgeBaseId;
    private String jvmName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

}
