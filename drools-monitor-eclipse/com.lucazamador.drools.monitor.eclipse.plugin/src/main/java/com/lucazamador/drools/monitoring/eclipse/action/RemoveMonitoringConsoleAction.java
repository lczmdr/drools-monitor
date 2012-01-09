package com.lucazamador.drools.monitoring.eclipse.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;

import com.lucazamador.drools.monitoring.eclipse.ICommandIds;

public class RemoveMonitoringConsoleAction extends Action {

    private final IWorkbenchWindow window;
    private String consoleName;

    public RemoveMonitoringConsoleAction(IWorkbenchWindow window) {
        this.window = window;
        setText("Remove Console");
        setToolTipText("Remove Console");
        setId(ICommandIds.REMOVE_MONITORING_CONSOLE);
        setActionDefinitionId(ICommandIds.REMOVE_MONITORING_CONSOLE);
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/remove.gif"));
    }

    public void run() {
        MessageDialog.openInformation(window.getShell(), "info", "remove console: " + consoleName);
        IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
        for (int i = 0; i < consoles.length; i++) {
            System.out.println(consoles[i].getName());
        }
    }

    public void setCurrentConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

}
