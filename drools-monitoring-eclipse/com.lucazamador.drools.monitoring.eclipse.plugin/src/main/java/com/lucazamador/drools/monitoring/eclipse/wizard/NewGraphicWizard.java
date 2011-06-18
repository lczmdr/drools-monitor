package com.lucazamador.drools.monitoring.eclipse.wizard;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

import com.lucazamador.drools.monitoring.eclipse.model.Graphic;
import com.lucazamador.drools.monitoring.eclipse.model.MonitoringMetric;

public class NewGraphicWizard extends Wizard {

    private NewGraphicPage1 page1;
    private List<Graphic> graphics;

    public NewGraphicWizard(List<Graphic> graphics) {
        this.graphics = graphics;
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        page1 = new NewGraphicPage1(graphics);
        addPage(page1);
    }

    @Override
    public boolean performFinish() {
        return page1.isPageComplete();
    }

    public String getGraphicName() {
        return page1.getGraphicName();
    }

    public List<MonitoringMetric> getSelectedMetrics() {
        return page1.getSelectedMetrics();
    }

}
