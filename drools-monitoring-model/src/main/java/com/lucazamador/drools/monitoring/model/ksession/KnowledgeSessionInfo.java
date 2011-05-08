package com.lucazamador.drools.monitoring.model.ksession;

import java.io.Serializable;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeSessionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String knowledgeBaseId;
    private String jvmName;

    private int knowledgeSessionId;

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

    public void setKnowledgeSessionId(int knowledgeSessionId) {
        this.knowledgeSessionId = knowledgeSessionId;
    }

    public int getKnowledgeSessionId() {
        return knowledgeSessionId;
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

}
