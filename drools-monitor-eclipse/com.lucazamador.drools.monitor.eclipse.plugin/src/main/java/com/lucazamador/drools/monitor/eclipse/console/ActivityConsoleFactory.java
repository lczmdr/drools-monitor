package com.lucazamador.drools.monitor.eclipse.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;

import com.lucazamador.drools.monitor.eclipse.model.KnowledgeBase;
import com.lucazamador.drools.monitor.eclipse.model.KnowledgeSession;

public class ActivityConsoleFactory {

    public static void openActivityConsole(String activityConsoleId) {
        IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
        boolean exists = false;
        for (int i = 0; i < consoles.length; i++) {
            if (consoles[i].getName().equals(activityConsoleId)) {
                MessageConsole cc = (MessageConsole) consoles[i];
                exists = true;
                ConsolePlugin.getDefault().getConsoleManager().showConsoleView(cc);
                break;
            }
        }
        if (!exists) {
            MessageConsole messageConsole = new MessageConsole(activityConsoleId, null);
            ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { messageConsole });
        }
    }

    public static String getViewId(KnowledgeBase kbase) {
        return kbase.getParent().getId() + " - kbase: " + kbase.getId();
    }

    public static String getViewId(KnowledgeSession ksession) {
        return ksession.getParent().getId() + " - ksession: " + ksession.getId();
    }

}
