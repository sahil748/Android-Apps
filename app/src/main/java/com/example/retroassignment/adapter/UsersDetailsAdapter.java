package com.example.retroassignment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.retroassignment.R;
import com.example.retroassignment.model.User;

import java.util.ArrayList;

public class UsersDetailsAdapter extends RecyclerView.Adapter {
    private ArrayList<User> mUserArrayList;
    private UserClickListener mUserClickListener;
    public UsersDetailsAdapter(ArrayList<User> mUserArrayList)
    {
        this.mUserArrayList = mUserArrayList;
    }
    @NonNull

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_user_list, viewGroup, false);
        return new UserViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        User user = mUserArrayList.get(i);
        UserViewHolder holder = (UserViewHolder) viewHolder;
        holder.tvUserId.setText(String.valueOf(user.getUserId()));
        holder.tvUserName.setText(user.getName());
        holder.tvUserEmail.setText(user.getUserEmail());

    }
    public int getItemCount() {
        return mUserArrayList.size();
    }

    //View Holder Class for holding view
    private class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivUserImage;
        private TextView tvUserName, tvUserId, tvUserEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserImage = itemView.findViewById(R.id.iv_user_image);
            tvUserId = itemView.findViewById(R.id.tv_user_id);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserEmail = itemView.findViewById(R.id.tv_user_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUserClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mUserClickListener.onCLick(position);
                        }
                    }
                }
            });
        }
    }

    /**
     * Interface for Click Listener
     */
    public interface UserClickListener {
        void onCLick(int position);
    }

    public void setOnClickListener(UserClickListener listener) {
        mUserClickListener = listener;
    }
}
