package com.lucazamador.drools.monitoring.eclipse;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.lucazamador.drools.monitoring.eclipse.perspective.DroolsMonitoringPerspective;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    private static final String DEFAULT_PERSPECTIVE_ID = DroolsMonitoringPerspective.ID;

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    public String getInitialWindowPerspectiveId() {
        return DEFAULT_PERSPECTIVE_ID;
    }
}
