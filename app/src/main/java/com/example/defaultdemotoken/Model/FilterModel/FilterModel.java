package com.example.defaultdemotoken.Model.FilterModel;

public class FilterModel {
    String lable,name;

    public FilterModel(String lable, String name) {
        this.lable = lable;
        this.name = name;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
