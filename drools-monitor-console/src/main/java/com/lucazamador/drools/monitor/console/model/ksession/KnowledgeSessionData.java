package com.lucazamador.drools.monitor.console.model.ksession;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "knowledgeSession")
public class KnowledgeSessionData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String agentId;
    private String knowledgeBaseId;
    private Integer knowledgeSessionId;

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @XmlElement
    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    @XmlElement
    public Integer getKnowledgeSessionId() {
        return knowledgeSessionId;
    }

    public void setKnowledgeSessionId(Integer knowledgeSessionId) {
        this.knowledgeSessionId = knowledgeSessionId;
    }

}
