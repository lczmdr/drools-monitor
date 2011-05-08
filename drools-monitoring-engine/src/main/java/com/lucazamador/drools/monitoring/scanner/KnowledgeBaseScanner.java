package com.lucazamador.drools.monitoring.scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseMetric;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeGlobalMetric;

/**
 * Scanner that update the metrics of the associated KnowledgeBase
 * 
 * @author Lucas Amador
 * 
 */
public class KnowledgeBaseScanner extends MetricScanner {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeBaseScanner.class);
    private KnowledgeBaseMetric currentMetric;
    private String jvmId;

    public KnowledgeBaseScanner(String jvmId, ObjectName resource, DroolsMBeanConnector connector) {
        this.jvmId = jvmId;
        this.resource = resource;
        this.connector = connector;
    }

    public AbstractMetric scan() throws IOException {
        String id = (String) getAttribute("Id");
        String[] packages = (String[]) getAttribute("Packages");
        Long sessionCount = (Long) getAttribute("SessionCount");
        TabularData globalsTabularData = (TabularData) getAttribute("Globals");
        Collection<?> globals = globalsTabularData.values();
        List<KnowledgeGlobalMetric> knowledgeGlobals = new ArrayList<KnowledgeGlobalMetric>();
        for (Object object : globals) {
            if (object instanceof CompositeDataSupport) {
                CompositeDataSupport globalData = (CompositeDataSupport) object;
                String globalClass = (String) globalData.get("class");
                String globalName = (String) globalData.get("name");
                knowledgeGlobals.add(new KnowledgeGlobalMetric(globalName, globalClass));
            }
        }
        String packagesList = "";
        for (int i = 0; i < packages.length; i++) {
            packagesList = packagesList.concat(packages[i] + ";");
        }
        if (packagesList.length() > 0) {
            packagesList = packagesList.substring(0, packagesList.length() - 1);
        }
        KnowledgeBaseMetric lastMetric = new KnowledgeBaseMetric(id, jvmId, sessionCount, packagesList,
                knowledgeGlobals);
        if (lastMetric == null || !lastMetric.equals(currentMetric)) {
            logger.info("KnowledgeBase id=" + id + " metrics changed. " + lastMetric);
            currentMetric = lastMetric;
            return lastMetric;
        }
        return null;
    }

}
