package com.example.retroassignment.model;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int userId;

    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String userName;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("address")
    private UserAddress address;
    @SerializedName("company")
    private UserCompany company;
    @SerializedName("phone")
    private String phone;
    @SerializedName("website")
    private String website;
    public User(int userId, String name, String userName,String userEmail, UserAddress address,String phone,String website,UserCompany company) {
        this.userId = userId;
        this.name = name;
        this.userName=userName;
        this.userEmail = userEmail;
        this.address=address;
        this.phone=phone;
        this.website=website;
        this.company=company;
    }

    public String getUserName() {
        return userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public int getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public UserAddress getUserAddress()
    {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public UserCompany getCompany() {
        return company;
    }

    public String getWebsite() {
        return website;
    }
}
