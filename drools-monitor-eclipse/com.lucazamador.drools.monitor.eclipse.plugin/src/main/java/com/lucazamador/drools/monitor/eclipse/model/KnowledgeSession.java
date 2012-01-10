package com.lucazamador.drools.monitor.eclipse.model;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

public class KnowledgeSession {

    private String id;
    private KnowledgeBase parent;
    private KnowledgeSessionMetric lastMetric;
    private List<Graphic> graphics = new ArrayList<Graphic>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KnowledgeBase getParent() {
        return parent;
    }

    public void setParent(KnowledgeBase parent) {
        this.parent = parent;
    }

    public KnowledgeSessionMetric getLastMetric() {
        return lastMetric;
    }

    public void setLastMetric(KnowledgeSessionMetric lastMetric) {
        this.lastMetric = lastMetric;
    }

    public List<Graphic> getGraphics() {
        return graphics;
    }

    public void addGraphic(Graphic graphic) {
        this.graphics.add(graphic);
    }

    public void setGraphics(List<Graphic> graphics) {
        this.graphics = graphics;
    }

}
