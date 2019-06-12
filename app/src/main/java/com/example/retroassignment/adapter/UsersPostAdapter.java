package com.example.retroassignment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retroassignment.R;
import com.example.retroassignment.model.UserPosts;

import java.util.ArrayList;

public class UsersPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<UserPosts> userPostArrayList;
    public UsersPostAdapter(ArrayList<UserPosts> userPostArrayList) {
        this.userPostArrayList = userPostArrayList;
    }
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_user_post, viewGroup, false);
        return new UserPostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UserPosts posts = userPostArrayList.get(i);
        UserPostViewHolder holder = (UserPostViewHolder) viewHolder;
        holder.tvPostId.setText(String.valueOf(posts.getPostId()));
        holder.tvPostTitle.setText(posts.getPostTitle());
        holder.tvPostBody.setText(posts.getPostBody());
    }

    @Override
    public int getItemCount() {
        return userPostArrayList.size();
    }

    //Custom View Holder class for users post
    private class UserPostViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPostTitle, tvPostBody, tvPostId;

        public UserPostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostId = itemView.findViewById(R.id.tv_post_id);
            tvPostTitle = itemView.findViewById(R.id.tv_post_title);
            tvPostBody = itemView.findViewById(R.id.tv_post_body);
        }
    }
}
