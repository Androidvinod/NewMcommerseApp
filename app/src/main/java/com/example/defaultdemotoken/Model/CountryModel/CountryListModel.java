
package com.example.defaultdemotoken.Model.CountryModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryListModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("two_letter_abbreviation")
    @Expose
    private String twoLetterAbbreviation;
    @SerializedName("three_letter_abbreviation")
    @Expose
    private String threeLetterAbbreviation;
    @SerializedName("full_name_locale")
    @Expose
    private String fullNameLocale;
    @SerializedName("full_name_english")
    @Expose
    private String fullNameEnglish;
    @SerializedName("available_regions")
    @Expose
    private List<AvailableRegion> availableRegions = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTwoLetterAbbreviation() {
        return twoLetterAbbreviation;
    }

    public void setTwoLetterAbbreviation(String twoLetterAbbreviation) {
        this.twoLetterAbbreviation = twoLetterAbbreviation;
    }

    public String getThreeLetterAbbreviation() {
        return threeLetterAbbreviation;
    }

    public void setThreeLetterAbbreviation(String threeLetterAbbreviation) {
        this.threeLetterAbbreviation = threeLetterAbbreviation;
    }

    public String getFullNameLocale() {
        return fullNameLocale;
    }

    public void setFullNameLocale(String fullNameLocale) {
        this.fullNameLocale = fullNameLocale;
    }

    public String getFullNameEnglish() {
        return fullNameEnglish;
    }

    public void setFullNameEnglish(String fullNameEnglish) {
        this.fullNameEnglish = fullNameEnglish;
    }

    public List<AvailableRegion> getAvailableRegions() {
        return availableRegions;
    }

    public void setAvailableRegions(List<AvailableRegion> availableRegions) {
        this.availableRegions = availableRegions;
    }

}
