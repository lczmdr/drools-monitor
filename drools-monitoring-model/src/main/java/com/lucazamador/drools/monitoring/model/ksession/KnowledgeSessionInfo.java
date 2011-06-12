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
    private String agentId;

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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

}
