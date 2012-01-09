package com.lucazamador.drools.monitor.eclipse.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.service.prefs.BackingStoreException;

import com.lucazamador.drools.monitor.eclipse.Application;
import com.lucazamador.drools.monitor.eclipse.ICommandIds;
import com.lucazamador.drools.monitor.eclipse.cfg.ConfigurationManager;
import com.lucazamador.drools.monitor.eclipse.model.MonitoringAgentInfo;
import com.lucazamador.drools.monitor.eclipse.view.MonitoringAgentView;

public class RemoveMonitoringAgentAction extends Action {

    private final IWorkbenchWindow window;
    private MonitoringAgentInfo monitoringAgentInfo;

    public RemoveMonitoringAgentAction(IWorkbenchWindow window, String label) {
        this.window = window;
        setText(label);
        setToolTipText(label);
        setId(ICommandIds.REMOVE_MONITORING_AGENT);
        setActionDefinitionId(ICommandIds.REMOVE_MONITORING_AGENT);
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/remove.gif"));
    }

    public void run() {
        if (window != null) {
            if (MessageDialog.openConfirm(window.getShell(), "Warning", "Do you want to remove the monitoring agent "
                    + monitoringAgentInfo.getId() + " ?")) {
                Application.getDroolsMonitoring().removeMonitoringAgent(monitoringAgentInfo.getId());
                Application.getDroolsMonitor().removeMonitorAgent(monitoringAgentInfo.getId());
                // update navigation view
                MonitoringAgentView navigationView = (MonitoringAgentView) window.getActivePage().findView(
                        MonitoringAgentView.ID);
                navigationView.refresh();
                ConfigurationManager manager = new ConfigurationManager();
                try {
                    manager.remove(monitoringAgentInfo.getId());
                } catch (BackingStoreException e) {
                    MessageDialog.openError(window.getShell(), "Error", "Error updating the configuration");
                }
                monitoringAgentInfo = null;
                setEnabled(false);
            }
        }
    }

    public void setMonitoringAgent(MonitoringAgentInfo monitoringAgentInfo) {
        this.monitoringAgentInfo = monitoringAgentInfo;
    }

}
