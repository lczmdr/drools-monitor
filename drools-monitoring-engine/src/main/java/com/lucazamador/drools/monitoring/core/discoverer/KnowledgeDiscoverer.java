package com.lucazamador.drools.monitoring.core.discoverer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitoring.scanner.KnowledgeBaseScanner;
import com.lucazamador.drools.monitoring.scanner.KnowledgeSessionScanner;

/**
 * Discover all the registered KnowledgeBase and KnowledgeSession in the JVM
 * MBeanServer
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeDiscoverer extends BaseDiscoverer {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeDiscoverer.class);

    private static final String KBASE_RESOURCE_NAMESPACE = "org.drools.kbases:type=*";
    private static final String KSESSION_RESOURCE_NAMESPACE = "org.drools.kbases:type=*,group=Sessions,sessionId=Session-*";

    private String agentId;

    private List<KnowledgeSessionInfo> knowledgeSessionInfos = new ArrayList<KnowledgeSessionInfo>();
    private List<KnowledgeBaseInfo> knowledgeBaseInfos = new ArrayList<KnowledgeBaseInfo>();
    private boolean discover;

    /**
     * 
     * @throws DroolsMonitoringException
     */
    @Override
    public void discover() throws DroolsMonitoringException {
        discover = false;
        discoverResource(KBASE_RESOURCE_NAMESPACE, new ResourceScanner() {
            public void add(ObjectName resource) {
                String knowledgeBaseId = resource.getKeyProperty("type");
                synchronized (resourceScanners) {
                    if (!resourceScanners.containsKey(knowledgeBaseId)) {
                        KnowledgeBaseInfo kbaseInfo = new KnowledgeBaseInfo();
                        kbaseInfo.setKnowledgeBaseId(knowledgeBaseId);
                        kbaseInfo.setAgentId(agentId);
                        knowledgeBaseInfos.add(kbaseInfo);
                        KnowledgeBaseScanner scanner = new KnowledgeBaseScanner(agentId, resource, connector);
                        resourceScanners.put(knowledgeBaseId, scanner);
                        logger.info("Drools KnowledgeBase discovered: " + resource.getCanonicalName());
                        discover = true;
                    }
                }
            }
        });
        discoverResource(KSESSION_RESOURCE_NAMESPACE, new ResourceScanner() {
            public void add(ObjectName resource) {
                String knowledgeSessionId = resource.getKeyProperty("sessionId");
                synchronized (resourceScanners) {
                    if (!resourceScanners.containsKey(knowledgeSessionId)) {
                        KnowledgeSessionInfo ksessionInfo = new KnowledgeSessionInfo();
                        ksessionInfo.setKnowledgeSessionId(knowledgeSessionId);
                        ksessionInfo.setAgentId(agentId);
                        try {
                            String knowledgeBaseId = (String) connector.getConnection().getAttribute(resource,
                                    "KnowledgeBaseId");
                            ksessionInfo.setKnowledgeBaseId(knowledgeBaseId);
                            knowledgeSessionInfos.add(ksessionInfo);
                        } catch (Exception e) {
                            logger.error("Unable to obtain KnowledgeBaseId attribute from ksession on jvm: " + agentId);
                            e.printStackTrace();
                        }
                        KnowledgeSessionScanner scanner = new KnowledgeSessionScanner(agentId, resource, connector);
                        resourceScanners.put(knowledgeSessionId, scanner);
                        logger.info("Drools KnowledgeSession discovered: " + resource.getCanonicalName());
                        discover = true;
                    }
                }
            }
        });
        if (discoveredListener != null && discover) {
            discoveredListener.discovered();
        }
    }

    private void discoverResource(String resourceFilter, ResourceScanner scanner) throws DroolsMonitoringException {
        try {
            List<ObjectName> resources = discoverResourceType(resourceFilter);
            for (ObjectName resource : resources) {
                scanner.add(resource);
            }
        } catch (MalformedObjectNameException e) {
            logger.error("wrong knowledge namespace: " + resourceFilter, e);
        } catch (IOException e) {
            throw new DroolsMonitoringException("Error on connection to JVM");
        }
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public List<KnowledgeSessionInfo> getKnowledgeSessionInfos() {
        return knowledgeSessionInfos;
    }

    public List<KnowledgeBaseInfo> getKnowledgeBaseInfos() {
        return knowledgeBaseInfos;
    }

    private interface ResourceScanner {
        public void add(ObjectName resource);
    }

}
