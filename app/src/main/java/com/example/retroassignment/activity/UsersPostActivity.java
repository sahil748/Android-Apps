package com.example.retroassignment.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retroassignment.R;
import com.example.retroassignment.adapter.UsersPostAdapter;
import com.example.retroassignment.helper.Constant;
import com.example.retroassignment.model.UserPosts;
import com.example.retroassignment.network.GetDataService;
import com.example.retroassignment.network.RetrofitUserInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersPostActivity extends AppCompatActivity {

    private int userId;
    private String mUserName;
    private GetDataService mGetDataService;
    private ArrayList<UserPosts> mUserPostList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private TextView tvUserName;
    private TextView tvTotalPosts;
    private int mTotalPostCount;
    private RecyclerView mRecyclerView;
    private UsersPostAdapter mUserPostAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        init();
        getUserPosts();
    }

    /**
     * get and set data to tv
     */
    private void init() {
        tvUserName = findViewById(R.id.tv_user_name_post_view);
        tvTotalPosts = findViewById(R.id.tv_total_post_count);
        mRecyclerView = findViewById(R.id.rv_user_post);

        mUserPostAdapter = new UsersPostAdapter(mUserPostList);
        mLayoutManager = new LinearLayoutManager(UsersPostActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mUserPostAdapter);

        Intent intent = getIntent();
        userId = Integer.parseInt(intent.getStringExtra(Constant.USER_ID));
        mUserName = intent.getStringExtra(Constant.USER_NAME);
        tvUserName.setText(mUserName);
        mProgressDialog = ProgressDialog.show(this, getString(R.string.loading_text), getString(R.string.post_loading_text), true);
        mGetDataService = RetrofitUserInstance.getUser().create(GetDataService.class);
    }

    /**
     * get user posts from server
     * add user posts to arraylist
     * shows error message on failure
     */
    private void getUserPosts() {
        Call<List<UserPosts>> postCall = mGetDataService.getUserPost(userId);
        postCall.enqueue(new Callback<List<UserPosts>>() {
            @Override
            public void onResponse(Call<List<UserPosts>> call, Response<List<UserPosts>> response) {
                List<UserPosts> userPosts = response.body();
                for (int i = 0; i < userPosts.size(); i++) {
                    mTotalPostCount++;
                    mUserPostList.add(new UserPosts(userPosts.get(i).getPostId()
                            , userPosts.get(i).getPostTitle()
                            , userPosts.get(i).getPostBody()));
                }
                tvTotalPosts.setText(String.valueOf(mTotalPostCount));
                mUserPostAdapter.notifyDataSetChanged();
                Toast.makeText(UsersPostActivity.this, getString(R.string.data_fetch_successful), Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<UserPosts>> call, Throwable t) {
                mProgressDialog.dismiss();
                showErrorDialog();
            }
        });
    }

    /**
     * shows alert on no internet connectivity
     */
    private void showErrorDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(UsersPostActivity.this);
        builder.setTitle(R.string.Internet_fetch_error);
        builder.setMessage(R.string.internet_not_available);
        builder.setPositiveButton(R.string.positive_try_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getUserPosts();
            }
        });
        builder.setNegativeButton(R.string.negative_back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
