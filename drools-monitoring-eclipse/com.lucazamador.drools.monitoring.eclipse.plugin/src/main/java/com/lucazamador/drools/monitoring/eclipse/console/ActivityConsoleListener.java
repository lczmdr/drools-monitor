package com.lucazamador.drools.monitoring.eclipse.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.lucazamador.drools.monitoring.listener.DroolsMonitoringListener;
import com.lucazamador.drools.monitoring.model.AbstractMetric;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionMetric;

public class ActivityConsoleListener implements DroolsMonitoringListener {

    private final String id;
    private final String consoleViewId;
    private MessageConsoleStream messageConsoleStream;

    public ActivityConsoleListener(String id, String consoleViewId) {
        this.id = id;
        this.consoleViewId = consoleViewId;
    }

    @Override
    public void newMetric(AbstractMetric metric) {
        if (messageConsoleStream == null) {
            getMessageConsoleStream();
        }
        if (messageConsoleStream != null) {
            if (metric instanceof KnowledgeSessionMetric) {
                KnowledgeSessionMetric ksessionMetric = (KnowledgeSessionMetric) metric;
                if (!messageConsoleStream.isClosed()) {
                    if (id.equals(ksessionMetric.getKnowledgeBaseId() + ":" + ksessionMetric.getKnowledgeSessionId())) {
                        messageConsoleStream.println("average firing time: " + ksessionMetric.getAverageFiringTime()
                                + " total fact count: " + ksessionMetric.getTotalFactCount() + " process started: "
                                + ksessionMetric.getTotalProcessInstancesStarted());
                    }
                }
            }
        }
    }

    private void getMessageConsoleStream() {
        IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
        for (int i = 0; i < consoles.length; i++) {
            if (consoles[i].getName().equals(consoleViewId)) {
                MessageConsole messageConsole = (MessageConsole) consoles[i];
                messageConsoleStream = messageConsole.newMessageStream();
                return;
            }
        }
    }

}
