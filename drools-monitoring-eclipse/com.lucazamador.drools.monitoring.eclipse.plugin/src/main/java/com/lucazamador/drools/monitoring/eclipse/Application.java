package com.lucazamador.drools.monitoring.eclipse;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.lucazamador.drools.monitoring.core.DroolsMonitoring;
import com.lucazamador.drools.monitoring.core.DroolsMonitoringFactory;
import com.lucazamador.drools.monitoring.eclipse.model.DroolsMonitor;
import com.lucazamador.drools.monitoring.eclipse.recovery.RecoveryStudioListener;
import com.lucazamador.drools.monitoring.eclipse.view.GraphicUpdaterListener;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

    private static DroolsMonitoring droolsMonitoring;
    private static DroolsMonitor droolsMonitor;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
     * IApplicationContext)
     */
    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        try {
            int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
            if (returnCode == PlatformUI.RETURN_RESTART)
                return IApplication.EXIT_RESTART;
            else
                return IApplication.EXIT_OK;
        } finally {
            display.dispose();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    public void stop() {
        if (!PlatformUI.isWorkbenchRunning())
            return;
        final IWorkbench workbench = PlatformUI.getWorkbench();
        final Display display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            public void run() {
                if (!display.isDisposed())
                    workbench.close();
            }
        });
    }

    public static DroolsMonitoring getDroolsMonitoring() {
        if (droolsMonitoring == null) {
            IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            droolsMonitoring = DroolsMonitoringFactory.newDroolsMonitoring(new RecoveryStudioListener(window));
            droolsMonitoring.registerListener(new GraphicUpdaterListener(window));
            try {
                droolsMonitoring.start();
            } catch (DroolsMonitoringException e) {
                e.printStackTrace();
            }
        }
        return droolsMonitoring;
    }

    public static DroolsMonitor getDroolsMonitor() {
        if (droolsMonitor == null) {
            droolsMonitor = new DroolsMonitor();
        }
        return droolsMonitor;
    }

}
