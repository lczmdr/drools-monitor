package com.lucazamador.drools.monitoring.eclipse.view;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.lucazamador.drools.monitoring.eclipse.model.KnowledgeBase;

public class KnowledgeBaseViewFactory {

    public static void openView(KnowledgeBase kbase) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        try {
            String viewName = kbase.getParent().getId() + " - " + kbase.getId();
            KnowledgeBaseView view = (KnowledgeBaseView) window.getActivePage().showView(KnowledgeBaseView.ID,
                    viewName, IWorkbenchPage.VIEW_ACTIVATE);
            view.setViewTitle(viewName);
            view.refresh(kbase.getLastMetric());
        } catch (PartInitException e) {
            MessageDialog.openError(window.getShell(), "Error", "Error opening knowledge base view");
        }
    }

}
