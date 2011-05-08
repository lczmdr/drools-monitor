package com.lucazamador.drools.monitoring.core.discoverer;

import java.io.IOException;
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

    private String jvmId;

    /**
     * 
     * @throws DroolsMonitoringException
     */
    @Override
    public void discover() throws DroolsMonitoringException {
        logger.info("Knowledge Base discovery...");
        discoverResource(KBASE_RESOURCE_NAMESPACE, new ResourceScanner() {
            public void add(ObjectName resource) {
                resourceScanners.add(new KnowledgeBaseScanner(jvmId, resource, connector));
                KnowledgeBaseInfo kbaseInfo = new KnowledgeBaseInfo();
                kbaseInfo.setKnowledgeBaseId(resource.getKeyProperty("type"));
                kbaseInfo.setJvmName(jvmId);
                // persistence.save(kbaseInfo);
            }
        });
        logger.info("Knowledge Session discovery...");
        discoverResource(KSESSION_RESOURCE_NAMESPACE, new ResourceScanner() {
            public void add(ObjectName resource) {
                resourceScanners.add(new KnowledgeSessionScanner(jvmId, resource, connector));
                KnowledgeSessionInfo ksessionInfo = new KnowledgeSessionInfo();
                ksessionInfo.setKnowledgeSessionId(Integer.valueOf(resource.getKeyProperty("sessionId").replace(
                        "Session-", "")));
                ksessionInfo.setJvmName(jvmId);
                try {
                    String knowledgeBaseId = (String) connector.getConnection().getAttribute(resource,
                            "KnowledgeBaseId");
                    ksessionInfo.setKnowledgeBaseId(knowledgeBaseId);
                } catch (Exception e) {
                    logger.error("Unable to obtain KnowledgeBaseId attribute from ksession on jvm: " + jvmId);
                    e.printStackTrace();
                }
                // persistence.save(ksessionInfo);
            }
        });
    }

    private void discoverResource(String resourceFilter, ResourceScanner scanner) throws DroolsMonitoringException {
        try {
            List<ObjectName> resources = discoverResourceType(resourceFilter);
            for (ObjectName resource : resources) {
                scanner.add(resource);
                logger.info("Drools MBean resource discovered: " + resource.getCanonicalName());
            }
        } catch (MalformedObjectNameException e) {
            logger.error("wrong knowledge namespace: " + resourceFilter, e);
        } catch (IOException e) {
            throw new DroolsMonitoringException("Error on connection to JVM");
        }
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

    private interface ResourceScanner {
        public void add(ObjectName resource);
    }

}
