package com.lucazamador.drools.monitor.console.model.kbase;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "knowledgeBases")
public class KnowledgeBaseDataList {

    private List<KnowledgeBaseData> knowledgeBases;

    public KnowledgeBaseDataList() {
    }

    public KnowledgeBaseDataList(List<KnowledgeBaseData> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }

    @XmlElement(name = "knowledgeBase")
    public List<KnowledgeBaseData> getKnowledgeBase() {
        return this.knowledgeBases;
    }

    public void setKnowledgeBase(List<KnowledgeBaseData> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }

}
