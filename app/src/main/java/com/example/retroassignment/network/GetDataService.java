package com.example.retroassignment.network;

import com.example.retroassignment.model.User;
import com.example.retroassignment.model.UserPosts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/posts")
    Call<List<UserPosts>> getUserPost(@Query("userId") int id);
}
