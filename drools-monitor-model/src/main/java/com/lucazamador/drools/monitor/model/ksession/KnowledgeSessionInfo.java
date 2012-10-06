package com.lucazamador.drools.monitor.model.ksession;


public class KnowledgeSessionInfo {

    private Long id;
    private String agentId;
    private String knowledgeBaseId;
    private Integer knowledgeSessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public void setKnowledgeSessionId(Integer knowledgeSessionId) {
        this.knowledgeSessionId = knowledgeSessionId;
    }

    public Integer getKnowledgeSessionId() {
        return knowledgeSessionId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

}
