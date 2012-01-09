package com.lucazamador.drools.monitor.eclipse.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.lucazamador.drools.monitor.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitor.core.mbean.DroolsMBeanConnector;
import com.lucazamador.drools.monitor.exception.DroolsMonitoringException;

public class NewMonitoringAgentPage1 extends WizardPage {

    private Composite container;
    private Text agentIdText;
    private Text addressText;
    private Text portText;
    private Spinner scanIntervalSpinner;
    private Spinner recoveryIntervalSpinner;

    private MonitoringAgentConfiguration configuration;

    public NewMonitoringAgentPage1() {
        super("New monitoring agent");
        setTitle("Create a new monitoring agent");
        configuration = new MonitoringAgentConfiguration();
    }

    @Override
    public void createControl(Composite parent) {
        GridLayout layout = new GridLayout();
        container = new Composite(parent, SWT.NONE);
        container.setLayout(layout);
        layout.numColumns = 2;

        Label label = new Label(container, SWT.NONE);
        label.setText("Agent ID:");

        agentIdText = new Text(container, SWT.BORDER);
        agentIdText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                configuration.setId(agentIdText.getText().trim());
                setPageComplete(isConfigurationComplete());
            }
        });
        GridData gd = new GridData();
        gd.widthHint = 100;
        agentIdText.setLayoutData(gd);

        label = new Label(container, SWT.NONE);
        label.setText("Address:");

        addressText = new Text(container, SWT.BORDER);
        addressText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                configuration.setAddress(addressText.getText().trim());
                setPageComplete(isConfigurationComplete());
            }
        });
        addressText.setLayoutData(gd);

        label = new Label(container, SWT.NONE);
        label.setText("Port:");

        portText = new Text(container, SWT.BORDER);
        portText.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent e) {
                char character = e.character;
                if (character == '\b' || ('0' <= character && character <= '9')) {
                    e.doit = true;
                    return;
                }
                e.doit = false;
            }
        });
        portText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                if (portText.getText().length() > 0) {
                    configuration.setPort(Integer.valueOf(portText.getText()));
                }
                setPageComplete(isConfigurationComplete());
            }
        });
        portText.setLayoutData(gd);

        label = new Label(container, SWT.NONE);
        label.setText("Scan Interval (ms):");

        scanIntervalSpinner = new Spinner(container, SWT.NONE);
        scanIntervalSpinner.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                configuration.setScanInterval(scanIntervalSpinner.getSelection());
            }
        });
        scanIntervalSpinner.setMaximum(50000);
        scanIntervalSpinner.setMinimum(500);
        scanIntervalSpinner.setIncrement(500);
        scanIntervalSpinner.setSelection(1000);

        label = new Label(container, SWT.NONE);
        label.setText("Recovery Interval (ms):");

        recoveryIntervalSpinner = new Spinner(container, SWT.NONE);
        recoveryIntervalSpinner.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                configuration.setRecoveryInterval(recoveryIntervalSpinner.getSelection());
            }
        });
        recoveryIntervalSpinner.setMaximum(50000);
        recoveryIntervalSpinner.setMinimum(1000);
        recoveryIntervalSpinner.setIncrement(500);
        recoveryIntervalSpinner.setSelection(1000);

        Button testConnectionButton = new Button(container, SWT.PUSH);
        testConnectionButton.setText("Test Connection");
        testConnectionButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                DroolsMBeanConnector connector = new DroolsMBeanConnector();
                connector.setAddress(configuration.getAddress());
                connector.setPort(configuration.getPort());
                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                try {
                    connector.connect();
                } catch (DroolsMonitoringException e1) {
                    MessageDialog.openError(window.getShell(), "Error",
                            "Unable to connect to: " + configuration.getAddress() + ":" + configuration.getPort());
                    return;
                }
                MessageDialog.openInformation(window.getShell(), "Information", "Connection succesfull");
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        testConnectionButton.setEnabled(false);

        setControl(container);
        setPageComplete(false);

    }

    private boolean isConfigurationComplete() {
        return agentIdText.getText().trim().length() > 0 && addressText.getText().trim().length() > 0
                && portText.getText().trim().length() > 0;
    }

    @Override
    public Control getControl() {
        return container;
    }

    public void setConfiguration(MonitoringAgentConfiguration configuration) {
        this.configuration = configuration;
    }

    public MonitoringAgentConfiguration getConfiguration() {
        return configuration;
    }

}
