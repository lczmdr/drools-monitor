package com.lucazamador.drools.monitoring.eclipse.view;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.lucazamador.drools.monitoring.eclipse.model.Graphic;
import com.lucazamador.drools.monitoring.eclipse.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringMetric;

public class GraphicViewFactory {

    public static void openView(Graphic graphic, List<MonitoringMetric> selectedMetrics) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        try {
            KnowledgeSession ksession = graphic.getParent();
            String viewName = ksession.getParent().getId() + " - " + ksession.getId() + " - " + graphic.getName();
            String viewTitle = viewName.concat(String.valueOf(graphic.getId()));
            GraphicView graphicView = (GraphicView) window.getActivePage().showView(GraphicView.ID, viewTitle,
                    IWorkbenchPage.VIEW_ACTIVATE);
            graphicView.setViewTitle(viewName);
            graphicView.setMetrics(selectedMetrics);
            graphicView.setGraphicId(getGraphicViewId(ksession));
            graphicView.initialize();
        } catch (PartInitException e) {
            MessageDialog.openError(window.getShell(), "Error", "Error opening graphic view");
        }
    }

    public static String getGraphicViewId(KnowledgeSession ksession) {
        return ksession.getParent().getParent().getId() + ":" + ksession.getParent().getId() + ":" + ksession.getId();
    }

}
