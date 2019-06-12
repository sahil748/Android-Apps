package com.example.retroassignment.model;

import com.google.gson.annotations.SerializedName;

public class UserCompany {
    @SerializedName("name")
    private String companyName;
    @SerializedName("catchPhrase")
    private String catchPhrase;
    @SerializedName("bs")
    private String bs;

    public String getCompanyName() {
        return companyName;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public String getBs() {
        return bs;
    }
}
