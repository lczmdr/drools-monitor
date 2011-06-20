package com.lucazamador.drools.monitoring.eclipse.recovery;

import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.eclipse.Application;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.eclipse.view.MonitoringAgentView;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;

public class RecoveryStudioListener implements MonitoringRecoveryListener {

    private final IWorkbenchWindow window;

    public RecoveryStudioListener(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void reconnected(final String agentId) {
        com.lucazamador.drools.monitoring.core.agent.MonitoringAgent monitoringAgent = Application
                .getDroolsMonitoring().getMonitoringAgent(agentId);
        MonitoringAgent agent = Application.getDroolsMonitor().getMonitoringAgent(agentId);
        agent.build(monitoringAgent);
        refreshMonitoringAgents();
    }

    @Override
    public void disconnected(String agentId) {
        MonitoringAgent agent = Application.getDroolsMonitor().getMonitoringAgent(agentId);
        if (agent != null) {
            agent.setConnected(false);
            agent.clear();
            refreshMonitoringAgents();
        }
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
