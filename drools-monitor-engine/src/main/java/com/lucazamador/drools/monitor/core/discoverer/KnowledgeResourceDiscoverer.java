package com.lucazamador.drools.monitor.core.discoverer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitor.scanner.KnowledgeBaseScanner;
import com.lucazamador.drools.monitor.scanner.KnowledgeSessionScanner;
import com.lucazamador.drools.monitor.scanner.MetricScanner;

/**
 * Discover all the registered knowledge base and knowledge session MBeans in
 * the JVM
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeResourceDiscoverer extends BaseDiscoverer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeResourceDiscoverer.class);

    private static final String KBASE_RES_NAMESPACE = "org.drools.kbases:type=*";
    private static final String KSESSION_KBASE_RES_NAMESPACE = "org.drools.kbases:type=*,";
    private static final String KSESSION_KSESION_RES_NAMESPACE = "group=Sessions,sessionId=Session-*";
// private static final String KSESSION_RES_NAMESPACE = "org.drools.kbases:type=*,group=Sessions,sessionId=Session-*";
    private static final String KSESSION_RES_NAMESPACE = KSESSION_KBASE_RES_NAMESPACE + KSESSION_KSESION_RES_NAMESPACE;

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
        discoverResource(KBASE_RES_NAMESPACE, new ResourceScanner() {
            public void add(ObjectName resource) {
                String knowledgeBaseId = resource.getKeyProperty("type");
                Map<String, MetricScanner> resourceScanners = getResourceScanners();
                synchronized (resourceScanners) {
                    if (!resourceScanners.containsKey(knowledgeBaseId)) {
                        KnowledgeBaseInfo kbaseInfo = new KnowledgeBaseInfo();
                        kbaseInfo.setKnowledgeBaseId(knowledgeBaseId);
                        kbaseInfo.setAgentId(agentId);
                        synchronized (knowledgeBaseInfos) {
                            knowledgeBaseInfos.add(kbaseInfo);
                        }
                        KnowledgeBaseScanner scanner = new KnowledgeBaseScanner(agentId, resource, getConnector());
                        resourceScanners.put(knowledgeBaseId, scanner);
                        LOGGER.info("Drools KnowledgeBase discovered: " + resource.getCanonicalName());
                        discover = true;
                    }
                }
            }
        });
        discoverResource(KSESSION_RES_NAMESPACE, new ResourceScanner() {
            public void add(ObjectName resource) {
                String knowledgeSessionId = String
                        .valueOf(resource.getKeyProperty("sessionId").replace("Session-", ""));
                String knowledgeBaseId;
                try {
                    knowledgeBaseId = (String) getConnector().getConnection().getAttribute(resource, "KnowledgeBaseId");
                } catch (Exception e1) {
                    LOGGER.error("Unable to obtain KnowledgeBaseId attribute from ksession on jvm: " + agentId);
                    return;
                }
                String scannerId = knowledgeBaseId.concat(knowledgeSessionId);
                Map<String, MetricScanner> resourceScanners = getResourceScanners();
                synchronized (resourceScanners) {
                    if (!resourceScanners.containsKey(scannerId)) {
                        KnowledgeSessionInfo ksessionInfo = new KnowledgeSessionInfo();
                        ksessionInfo.setKnowledgeSessionId(Integer.valueOf(knowledgeSessionId));
                        ksessionInfo.setAgentId(agentId);
                        ksessionInfo.setKnowledgeBaseId(knowledgeBaseId);
                        synchronized (knowledgeSessionInfos) {
                            knowledgeSessionInfos.add(ksessionInfo);
                        }
                        DroolsMBeanConnector connector = getConnector();
                        KnowledgeSessionScanner scanner = new KnowledgeSessionScanner(agentId, resource, connector);
                        resourceScanners.put(scannerId, scanner);
                        LOGGER.info("Drools KnowledgeSession discovered: " + resource.getCanonicalName());
                        discover = true;
                    }
                }
            }
        });
        if (getResourceDiscoveredListener() != null && discover) {
            getResourceDiscoveredListener().discovered(agentId);
        }
    }

    private void discoverResource(String resourceFilter, ResourceScanner scanner) throws DroolsMonitoringException {
        try {
            List<ObjectName> resources = discoverResourceType(resourceFilter);
            for (ObjectName resource : resources) {
                scanner.add(resource);
            }
        } catch (MalformedObjectNameException e) {
            LOGGER.error("wrong knowledge namespace: " + resourceFilter, e);
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
        void add(ObjectName resource);
    }

}
