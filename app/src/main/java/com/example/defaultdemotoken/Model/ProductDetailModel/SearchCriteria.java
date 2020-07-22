
package com.example.defaultdemotoken.Model.ProductDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchCriteria {

    @SerializedName("filter_groups")
    @Expose
    private List<FilterGroup> filterGroups = null;

    public List<FilterGroup> getFilterGroups() {
        return filterGroups;
    }

    public void setFilterGroups(List<FilterGroup> filterGroups) {
        this.filterGroups = filterGroups;
    }

}
