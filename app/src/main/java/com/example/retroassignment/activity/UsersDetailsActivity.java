package com.example.retroassignment.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retroassignment.R;
import com.example.retroassignment.adapter.UsersDetailsAdapter;
import com.example.retroassignment.constant.Constant;
import com.example.retroassignment.model.User;
import com.example.retroassignment.network.GetDataService;
import com.example.retroassignment.network.RetrofitUserInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersDetailsActivity extends AppCompatActivity {
    private ArrayList<User> mUserArrayList = new ArrayList<>();
    private TextView tvName;
    private TextView tvId;
    private TextView tvEmail;
    private Button btnFetchPost,btnMoreDetails;
    private RecyclerView recyclerView;
    private UsersDetailsAdapter userDetailsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GetDataService getDataService;
    private ProgressDialog progress;
    private int adapterPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_details);
        init();
        getUsersData();
    }

    private void init() {
        tvName = findViewById(R.id.tv_frag_user_name);
        tvId = findViewById(R.id.tv_frag_user_id);
        tvEmail = findViewById(R.id.tv_frag_user_email);

        btnFetchPost = findViewById(R.id.btn_fetch_post);
        btnMoreDetails=findViewById(R.id.btn_more_details);

        recyclerView = findViewById(R.id.rv_user_list);
        progress = ProgressDialog.show(this, getString(R.string.progress_title), getString(R.string.progress_body), true);


        userDetailsAdapter = new UsersDetailsAdapter(mUserArrayList);
        mLayoutManager = new LinearLayoutManager(UsersDetailsActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(userDetailsAdapter);
        recyclerTouchHandle();
        fetchUserPost();
        showMoreDetails();
        getDataService = RetrofitUserInstance.getUser().create(GetDataService.class);
    }

    //Fetch User Posts
    private void fetchUserPost() {
        btnFetchPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersDetailsActivity.this, UsersPostActivity.class);
                if (tvId.getText().toString().equals(Constant.USER_CURRENT_ID)) {
                    Toast.makeText(UsersDetailsActivity.this, getString(R.string.no_user_click_text), Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra(Constant.USER_ID, tvId.getText().toString());
                    intent.putExtra(Constant.USER_NAME, tvName.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
    private void showMoreDetails(){
        btnMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvId.getText().toString().equals(Constant.USER_CURRENT_ID)) {
                    Toast.makeText(UsersDetailsActivity.this, getString(R.string.no_user_click_text), Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UsersDetailsActivity.this,R.style.MoreDetailsDialog);
                    builder.setTitle(R.string.more_details_dialog_Title);
                    builder.setMessage( getString(R.string.more_details_id) + mUserArrayList.get(adapterPosition).getUserId() + "\n" +
                            getString(R.string.more_details_name) + mUserArrayList.get(adapterPosition).getName() + "\n" +
                            getString(R.string.more_details_userName) + mUserArrayList.get(adapterPosition).getUserName() + "\n" +
                            getString(R.string.more_details_phone) + mUserArrayList.get(adapterPosition).getPhone() + "\n" +
                            getString(R.string.more_details_website) + mUserArrayList.get(adapterPosition).getWebsite() + "\n\n" +
                            getString(R.string.more_details_address)+"\n"+
                            getString(R.string.more_details_street) + mUserArrayList.get(adapterPosition).getUserAddress().getStreet() + "\n" +
                            getString(R.string.more_details_city) + mUserArrayList.get(adapterPosition).getUserAddress().getCity() + "\n" +
                            getString(R.string.more_details_suite) + mUserArrayList.get(adapterPosition).getUserAddress().getSuite() + "\n" +
                            getString(R.string.more_details_Zipcode) + mUserArrayList.get(adapterPosition).getUserAddress().getZipcode() + "\n\n" +
                            getString(R.string.more_details_company)+"\n"+
                            getString(R.string.more_details_companyName)+ mUserArrayList.get(adapterPosition).getCompany().getCompanyName()+"\n"+
                            getString(R.string.more_details_catchPhrase)+ mUserArrayList.get(adapterPosition).getCompany().getCatchPhrase()+"\n"+
                            getString(R.string.more_details_bs)+ mUserArrayList.get(adapterPosition).getCompany().getBs()+"\n");

                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
                    dialog.show();
                }
            }



        });
    }

    //Get All Users List
    private void getUsersData() {

        Call<List<User>> call = getDataService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> user = response.body();
                for (int i = 0; i < user.size(); i++) {
                    mUserArrayList.add(new User(user.get(i).getUserId(), user.get(i).getName(),user.get(i).getUserName(), user.get(i).getUserEmail(), user.get(i).getUserAddress(),user.get(i).getPhone(),user.get(i).getWebsite(),user.get(i).getCompany()));
                }
                userDetailsAdapter.notifyDataSetChanged();
                progress.dismiss();
                Toast.makeText(UsersDetailsActivity.this, getString(R.string.data_fetch_successful), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progress.dismiss();
                showErrorDialog();
                Toast.makeText(UsersDetailsActivity.this, getString(R.string.error_fetching_data), Toast.LENGTH_LONG).show();
            }
        });
    }
    //Recycler Touch Handling
    private void recyclerTouchHandle() {
        userDetailsAdapter.setOnClickListener(new UsersDetailsAdapter.UserClickListener() {
            @Override
            public void onCLick(int position) {
                adapterPosition=position;
                User user = mUserArrayList.get(adapterPosition);
                setData(user.getName(), String.valueOf(user.getUserId()), user.getUserEmail());
            }
        });
    }

    /**
     * Sets Data on Display Format
     *
     * @param name as User Name
     * @param id   as User Id
     */
    private void setData(String name, String id, String email) {
        tvId.setText(id);
        tvName.setText(name);
        tvEmail.setText(email);
    }

    //Error Dialog if No Internet is there
    private void showErrorDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(UsersDetailsActivity.this);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Internet not available?");
        builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getUsersData();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
