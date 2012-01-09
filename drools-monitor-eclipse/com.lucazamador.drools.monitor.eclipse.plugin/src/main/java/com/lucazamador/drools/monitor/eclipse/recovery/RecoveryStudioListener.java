package com.lucazamador.drools.monitor.eclipse.recovery;

import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitor.eclipse.Application;
import com.lucazamador.drools.monitor.eclipse.model.MonitoringAgentInfo;
import com.lucazamador.drools.monitor.eclipse.view.MonitoringAgentView;
import com.lucazamador.drools.monitor.listener.MonitoringRecoveryListener;

public class RecoveryStudioListener implements MonitoringRecoveryListener {

    private final IWorkbenchWindow window;

    public RecoveryStudioListener(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void reconnected(final String agentId) {
        MonitoringAgent monitoringAgent = Application.getDroolsMonitoring().getMonitoringAgent(agentId);
        MonitoringAgentInfo agent = Application.getDroolsMonitor().getMonitoringAgent(agentId);
        agent.build(monitoringAgent);
        refreshMonitoringAgents();
    }

    @Override
    public void disconnected(String agentId) {
        MonitoringAgentInfo agent = Application.getDroolsMonitor().getMonitoringAgent(agentId);
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
