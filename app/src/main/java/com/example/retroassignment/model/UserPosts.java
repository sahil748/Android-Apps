package com.example.retroassignment.model;

import com.google.gson.annotations.SerializedName;

public class UserPosts {
    @SerializedName("id")
    private int postId;

    @SerializedName("title")
    private String postTitle;

    @SerializedName("body")
    private String postBody;

    public UserPosts(int postId, String postTitle, String postBody) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postBody = postBody;
    }

    public int getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

}
