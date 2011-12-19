package com.lucazamador.drools.monitoring.eclipse.view;

import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.eclipse.Application;
import com.lucazamador.drools.monitoring.eclipse.model.KnowledgeBase;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgentInfo;
import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseMetric;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionMetric;

public class GraphicUpdaterListener implements DroolsMonitoringListener {

    private final IWorkbenchWindow window;

    public GraphicUpdaterListener(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void newMetric(AbstractMetric metric) {
        if (metric instanceof KnowledgeSessionMetric) {
            KnowledgeSessionMetric kmetric = (KnowledgeSessionMetric) metric;
            if (window != null) {
                IViewReference[] viewReferences = window.getActivePage().getViewReferences();
                for (int i = 0; i < viewReferences.length; i++) {
                    if (viewReferences[i].getId().equals(GraphicView.ID)) {
                        GraphicView view = (GraphicView) viewReferences[i].getPart(false);
                        view.updateGraphic(kmetric);
                    }
                }
            }
        } else if (metric instanceof KnowledgeBaseMetric) {
            KnowledgeBaseMetric kmetric = (KnowledgeBaseMetric) metric;
            MonitoringAgentInfo agent = Application.getDroolsMonitor().getMonitoringAgent(kmetric.getAgentId());
            if (agent != null) {
                for (KnowledgeBase kbase : agent.getKnowledgeBases().values()) {
                    if (kbase.getId().equals(kmetric.getKnowledgeBaseId())) {
                        kbase.setLastMetric(kmetric);
                        break;
                    }
                }
            }

        }
    }
}
