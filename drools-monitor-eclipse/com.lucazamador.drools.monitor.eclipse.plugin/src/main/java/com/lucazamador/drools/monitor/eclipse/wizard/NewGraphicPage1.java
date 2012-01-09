package com.lucazamador.drools.monitor.eclipse.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.lucazamador.drools.monitor.eclipse.model.Graphic;
import com.lucazamador.drools.monitor.eclipse.model.MonitoringMetric;
import com.lucazamador.drools.monitor.eclipse.view.sorter.MonitoringMetricSorter;

public class NewGraphicPage1 extends WizardPage {

    private Composite container;
    private String graphicName = "";
    private ListViewer availableMetricsListViewer;
    private ListViewer selectedMetricsListViewer;
    private List<MonitoringMetric> availableMetrics;
    private List<MonitoringMetric> selectedMetrics;

    public NewGraphicPage1(List<Graphic> graphics) {
        super("New graphic");
        setTitle("Create a new Graphic");
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/graphic48.png"));
    }

    @Override
    public void createControl(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        container = new Composite(parent, SWT.NONE);
        container.setLayout(layout);

        Composite graphicIdComposite = new Composite(container, SWT.NONE);
        GridData gridData = new GridData();
        gridData.horizontalSpan = 3;
        graphicIdComposite.setLayoutData(gridData);
        layout = new GridLayout();
        layout.numColumns = 2;
        graphicIdComposite.setLayout(layout);

        Label label = new Label(graphicIdComposite, SWT.NONE);
        label.setText("Graphic ID:");

        final Text graphicNameText = new Text(graphicIdComposite, SWT.BORDER);
        gridData = new GridData();
        gridData.widthHint = 150;
        graphicNameText.setLayoutData(gridData);
        graphicNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                graphicName = graphicNameText.getText().trim();
                setPageComplete(pageComplete());
            }
        });

        label = new Label(container, SWT.NONE);
        label.setText("Available Metrics");

        label = new Label(container, SWT.NONE);
        label.setText("");

        label = new Label(container, SWT.NONE);
        label.setText("Selected Metrics");

        availableMetrics = new ArrayList<MonitoringMetric>(Arrays.asList(MonitoringMetric.values()));
        selectedMetrics = new ArrayList<MonitoringMetric>();

        availableMetricsListViewer = new ListViewer(container);
        availableMetricsListViewer.setContentProvider(createContentProvider());
        availableMetricsListViewer.setLabelProvider(createLabelProvider());
        availableMetricsListViewer.setSorter(new MonitoringMetricSorter());
        availableMetricsListViewer.setInput(availableMetrics);
        availableMetricsListViewer.getList().setLayoutData(new GridData(180, 200));
        availableMetricsListViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                addSelectedMetrics();
            }
        });

        Composite buttons = new Composite(container, SWT.NONE);
        layout = new GridLayout();
        layout.numColumns = 1;
        buttons.setLayout(layout);
        Button addMetricButton = new Button(buttons, SWT.PUSH);
        addMetricButton.setText("->");
        addMetricButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                addSelectedMetrics();
            }
        });

        Button removeMetricButton = new Button(buttons, SWT.PUSH);
        removeMetricButton.setText("<-");
        removeMetricButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) selectedMetricsListViewer.getSelection();
                if (selection != null) {
                    Iterator<?> selectionIterator = selection.iterator();
                    while (selectionIterator.hasNext()) {
                        Object object = (Object) selectionIterator.next();
                        MonitoringMetric monitoringMetric = (MonitoringMetric) object;
                        selectedMetrics.remove(monitoringMetric);
                        availableMetrics.add(monitoringMetric);
                        availableMetricsListViewer.refresh();
                        selectedMetricsListViewer.refresh();
                    }
                    selectFirstElement(selectedMetricsListViewer.getList());
                    setPageComplete(pageComplete());
                }
            }
        });

        selectedMetricsListViewer = new ListViewer(container);
        selectedMetricsListViewer.setContentProvider(createContentProvider());
        selectedMetricsListViewer.setLabelProvider(createLabelProvider());
        selectedMetricsListViewer.setInput(selectedMetrics);
        selectedMetricsListViewer.setSorter(new MonitoringMetricSorter());
        selectedMetricsListViewer.getList().setLayoutData(new GridData(180, 200));

        label = new Label(container, SWT.NONE);
        label.setText("");

        setControl(container);
        setPageComplete(false);

    }

    private boolean pageComplete() {
        return selectedMetrics.size() > 0 && graphicName.length() > 0;
    }

    private void addSelectedMetrics() {
        IStructuredSelection selection = (IStructuredSelection) availableMetricsListViewer.getSelection();
        if (selection != null) {
            Iterator<?> selectionIterator = selection.iterator();
            while (selectionIterator.hasNext()) {
                Object object = (Object) selectionIterator.next();
                MonitoringMetric monitoringMetric = (MonitoringMetric) object;
                availableMetrics.remove(monitoringMetric);
                selectedMetrics.add(monitoringMetric);
                availableMetricsListViewer.refresh();
                selectedMetricsListViewer.refresh();
            }
            selectFirstElement(availableMetricsListViewer.getList());
            setPageComplete(pageComplete());
        }
    }

    protected void selectFirstElement(org.eclipse.swt.widgets.List list) {
        if (list.getItemCount() > 0) {
            list.select(0);
        }
    }

    private IStructuredContentProvider createContentProvider() {
        return new IStructuredContentProvider() {
            public Object[] getElements(Object inputElement) {
                @SuppressWarnings("unchecked")
                List<MonitoringMetric> metrics = (List<MonitoringMetric>) inputElement;
                return metrics.toArray();
            }

            public void dispose() {
            }

            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }
        };
    }

    private LabelProvider createLabelProvider() {
        return new LabelProvider() {
            public Image getImage(Object element) {
                return null;
            }

            public String getText(Object element) {
                MonitoringMetric metric = (MonitoringMetric) element;
                return metric.getDescription();
            }
        };
    }

    @Override
    public Control getControl() {
        return container;
    }

    public String getGraphicName() {
        return graphicName;
    }

    public List<MonitoringMetric> getSelectedMetrics() {
        return selectedMetrics;
    }

}
