package com.example.retroassignment.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retroassignment.R;
import com.example.retroassignment.adapter.UsersDetailsAdapter;
import com.example.retroassignment.helper.Constant;
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
    private RecyclerView mRecyclerView;
    private UsersDetailsAdapter mUserDetailsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GetDataService mGetDataService;
    private ProgressDialog mProgress;
    private int mAdapterPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_details);
        init();
        getUsersData();
    }

    private void init() {
        tvName = findViewById(R.id.rv_user_tv_name);
        tvId = findViewById(R.id.rv_user_tv_id);
        tvEmail = findViewById(R.id.rv_user_tv_email);

        btnFetchPost = findViewById(R.id.btn_fetch_post);
        btnMoreDetails=findViewById(R.id.btn_more_details);

        mRecyclerView = findViewById(R.id.rv_user_list);
        mProgress = ProgressDialog.show(this, getString(R.string.progress_title), getString(R.string.progress_body), true);
        mUserDetailsAdapter = new UsersDetailsAdapter(mUserArrayList);
        mLayoutManager = new LinearLayoutManager(UsersDetailsActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mUserDetailsAdapter);
        recyclerTouchHandle();
        fetchUserPost();
        showMoreDetails();
        mGetDataService = RetrofitUserInstance.getUser().create(GetDataService.class);
    }

    /**
     * shows User Posts
     * shows message when no user is selected
     * and details of user sent through intent
     */
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

    /**
     * shows all details of user through alert dialog
     * shows message when no user is selected to show details
     */
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
                    builder.setMessage( getString(R.string.more_details_id) + mUserArrayList.get(mAdapterPosition).getUserId() + "\n" +
                            getString(R.string.more_details_name) + mUserArrayList.get(mAdapterPosition).getName() + "\n" +
                            getString(R.string.more_details_userName) + mUserArrayList.get(mAdapterPosition).getUserName() + "\n" +
                            getString(R.string.more_details_phone) + mUserArrayList.get(mAdapterPosition).getPhone() + "\n" +
                            getString(R.string.more_details_website) + mUserArrayList.get(mAdapterPosition).getWebsite() + "\n\n" +
                            getString(R.string.more_details_address)+"\n"+
                            getString(R.string.more_details_street) + mUserArrayList.get(mAdapterPosition).getUserAddress().getStreet() + "\n" +
                            getString(R.string.more_details_city) + mUserArrayList.get(mAdapterPosition).getUserAddress().getCity() + "\n" +
                            getString(R.string.more_details_suite) + mUserArrayList.get(mAdapterPosition).getUserAddress().getSuite() + "\n" +
                            getString(R.string.more_details_Zipcode) + mUserArrayList.get(mAdapterPosition).getUserAddress().getZipcode() + "\n\n" +
                            getString(R.string.more_details_company)+"\n"+
                            getString(R.string.more_details_companyName)+ mUserArrayList.get(mAdapterPosition).getCompany().getCompanyName()+"\n"+
                            getString(R.string.more_details_catchPhrase)+ mUserArrayList.get(mAdapterPosition).getCompany().getCatchPhrase()+"\n"+
                            getString(R.string.more_details_bs)+ mUserArrayList.get(mAdapterPosition).getCompany().getBs()+"\n");

                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
                    dialog.show();
                }
            }



        });
    }

    /**
     * Get All Users List from server
     * add data from server to arraylist
     * shows error message on no internet connectivity
     */
    private void getUsersData() {

        Call<List<User>> call = mGetDataService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> user = response.body();
                for (int i = 0; i < user.size(); i++) {
                    mUserArrayList.add(new User(user.get(i).getUserId(), user.get(i).getName(),user.get(i).getUserName(), user.get(i).getUserEmail(), user.get(i).getUserAddress(),user.get(i).getPhone(),user.get(i).getWebsite(),user.get(i).getCompany()));
                }
                mUserDetailsAdapter.notifyDataSetChanged();
                mProgress.dismiss();
                Toast.makeText(UsersDetailsActivity.this, getString(R.string.data_fetch_successful), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mProgress.dismiss();
                showErrorDialog();
                Toast.makeText(UsersDetailsActivity.this, getString(R.string.error_fetching_data), Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * Recycler Touch Handling
     * set  user data to text views
     */
    private void recyclerTouchHandle() {
        mUserDetailsAdapter.setOnClickListener(new UsersDetailsAdapter.UserClickListener() {
            @Override
            public void onCLick(int position) {
                mAdapterPosition=position;
                User user = mUserArrayList.get(mAdapterPosition);
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
        builder.setMessage(getString(R.string.internet_not_available));
        builder.setPositiveButton(getString(R.string.positive_try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getUsersData();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
