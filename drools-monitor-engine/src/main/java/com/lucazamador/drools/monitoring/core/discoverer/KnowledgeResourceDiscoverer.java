package com.lucazamador.drools.monitoring.core.discoverer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
 * Discover all the registered knowledge base and knowledge session MBeans in
 * the JVM
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeResourceDiscoverer extends BaseDiscoverer {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeResourceDiscoverer.class);

    private static final String KBASE_RESOURCE_NAMESPACE = "org.drools.kbases:type=*";
    private static final String KSESSION_RESOURCE_NAMESPACE = "org.drools.kbases:type=*,group=Sessions,sessionId=Session-*";

    private String agentId;

    private List<KnowledgeSessionInfo> knowledgeSessionInfos = Collections
            .synchronizedList(new ArrayList<KnowledgeSessionInfo>());
    private List<KnowledgeBaseInfo> knowledgeBaseInfos = Collections
            .synchronizedList(new ArrayList<KnowledgeBaseInfo>());
    private boolean discover;

    /**
     * Discover all the registered Drools knowledge resources (aka: knowledge
     * base and knowledge sessions). Register and initalize a knowledge scanner
     * for each of the knowledge resources discovered.
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
                        synchronized (knowledgeBaseInfos) {
                            knowledgeBaseInfos.add(kbaseInfo);
                        }
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
                String knowledgeSessionId = String
                        .valueOf(resource.getKeyProperty("sessionId").replace("Session-", ""));
                String knowledgeBaseId;
                try {
                    knowledgeBaseId = (String) connector.getConnection().getAttribute(resource, "KnowledgeBaseId");
                } catch (Exception e1) {
                    logger.error("Unable to obtain KnowledgeBaseId attribute from ksession on jvm: " + agentId);
                    return;
                }
                String scannerId = knowledgeBaseId.concat(knowledgeSessionId);
                synchronized (resourceScanners) {
                    if (!resourceScanners.containsKey(scannerId)) {
                        KnowledgeSessionInfo ksessionInfo = new KnowledgeSessionInfo();
                        ksessionInfo.setKnowledgeSessionId(Integer.valueOf(knowledgeSessionId));
                        ksessionInfo.setAgentId(agentId);
                        ksessionInfo.setKnowledgeBaseId(knowledgeBaseId);
                        synchronized (knowledgeSessionInfos) {
                            knowledgeSessionInfos.add(ksessionInfo);
                        }
                        KnowledgeSessionScanner scanner = new KnowledgeSessionScanner(agentId, resource, connector);
                        resourceScanners.put(scannerId, scanner);
                        logger.info("Drools KnowledgeSession discovered: " + resource.getCanonicalName());
                        discover = true;
                    }
                }
            }
        });
        if (discoveredListener != null && discover) {
            discoveredListener.discovered(agentId);
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
        synchronized (knowledgeSessionInfos) {
            List<KnowledgeSessionInfo> copy = new ArrayList<KnowledgeSessionInfo>(knowledgeSessionInfos.size());
            for (KnowledgeSessionInfo knowledgeSessionInfo : knowledgeSessionInfos) {
                copy.add(knowledgeSessionInfo);
            }
            return copy;
        }
    }

    public List<KnowledgeBaseInfo> getKnowledgeBaseInfos() {
        synchronized (knowledgeBaseInfos) {
            List<KnowledgeBaseInfo> copy = new ArrayList<KnowledgeBaseInfo>(knowledgeBaseInfos.size());
            for (KnowledgeBaseInfo knowledgeBaseInfo : knowledgeBaseInfos) {
                copy.add(knowledgeBaseInfo);
            }
            return copy;
        }
    }

    private interface ResourceScanner {
        public void add(ObjectName resource);
    }

}
