package com.lucazamador.drools.monitor.console.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "knowledge-bases")
public class KnowledgeBaseDataList extends GenericDataList<KnowledgeBaseData> {

    public KnowledgeBaseDataList() {
    }

    public KnowledgeBaseDataList(List<KnowledgeBaseData> knowledgeBases) {
        this.setList(knowledgeBases);
    }

    @XmlElement(name = "knowledge-base")
    public List<KnowledgeBaseData> getKnowledgeBase() {
        return this.getList();
    }

    public void setKnowledgeBase(List<KnowledgeBaseData> knowledgeBases) {
        this.setList(knowledgeBases);
    }

}
