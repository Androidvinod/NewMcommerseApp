
package com.example.defaultdemotoken.Model.ProductDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtensionAttributes_ {

    @SerializedName("website_id")
    @Expose
    private Integer websiteId;

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

}
