package com.lucazamador.drools.monitor.eclipse.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.service.prefs.BackingStoreException;

import com.lucazamador.drools.monitor.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitor.core.DroolsMonitoring;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitor.eclipse.Application;
import com.lucazamador.drools.monitor.eclipse.ICommandIds;
import com.lucazamador.drools.monitor.eclipse.cfg.ConfigurationManager;
import com.lucazamador.drools.monitor.eclipse.model.MonitoringAgentInfo;
import com.lucazamador.drools.monitor.eclipse.model.MonitoringAgentInfoFactory;
import com.lucazamador.drools.monitor.eclipse.view.MonitoringAgentView;
import com.lucazamador.drools.monitor.eclipse.wizard.NewMonitoringAgentWizard;
import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;

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
            if (droolsMonitoring.getMonitoringAgent(configuration.getId())!=null) {
                MessageDialog.openInformation(window.getShell(), "Error", "A monitoring agent with the same ID already exists.");
                return;
            }
            try {
                droolsMonitoring.addMonitoringAgent(configuration);
            } catch (DroolsMonitoringException e) {
                MessageDialog.openError(window.getShell(), "Error", e.getMessage());
                return;
            }
            // update view model with a new monitoring agent object
            MonitoringAgentInfo agent = MonitoringAgentInfoFactory.newMonitoringAgent(configuration);
            MonitoringAgent monitoringAgent = droolsMonitoring.getMonitoringAgent(configuration.getId());
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
                MessageDialog.openError(window.getShell(), "error", "Storing agent configuration");
            }
        }
    }

}
