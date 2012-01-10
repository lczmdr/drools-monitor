package com.lucazamador.drools.monitor.eclipse.view;

import java.text.SimpleDateFormat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

import com.lucazamador.drools.monitor.eclipse.model.KnowledgeSession;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeRuleMetric;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

public class KnowledgeSessionView extends ViewPart {

    public static final String ID = "com.lucazamador.drools.monitor.eclipse.view.knowledgeSessionView";
    private static final String LAST_METRIC_OBTAINED_AT = "Last metric obtained at ";

    private KnowledgeSession ksession;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS MM/dd/yyyy");

    private FormToolkit toolkit;
    private ScrolledForm form;
    private TableViewer rulesTableViewer;
    private Label lastMetricLabel;

    public void createPartControl(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createScrolledForm(parent);
        form.setText("Knowledge session: ");

        TableWrapLayout layout = new TableWrapLayout();
        form.getBody().setLayout(layout);
        layout.numColumns = 1;
        TableWrapData td = new TableWrapData();
        td.colspan = 1;

        td = new TableWrapData(TableWrapData.FILL_GRAB);
        td = new TableWrapData();
        td.colspan = 2;

        createRulesSection();

        toolkit.paintBordersFor(form.getBody());
    }

    private void createRulesSection() {
        int sectionStyle = Section.DESCRIPTION | Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED;
        Section section = toolkit.createSection(form.getBody(), sectionStyle);
        TableWrapData td = new TableWrapData(TableWrapData.FILL);
        td.colspan = 1;
        section.setLayoutData(td);
        section.addExpansionListener(new ExpansionAdapter() {
            public void expansionStateChanged(ExpansionEvent e) {
                form.reflow(true);
            }
        });
        section.setText("Rules statistics");
        section.setDescription("Defined rules:");
        Composite rulesSection = toolkit.createComposite(section);
        rulesSection.setLayout(new GridLayout());
        Table rulesTable = toolkit.createTable(rulesSection, SWT.NULL);
        rulesTable.setHeaderVisible(true);
        rulesTable.setLinesVisible(true);
        rulesTable.setLayoutData(new GridData(800, 250));

        rulesTableViewer = new TableViewer(rulesTable);
        rulesTableViewer.setContentProvider(new ArrayContentProvider());

        TableViewerColumn viewerColumn = new TableViewerColumn(rulesTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Name");
        viewerColumn.getColumn().setWidth(250);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                KnowledgeRuleMetric rule = (KnowledgeRuleMetric) element;
                return rule.getName();
            };

            public Image getImage(Object element) {
                return ImageDescriptor.createFromFile(getClass(), "/icons/rule.gif").createImage();
            };
        });

        viewerColumn = new TableViewerColumn(rulesTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Activations Created");
        viewerColumn.getColumn().setWidth(140);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                KnowledgeRuleMetric rule = (KnowledgeRuleMetric) element;
                return rule.getActivationsCreated().toString();
            };

            public Image getImage(Object element) {
                return null;
            };
        });

        viewerColumn = new TableViewerColumn(rulesTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Activations Cancelled");
        viewerColumn.getColumn().setWidth(140);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                KnowledgeRuleMetric rule = (KnowledgeRuleMetric) element;
                return rule.getActivationsCancelled().toString();
            };

            public Image getImage(Object element) {
                return null;
            };
        });

        viewerColumn = new TableViewerColumn(rulesTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Activations Fired");
        viewerColumn.getColumn().setWidth(140);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                KnowledgeRuleMetric rule = (KnowledgeRuleMetric) element;
                return rule.getActivationsFired().toString();
            };

            public Image getImage(Object element) {
                return null;
            };
        });

        viewerColumn = new TableViewerColumn(rulesTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Firing Time");
        viewerColumn.getColumn().setWidth(120);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                KnowledgeRuleMetric rule = (KnowledgeRuleMetric) element;
                return rule.getFiringTime().toString();
            };

            public Image getImage(Object element) {
                return null;
            };
        });
        section.setClient(rulesSection);

        lastMetricLabel = toolkit.createLabel(rulesSection, LAST_METRIC_OBTAINED_AT);
        lastMetricLabel.setLayoutData(new GridData(400, 20));

        Button refreshButton = toolkit.createButton(rulesSection, "Refresh", SWT.PUSH);
        refreshButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                refresh(ksession.getLastMetric());
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });

    }

    public void setViewTitle(String title) {
        this.setPartName(title);
    }

    private void setKnowledgeSession(KnowledgeSession ksession) {
        this.ksession = ksession;
    }

    @Override
    public void setFocus() {

    }

    public void refresh(KnowledgeSessionMetric lastMetric) {
        if (lastMetric != null) {
            form.setText("Knowledge session: " + lastMetric.getKnowledgeSessionId());
            lastMetricLabel.setText(LAST_METRIC_OBTAINED_AT + sdf.format(lastMetric.getTimestamp()));
            rulesTableViewer.setInput(lastMetric.getRuleStats().toArray());
        }
    }

    public static void openView(KnowledgeSession ksession) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        try {
            String viewName = ksession.getParent().getParent().getId() + " - " + ksession.getParent().getId() + " - "
                    + ksession.getId();
            KnowledgeSessionView view = (KnowledgeSessionView) window.getActivePage().showView(KnowledgeSessionView.ID,
                    viewName, IWorkbenchPage.VIEW_ACTIVATE);
            view.setViewTitle(viewName);
            view.refresh(ksession.getLastMetric());
            view.setKnowledgeSession(ksession);
        } catch (PartInitException e) {
            MessageDialog.openError(window.getShell(), "Error", "Error opening knowledge base view");
        }
    }

}