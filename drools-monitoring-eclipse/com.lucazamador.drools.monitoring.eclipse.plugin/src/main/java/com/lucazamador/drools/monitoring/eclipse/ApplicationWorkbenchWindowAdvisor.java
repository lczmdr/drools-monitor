package com.lucazamador.drools.monitoring.eclipse;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.osgi.service.prefs.BackingStoreException;

import com.lucazamador.drools.monitoring.core.DroolsMonitoring;
import com.lucazamador.drools.monitoring.eclipse.cfg.ConfigurationManager;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgentFactory;
import com.lucazamador.drools.monitoring.eclipse.view.MonitoringAgentView;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public static final String APP_TITLE = "Drools Monitoring";

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }

    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setTitle(APP_TITLE);
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(true);
        configurer.setShowProgressIndicator(true);
    }

    @Override
    public void postWindowCreate() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.getWindow().getShell().setMaximized(true);
        initialize();
    }

    private void initialize() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        DroolsMonitoring droolsMonitoring = Application.getDroolsMonitoring();
        try {
            List<MonitoringAgent> agents = configurationManager.read();
            for (MonitoringAgent agent : agents) {
                try {
                    droolsMonitoring.addMonitoringAgent(MonitoringAgentFactory.newMonitoringAgentConfiguration(agent));
                    com.lucazamador.drools.monitoring.core.agent.MonitoringAgent monitoringAgent = droolsMonitoring
                            .getMonitoringAgent(agent.getId());
                    if (monitoringAgent.isConnected()) {
                        agent.build(monitoringAgent);
                    }
                } catch (DroolsMonitoringException e) {
                    e.printStackTrace();
                    continue;
                }
                Application.getDroolsMonitor().addMonitoringAgent(agent);
            }
        } catch (BackingStoreException e) {
            MessageDialog.openError(getWindowConfigurer().getWindow().getShell(), "Error",
                    "Error reading default configuration");
        }
        MonitoringAgentView navigationView = (MonitoringAgentView) getWindowConfigurer().getWindow().getActivePage()
                .findView(MonitoringAgentView.ID);
        navigationView.refresh();
    }
}
