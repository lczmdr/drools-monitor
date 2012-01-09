package com.lucazamador.drools.monitor.eclipse.view.provider;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.lucazamador.drools.monitor.eclipse.model.DroolsMonitor;
import com.lucazamador.drools.monitor.eclipse.model.Graphic;
import com.lucazamador.drools.monitor.eclipse.model.KnowledgeBase;
import com.lucazamador.drools.monitor.eclipse.model.KnowledgeSession;
import com.lucazamador.drools.monitor.eclipse.model.MonitoringAgentInfo;

public class MonitorLabelProvider extends LabelProvider {

    private ImageRegistry imageRegistry = new ImageRegistry();

    public MonitorLabelProvider() {
        imageRegistry.put("agent", ImageDescriptor.createFromFile(MonitorLabelProvider.class, "/icons/agent.png"));
        imageRegistry.put("agent-disabled",
                ImageDescriptor.createFromFile(MonitorLabelProvider.class, "/icons/agent_disabled.png"));
        imageRegistry.put("kbase", ImageDescriptor.createFromFile(MonitorLabelProvider.class, "/icons/kbase.png"));
        imageRegistry.put("ksession", ImageDescriptor.createFromFile(getClass(), "/icons/ksession.png"));
        imageRegistry.put("graphic", ImageDescriptor.createFromFile(getClass(), "/icons/graphic.png"));
    }

    public Image getImage(Object element) {
        if (element instanceof DroolsMonitor) {
            return imageRegistry.get("monitor");
        } else if (element instanceof MonitoringAgentInfo) {
            MonitoringAgentInfo agent = (MonitoringAgentInfo) element;
            if (agent.isConnected()) {
                return imageRegistry.get("agent");
            }
            return imageRegistry.get("agent-disabled");
        } else if (element instanceof KnowledgeBase) {
            return imageRegistry.get("kbase");
        } else if (element instanceof KnowledgeSession) {
            return imageRegistry.get("ksession");
        } else if (element instanceof Graphic) {
            return imageRegistry.get("graphic");
        }
        return null;
    }

    public String getText(Object element) {
        if (element instanceof DroolsMonitor) {
            return "Drools Monitoring";
        } else if (element instanceof MonitoringAgentInfo) {
            MonitoringAgentInfo agent = (MonitoringAgentInfo) element;
            return agent.getId() + " (" + agent.getAddress() + ":" + agent.getPort() + ")";
        } else if (element instanceof KnowledgeBase) {
            return ((KnowledgeBase) element).getId();
        } else if (element instanceof KnowledgeSession) {
            return ((KnowledgeSession) element).getId();
        } else if (element instanceof Graphic) {
            return ((Graphic) element).getName();
        }
        return null;
    }

    public void dispose() {
        imageRegistry.dispose();
    }

}
