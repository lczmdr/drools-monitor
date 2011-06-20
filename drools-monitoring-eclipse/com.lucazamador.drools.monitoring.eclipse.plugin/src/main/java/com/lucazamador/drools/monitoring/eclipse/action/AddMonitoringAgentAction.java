package com.lucazamador.drools.monitoring.eclipse.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.service.prefs.BackingStoreException;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.core.DroolsMonitoring;
import com.lucazamador.drools.monitoring.eclipse.Application;
import com.lucazamador.drools.monitoring.eclipse.ICommandIds;
import com.lucazamador.drools.monitoring.eclipse.cfg.ConfigurationManager;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgentFactory;
import com.lucazamador.drools.monitoring.eclipse.view.MonitoringAgentView;
import com.lucazamador.drools.monitoring.eclipse.wizard.NewMonitoringAgentWizard;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

public class AddMonitoringAgentAction extends Action {

    private final IWorkbenchWindow window;

    public AddMonitoringAgentAction(IWorkbenchWindow window, String label) {
        this.window = window;
        setText(label);
        setToolTipText(label);
        setId(ICommandIds.ADD_MONITORING_AGENT);
        setActionDefinitionId(ICommandIds.ADD_MONITORING_AGENT);
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/add.gif"));
    }

    public void run() {
        if (window != null) {
            NewMonitoringAgentWizard wizard = new NewMonitoringAgentWizard();
            WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
            dialog.open();
            if (dialog.getReturnCode() == Window.CANCEL) {
                return;
            }
            MonitoringAgentConfiguration configuration = wizard.getConfiguration();

            DroolsMonitoring droolsMonitoring = Application.getDroolsMonitoring();
            try {
                droolsMonitoring.addMonitoringAgent(configuration);
            } catch (DroolsMonitoringException e) {
                MessageDialog.openError(window.getShell(), "Error", e.getMessage());
                return;
            }
            // update view model with a new monitoring agent object
            MonitoringAgent agent = MonitoringAgentFactory.newMonitoringAgent(configuration);
            com.lucazamador.drools.monitoring.core.agent.MonitoringAgent monitoringAgent = droolsMonitoring
                    .getMonitoringAgent(configuration.getId());
            if (monitoringAgent.isConnected()) {
                agent.build(monitoringAgent);
            }
            Application.getDroolsMonitor().addMonitoringAgent(agent);
            // update navigation view
            MonitoringAgentView navigationView = (MonitoringAgentView) window.getActivePage().findView(
                    MonitoringAgentView.ID);
            navigationView.refresh();

            ConfigurationManager reader = new ConfigurationManager();
            try {
                reader.store(configuration);
            } catch (BackingStoreException e) {
                e.printStackTrace();
                MessageDialog.openError(window.getShell(), "error", "Storing agent configuration");
            }
        }
    }

}
