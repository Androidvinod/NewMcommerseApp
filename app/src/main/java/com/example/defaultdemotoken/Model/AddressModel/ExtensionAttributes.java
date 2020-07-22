
package com.example.defaultdemotoken.Model.AddressModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtensionAttributes {

    @SerializedName("is_subscribed")
    @Expose
    private Boolean isSubscribed;

    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

}
