package com.lucazamador.drools.monitoring.eclipse.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.eclipse.ICommandIds;
import com.lucazamador.drools.monitoring.eclipse.model.Graphic;
import com.lucazamador.drools.monitoring.eclipse.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.eclipse.view.MonitoringAgentView;

public class RemoveGraphicAction extends Action {

    private final IWorkbenchWindow window;
    private final Graphic graphic;

    public RemoveGraphicAction(IWorkbenchWindow window, Graphic graphic) {
        this.window = window;
        this.graphic = graphic;
        setText("Remove Graphic");
        setToolTipText("Remove Graphic");
        setId(ICommandIds.REMOVE_GRAPHIC);
        setActionDefinitionId(ICommandIds.REMOVE_GRAPHIC);
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/remove.gif"));
    }

    public void run() {
        if (window != null) {
            if (MessageDialog.openConfirm(window.getShell(), "Warning", "Do you want to remove this graphic?")) {
                KnowledgeSession ksession = graphic.getParent();
                ksession.getGraphics().remove(graphic);
                MonitoringAgentView navigationView = (MonitoringAgentView) window.getActivePage().findView(
                        MonitoringAgentView.ID);
                navigationView.refresh();
            }
        }
    }

}
