package com.lucazamador.drools.monitor.eclipse.wizard;

import org.eclipse.jface.wizard.Wizard;

import com.lucazamador.drools.monitor.cfg.MonitoringAgentConfiguration;

public class NewMonitoringAgentWizard extends Wizard {

    private NewMonitoringAgentPage1 page1;

    public NewMonitoringAgentWizard() {
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        page1 = new NewMonitoringAgentPage1();
        addPage(page1);
    }

    @Override
    public boolean performFinish() {
        return page1.isPageComplete();
    }

    public MonitoringAgentConfiguration getConfiguration() {
        return page1.getConfiguration();
    }

}
