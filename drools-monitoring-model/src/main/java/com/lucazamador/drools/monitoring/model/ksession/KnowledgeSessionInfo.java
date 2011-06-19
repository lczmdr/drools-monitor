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
    private String agentId;
    private String knowledgeBaseId;
    private String knowledgeSessionId;

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

    public void setKnowledgeSessionId(String knowledgeSessionId) {
        this.knowledgeSessionId = knowledgeSessionId;
    }

    public String getKnowledgeSessionId() {
        return knowledgeSessionId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

}
