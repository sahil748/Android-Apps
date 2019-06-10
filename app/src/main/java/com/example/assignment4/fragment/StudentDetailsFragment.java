package com.example.assignment4.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment4.background.StudentAsyncTaskHelper;
import com.example.assignment4.background.StudentIntentServiceHelper;
import com.example.assignment4.background.StudentServiceHelper;
import com.example.assignment4.database.StudentDatabaseHelper;
import com.example.assignment4.helper.Constants;
import com.example.assignment4.R;
import com.example.assignment4.helper.Util;


public class StudentDetailsFragment extends Fragment {
        private String mName,mRoll,mClassName;
        private EditText etName,etRoll,etClassName;
        private Button btnAdd;
        private OnAddFragmentListener fragmentCaller;
        Util utl;
        StudentDatabaseHelper myDb;
        BroadcastReceiver serviceBroadcastReceiver,intentServiceBroadcastReceiver;
        IntentFilter intentFilter = new IntentFilter();
        private String mButtonValue,mActionForAsync,mActionOfButton = Constants.BUTTON_ACTION_ASYNCTASK,mResultFromBroadcast;


        public StudentDetailsFragment() {
        }

        public static StudentDetailsFragment newInstance(Bundle bundle){
            StudentDetailsFragment fragment = new StudentDetailsFragment();
            if(bundle != null)
                fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {

            // Inflates the layout for this fragment
            final View view = inflater.inflate(R.layout.fragment_student_details, container, false);
            utl=new Util();
            initEditText(view);
            final Context context =getActivity();
            myDb = new StudentDatabaseHelper(context);

            intentFilter.addAction(Constants.BROADCAST_SERVICE_VALUE);
            intentFilter.addAction(Constants.BROADCAST_INTENTSERVICE_VALUE);
            context.registerReceiver(serviceBroadcastReceiver, intentFilter);
            context.registerReceiver(intentServiceBroadcastReceiver,intentFilter);

            serviceBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent resultIntent) {
                    String action = resultIntent.getAction();
                    if(action.equals(Constants.BROADCAST_SERVICE_VALUE)){
                        mResultFromBroadcast = resultIntent.getStringExtra(Constants.RECEIVER_SERVICE_VALUE);

                        utl.showToast(context,mResultFromBroadcast);
                    }
                }
            };

            intentServiceBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent resultIntent) {
                    String action = resultIntent.getAction();
                    if(action.equals(Constants.BROADCAST_INTENTSERVICE_VALUE)){
                        mResultFromBroadcast = resultIntent.getStringExtra(Constants.RECEIVER_INTENTSERVICE_VALUE);
                        utl.showToast(context,mResultFromBroadcast);
                    }
                }
            };

            etName.requestFocus();
            /**
             * send student details to other fragment on clicking add button using bundle
             */
            btnAdd = view.findViewById(R.id.fragment_studentDetails_btn_add);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utl=new Util();
                    etName.requestFocus();
                    mName = etName.getText().toString();
                    mRoll = etRoll.getText().toString();
                    mClassName = etClassName.getText().toString();
                    //for validating whether the fields entered are correct or not
                    mButtonValue = btnAdd.getText().toString();
                    if(mButtonValue.equals(Constants.BUTTON_VALUE_ADD)) {
                        getValues();
                        //for validating whether the fields entered are correct or not
                        if(utl.validName(context,mName,mRoll,mClassName)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle(getString(R.string.builder_title));
                            String[] options = {getString(R.string.dialog_btn_async),getString(R.string.dialog_btn_service),getString(R.string.dialog_btn_intent_service)};
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0 :
                                            mActionOfButton = Constants.BUTTON_ACTION_ASYNCTASK;
                                            mActionForAsync = Constants.ADD;
                                            StudentAsyncTaskHelper asyncTaskHelper = new StudentAsyncTaskHelper(context);
                                            asyncTaskHelper.execute(mActionForAsync,mName,mRoll,mClassName);
                                            Bundle bundle = new Bundle();
                                            addDataToBundle(bundle);
                                            fragmentCaller.addFragmentListener(bundle);
                                            break;
                                        case 1:
                                            mActionOfButton = Constants.BUTTON_ACTION_SERVICE;
                                            bundle = new Bundle();
                                            addDataToBundle(bundle);
                                            Intent intent = new Intent(context, StudentServiceHelper.class);
                                            addDataToIntent(intent);
                                            context.startService(intent);
                                            fragmentCaller.addFragmentListener(bundle);
                                            break;
                                        case 2:
                                            mActionOfButton = Constants.BUTTON_ACTION_INTENTSERVICE;
                                            bundle = new Bundle();
                                            addDataToBundle(bundle);
                                            Intent intent1 = new Intent(context, StudentIntentServiceHelper.class);
                                            addDataToIntent(intent1);
                                            context.startService(intent1);
                                            fragmentCaller.addFragmentListener(bundle);
                                            break;
                                    }
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    else if(mButtonValue.equals(Constants.BUTTON_VALUE_UPDATE)) {
                        getValues();
                        //for validating whether the fields entered are correct or not
                        if(utl.validName(context,mName,mRoll,mClassName)) {
                            Bundle bundle = new Bundle();
                            addDataToBundle(bundle);
                            fragmentCaller.addFragmentListener(bundle);
                        }
                    }
                }
            });
            Bundle viewBundle = getArguments();
            if(viewBundle != null) {
                String result = viewBundle.getString(Constants.KEY);
                if (result.equals(getString(R.string.value_view))) {
                    etName.setEnabled(false);
                    etRoll.setEnabled(false);
                    etClassName.setEnabled(false);
                    mName = viewBundle.getString(Constants.STUDENT_NAME);
                    mRoll = viewBundle.getString(Constants.STUDENT_ROLLNO);
                    mClassName = viewBundle.getString(Constants.STUDENT_CLASS);
                    etName.setText(mName);
                    etRoll.setText(mRoll);
                    etClassName.setText(mClassName);
                    btnAdd.setVisibility(View.INVISIBLE);
                }
            }
            return view;
        }
    @Override
    public void onResume(){
        super.onResume();
        getContext().registerReceiver(serviceBroadcastReceiver,intentFilter);
        getContext().registerReceiver(intentServiceBroadcastReceiver,intentFilter);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        getContext().unregisterReceiver(serviceBroadcastReceiver);
        getContext().unregisterReceiver(intentServiceBroadcastReceiver);
    }

    //function for getting the result from asyncTask and displaying a toast
    public void resultFromAsync(Context context,String result){
            utl=new Util();
        utl.showToast(context,result);
    }
    public void getValues(){
        mName = etName.getText().toString();
        mRoll = etRoll.getText().toString();
        mClassName = etClassName.getText().toString();
    }
    public void addDataToBundle(Bundle bundle)
    {
        bundle.putString(Constants.BUTTON_ACTION_KEY,mActionOfButton);
        bundle.putString(Constants.STUDENT_NAME, mName);
        bundle.putString(Constants.STUDENT_ROLLNO, mRoll);
        bundle.putString(Constants.STUDENT_CLASS, mClassName);
    }
    public void addDataToIntent(Intent intent)
    {
        intent.putExtra(Constants.KEY,Constants.ADD);
        intent.putExtra(Constants.STUDENT_NAME, mName);
        intent.putExtra(Constants.STUDENT_ROLLNO, mRoll);
        intent.putExtra(Constants.STUDENT_CLASS, mClassName);
    }

        //this is self defined method for getting the edit texts from the layouts
        public void initEditText(View view){
            etName = view.findViewById(R.id.fragment_studentDetails_et_name);
            etName.requestFocus();
            etRoll = view.findViewById(R.id.fragment_studentDetails_et_rollno);
            etClassName = view.findViewById(R.id.fragment_studentDetails_et_class);
        }

        public void instantiateAddListener(OnAddFragmentListener fragmentCaller){
            this.fragmentCaller=fragmentCaller;
        }

        //for clearing the edit text when new add student button is clicked
        public void clearEditText(){
            etName.getText().clear();
            etRoll.getText().clear();
            etClassName.getText().clear();
            etName.requestFocus();
            etRoll.setEnabled(true);
            btnAdd.setText(getString(R.string.add_student_btn));

        }

        //for setting the text to textviews
        public void setEditText(Bundle bundle) {
            if (bundle != null) {
                etName.setText(bundle.getString(Constants.STUDENT_NAME));
                etRoll.setText(bundle.getString(Constants.STUDENT_ROLLNO));
                etClassName.setText(bundle.getString(Constants.STUDENT_CLASS));
                btnAdd.setText(getString(R.string.studentDetailsActivity_btn_updateText));
                etRoll.setEnabled(false);
            }
        }

        public interface OnAddFragmentListener {
            void addFragmentListener(Bundle bundle);
        }

    }
