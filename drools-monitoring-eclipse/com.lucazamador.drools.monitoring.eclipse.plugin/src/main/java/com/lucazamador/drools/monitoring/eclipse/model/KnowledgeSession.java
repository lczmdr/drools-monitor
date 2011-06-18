package com.lucazamador.drools.monitoring.eclipse.model;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeSession {

    private String id;
    private KnowledgeBase parent;
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
