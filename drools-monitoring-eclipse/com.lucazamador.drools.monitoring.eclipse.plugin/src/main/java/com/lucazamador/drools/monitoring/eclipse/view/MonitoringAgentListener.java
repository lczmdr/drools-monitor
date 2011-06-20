package com.lucazamador.drools.monitoring.eclipse.view;

import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.eclipse.Application;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.listener.ResourceDiscoveredListener;

public class MonitoringAgentListener implements ResourceDiscoveredListener {

    private final IWorkbenchWindow window;

    public MonitoringAgentListener(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void discovered(String agentId) {
        com.lucazamador.drools.monitoring.core.agent.MonitoringAgent monitoringAgent = Application
                .getDroolsMonitoring().getMonitoringAgent(agentId);
        MonitoringAgent agent = Application.getDroolsMonitor().getMonitoringAgent(agentId);
        agent.build(monitoringAgent);
        refreshMonitoringAgents();
    }

    private void refreshMonitoringAgents() {
        window.getShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                MonitoringAgentView navigationView = (MonitoringAgentView) window.getActivePage().findView(
                        MonitoringAgentView.ID);
                navigationView.refresh();
            }
        });
    }

}
