package com.lucazamador.drools.monitor.eclipse.action;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitor.eclipse.ICommandIds;
import com.lucazamador.drools.monitor.eclipse.model.Graphic;
import com.lucazamador.drools.monitor.eclipse.model.KnowledgeSession;
import com.lucazamador.drools.monitor.eclipse.model.MonitoringMetric;
import com.lucazamador.drools.monitor.eclipse.view.GraphicView;
import com.lucazamador.drools.monitor.eclipse.view.MonitoringAgentView;
import com.lucazamador.drools.monitor.eclipse.wizard.NewGraphicWizard;

public class AddGraphicAction extends Action {

    private final IWorkbenchWindow window;
    private final KnowledgeSession ksession;

    public AddGraphicAction(IWorkbenchWindow window, KnowledgeSession ksession) {
        this.window = window;
        this.ksession = ksession;
        setText("Add Graphic");
        setToolTipText("Add Graphic");
        setId(ICommandIds.ADD_GRAPHIC);
        setActionDefinitionId(ICommandIds.ADD_GRAPHIC);
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/graphic.png"));
    }

    public void run() {
        if (window != null) {
            NewGraphicWizard wizard = new NewGraphicWizard(ksession.getGraphics());
            WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
            dialog.open();
            if (dialog.getReturnCode() == Window.CANCEL) {
                return;
            }
            List<MonitoringMetric> selectedMetrics = wizard.getSelectedMetrics();
            String graphicName = wizard.getGraphicName();
            Graphic graphic = new Graphic();
            graphic.setName(graphicName);
            graphic.setParent(ksession);
            graphic.setMetrics(selectedMetrics);
            ksession.addGraphic(graphic);
            MonitoringAgentView navigationView = (MonitoringAgentView) window.getActivePage().findView(
                    MonitoringAgentView.ID);
            navigationView.refresh();
            GraphicView.openView(graphic, selectedMetrics);
        }
    }

}
