package com.lucazamador.drools.monitor.console.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "knowledge-sessions")
public class KnowledgeSessionDataList {

    private List<KnowledgeSessionData> knowledgeSessions;

    public KnowledgeSessionDataList() {
    }

    public KnowledgeSessionDataList(List<KnowledgeSessionData> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

    @XmlElement(name = "knowledge-session")
    public List<KnowledgeSessionData> getKnowledgeSession() {
        return knowledgeSessions;
    }

    public void setKnowledgeSession(List<KnowledgeSessionData> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

    public boolean isEmpty() {
        return this.knowledgeSessions == null || this.knowledgeSessions.size() == 0;
    }

}
