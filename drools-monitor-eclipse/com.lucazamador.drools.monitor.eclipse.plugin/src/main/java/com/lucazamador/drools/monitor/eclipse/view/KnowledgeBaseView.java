package com.lucazamador.drools.monitor.eclipse.view;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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

import com.lucazamador.drools.monitor.eclipse.model.KnowledgeBase;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeBaseMetric;
import com.lucazamador.drools.monitor.model.kbase.KnowledgeGlobalMetric;

public class KnowledgeBaseView extends ViewPart {

    public static final String ID = "com.lucazamador.drools.monitor.eclipse.view.knowledgeBaseView";

    private FormToolkit toolkit;
    private ScrolledForm form;
    private Label sessionCountLabel;
    private TableViewer packagesTableViewer;
    private TableViewer globalsTableViewer;

    public void createPartControl(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createScrolledForm(parent);
        form.setText("Knowledge Base: ");

        TableWrapLayout layout = new TableWrapLayout();
        form.getBody().setLayout(layout);
        layout.numColumns = 2;
        TableWrapData td = new TableWrapData();
        td.colspan = 1;
        sessionCountLabel = toolkit.createLabel(form.getBody(), "Session Count:    ");

        toolkit.createLabel(form.getBody(), "");
        td = new TableWrapData(TableWrapData.FILL_GRAB);
        td = new TableWrapData();
        td.colspan = 2;

        createPackageSection();
        createGlobalsSection();

        toolkit.paintBordersFor(form.getBody());
    }

    private void createPackageSection() {
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
        section.setText("Packages");
        section.setDescription("Available packages:");
        Composite packagesSection = toolkit.createComposite(section);
        packagesSection.setLayout(new GridLayout());
        Table packagesTable = toolkit.createTable(packagesSection, SWT.NULL);
        packagesTable.setHeaderVisible(true);
        packagesTable.setLinesVisible(true);
        packagesTable.setLayoutData(new GridData(300, 200));

        packagesTableViewer = new TableViewer(packagesTable);
        packagesTableViewer.setContentProvider(new ArrayContentProvider());

        TableViewerColumn viewerColumn = new TableViewerColumn(packagesTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Name");
        viewerColumn.getColumn().setWidth(300);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                return element.toString();
            };

            public Image getImage(Object element) {
                return ImageDescriptor.createFromFile(getClass(), "/icons/package.gif").createImage();
            };
        });
        section.setClient(packagesSection);
    }

    private void createGlobalsSection() {
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
        section.setText("Globals");
        section.setDescription("Defined Globals:");
        Composite packagesSection = toolkit.createComposite(section);
        packagesSection.setLayout(new GridLayout());
        Table globalsTable = toolkit.createTable(packagesSection, SWT.NULL);
        globalsTable.setHeaderVisible(true);
        globalsTable.setLinesVisible(true);
        globalsTable.setLayoutData(new GridData(400, 200));

        globalsTableViewer = new TableViewer(globalsTable);
        globalsTableViewer.setContentProvider(new ArrayContentProvider());

        TableViewerColumn viewerColumn = new TableViewerColumn(globalsTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Name");
        viewerColumn.getColumn().setWidth(220);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                KnowledgeGlobalMetric global = (KnowledgeGlobalMetric) element;
                return global.getName();
            };

            public Image getImage(Object element) {
                return ImageDescriptor.createFromFile(getClass(), "/icons/package.gif").createImage();
            };
        });

        viewerColumn = new TableViewerColumn(globalsTableViewer, SWT.NONE);
        viewerColumn.getColumn().setText("Type");
        viewerColumn.getColumn().setWidth(180);
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                KnowledgeGlobalMetric global = (KnowledgeGlobalMetric) element;
                return global.getClassType();
            };

            public Image getImage(Object element) {
                return ImageDescriptor.createFromFile(getClass(), "/icons/package.gif").createImage();
            };
        });
        section.setClient(packagesSection);
    }

    public void setViewTitle(String title) {
        this.setPartName(title);
    }

    @Override
    public void setFocus() {

    }

    public void refresh(KnowledgeBaseMetric lastMetric) {
        if (lastMetric != null) {
            form.setText("Knowledge Base: " + lastMetric.getKnowledgeBaseId());
            sessionCountLabel.setText("Session Count: " + lastMetric.getSessionCount());
            packagesTableViewer.setInput(lastMetric.getPackagesSplited().toArray());
            globalsTableViewer.setInput(lastMetric.getGlobals().toArray());
        }
    }

    public static void openView(KnowledgeBase kbase) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        try {
            String viewName = kbase.getParent().getId() + " - " + kbase.getId();
            KnowledgeBaseView view = (KnowledgeBaseView) window.getActivePage().showView(KnowledgeBaseView.ID,
                    viewName, IWorkbenchPage.VIEW_ACTIVATE);
            view.setViewTitle(viewName);
            view.refresh(kbase.getLastMetric());
        } catch (PartInitException e) {
            MessageDialog.openError(window.getShell(), "Error", "Error opening knowledge base view");
        }
    }

}