package com.example.defaultdemotoken.Model.FilterModel;

public class SortModel {
    String name,label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SortModel(String name, String label) {
        this.name = name;
        this.label = label;
    }
}
