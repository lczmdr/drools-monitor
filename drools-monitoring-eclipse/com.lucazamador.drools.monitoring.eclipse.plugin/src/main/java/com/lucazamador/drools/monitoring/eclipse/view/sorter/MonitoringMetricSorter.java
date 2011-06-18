package com.lucazamador.drools.monitoring.eclipse.view.sorter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.TableColumn;

import com.lucazamador.drools.monitoring.eclipse.model.MonitoringMetric;

public class MonitoringMetricSorter extends ViewerSorter {

    private Map<TableColumn, Boolean> sortMap = new HashMap<TableColumn, Boolean>();

    public void pushSortCriteria(TableColumn column) {
        if (this.sortMap.get(column) == null) {
            this.sortMap.put(column, new Boolean(true));
        } else {
            boolean newSort = !((Boolean) this.sortMap.get(column)).booleanValue();
            this.sortMap.put(column, new Boolean(newSort));
        }
    }

    public boolean isDescending(TableColumn column) {
        boolean returnValue = true;
        if (this.sortMap.get(column) != null)
            returnValue = ((Boolean) this.sortMap.get(column)).booleanValue();
        else
            pushSortCriteria(column);
        return returnValue;
    }

    @SuppressWarnings("unchecked")
    public int compare(Viewer viewer, Object obj1, Object obj2) {
        MonitoringMetric metric1 = (MonitoringMetric) obj1;
        MonitoringMetric metric2 = (MonitoringMetric) obj2;
        Comparator<String> comparator = getComparator();
        return comparator.compare(metric1.getDescription(), metric2.getDescription());
    }

}