package com.lucazamador.drools.monitor.model.kbase;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.lucazamador.drools.monitor.model.AbstractMetric;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeBaseMetric extends AbstractMetric implements Serializable {

    private static final long serialVersionUID = 1L;

    private String knowledgeBaseId;
    private String agentId;
    private Long sessionCount;
    private String packages;
    private List<KnowledgeGlobalMetric> globals;

    public KnowledgeBaseMetric() {
    }

    public KnowledgeBaseMetric(String knowledgeBaseId, String agentId, Long sessionCount, String packages,
            List<KnowledgeGlobalMetric> globals) {
        this.knowledgeBaseId = knowledgeBaseId;
        this.agentId = agentId;
        this.sessionCount = sessionCount;
        this.packages = packages;
        this.globals = globals;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Long getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Long sessionCount) {
        this.sessionCount = sessionCount;
    }

    public String getPackages() {
        return packages;
    }

    public List<String> getPackagesSplited() {
        return Arrays.asList(packages.split(";"));
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public List<KnowledgeGlobalMetric> getGlobals() {
        return globals;
    }

    public void setGlobals(List<KnowledgeGlobalMetric> globals) {
        this.globals = globals;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KnowledgeBaseMetric)) {
            return false;
        }
        KnowledgeBaseMetric kbm = (KnowledgeBaseMetric) o;
        return kbm.knowledgeBaseId.equals(knowledgeBaseId) && kbm.sessionCount.equals(sessionCount)
                && kbm.packages.equals(packages);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + knowledgeBaseId.hashCode();
        result = 31 * result + (int) (sessionCount ^ (sessionCount >>> 32));
        result = 31 * result + packages.hashCode();
        return result;
    }

}
