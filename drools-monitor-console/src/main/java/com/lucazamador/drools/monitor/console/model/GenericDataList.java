package com.lucazamador.drools.monitor.console.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public abstract class GenericDataList<T> {

    private List<T> list;

    @XmlElement(required = false)
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isEmpty() {
        return this.list == null || this.list.size() == 0;
    }

}
