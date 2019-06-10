package com.example.assignment4.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment4.background.StudentAsyncTaskHelper;
import com.example.assignment4.background.StudentIntentServiceHelper;
import com.example.assignment4.background.StudentServiceHelper;
import com.example.assignment4.database.StudentDatabaseHelper;
import com.example.assignment4.helper.Constants;
import com.example.assignment4.R;
import com.example.assignment4.model.StudentModel;
import com.example.assignment4.activity.StudentDetailsActivity;
import com.example.assignment4.adapter.StudentRecyclerViewAdapter;

import java.util.ArrayList;


public class StudentsListFragment extends Fragment implements StudentRecyclerViewAdapter.RecyclerViewClickListener {


    private StudentListFragmentListener mListener;
    private RecyclerView mStudentRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<StudentModel> mStudentslist=new ArrayList<>();
    private TextView noDataFound;
    private Button mAddStudent;
    private String mName,mRoll,mClassName,mResultForAsync,mResultOfButton = Constants.BUTTON_ACTION_ASYNCTASK;;
    private int mViewPosition;
    private String result=Constants.ADD;

    private String mResult=Constants.ADD;
    StudentDatabaseHelper myDb;
    //default contructor
    public StudentsListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_students_list, container, false);
        setHasOptionsMenu(true);
        mStudentRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mStudentRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StudentRecyclerViewAdapter(mStudentslist,this);
        mStudentRecyclerView.setAdapter(mAdapter);

        noDataFound = view.findViewById(R.id.fragment_students_list_tv_nodatafound);
        final Bundle bundle = new Bundle();

        myDb = new StudentDatabaseHelper(getContext());
        Cursor res = myDb.getAllData();
        while(res.moveToNext()){
            mStudentslist.add(new StudentModel(res.getString(Constants.DB_FIRST_COLUMN),res.getString(Constants.DB_SECOND_COLUMN),res.getString(Constants.DB_THIRD_COLUMN)));
            mAdapter = new StudentRecyclerViewAdapter(mStudentslist,this);
            mStudentRecyclerView.setAdapter(mAdapter);
        }

        isDataAvailable(Constants.STUDENT_LIST_FILLED);
        mAddStudent=view.findViewById(R.id.fragment_studentsList_btn_add);
        mAddStudent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getResult(getString(R.string.value_add));
                bundle.putString(Constants.KEY,getString(R.string.value_add));
                mListener.studentFragment(bundle);
            }
        });

        return view;
    }

    /**
     *     method of interface RecyclerViewClickListener declared in recyclerAdapter performs the operation selected through dialog
     *     box
     * @param view        view of selected view
     * @param position    position of selected view
     */
    public void onItemClicked(View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        final Bundle bundle = new Bundle();
        mViewPosition=position;
        builder.setTitle(getString(R.string.builder_title));
        String[] options = {getString(R.string.dialog_btn_view),getString(R.string.dialog_btn_edit),getString(R.string.dialog_btn_delete)};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        dialogSelectedFunction(Constants.VIEW,bundle);
                        break;
                    case 1:
                        mViewPosition=position;
                        mName=mStudentslist.get(mViewPosition).getName();
                        mRoll=mStudentslist.get(mViewPosition).getRoll();
                        mClassName=mStudentslist.get(mViewPosition).getClassName();
                        bundle.putString(Constants.KEY,Constants.EDIT);
                        getResult(getString(R.string.Dialog_Option_Edit));
                        bundle.putString(Constants.STUDENT_NAME,mName);
                        bundle.putString(Constants.STUDENT_ROLLNO,mRoll);
                        bundle.putString(Constants.STUDENT_CLASS,mClassName);
                        mListener.studentFragment(bundle);
                        break;
//                        dialogSelectedFunction(Constants.EDIT,bundle);
//                        break;
                    case 2:
                        mViewPosition=position;
                        mName = mStudentslist.get(mViewPosition).getName();
                        mRoll = mStudentslist.get(mViewPosition).getRoll();
                        mClassName = mStudentslist.get(mViewPosition).getClassName();
                        mStudentslist.remove(mViewPosition);
                        checkResultAndDelete();
                        mAdapter.notifyItemRemoved(mViewPosition);
                        mAdapter.notifyDataSetChanged();
                        isDataAvailable(Constants.STUDENT_LIST_FILLED);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // set result key value ie edit or add
    public void getResult(String result){
        this.mResult = result;
    }

    //function for deleting selected view using the service which was selected when last entry was made
    public void checkResultAndDelete(){
        if(mResultOfButton.equals(Constants.BUTTON_ACTION_ASYNCTASK)) {
            mResultForAsync = Constants.DELETE;
            StudentAsyncTaskHelper asyncDelete = new StudentAsyncTaskHelper(getContext());
            asyncDelete.execute(mResultForAsync,mName,mRoll,mClassName);
        }
        else if(mResultOfButton.equals(Constants.BUTTON_ACTION_SERVICE)){
            Intent intent = new Intent(getContext(), StudentServiceHelper.class);
            deleteData(intent);
        }
        else if(mResultOfButton.equals(Constants.BUTTON_ACTION_INTENTSERVICE)){
            Intent intent = new Intent(getContext(), StudentIntentServiceHelper.class);
            deleteData(intent);
        }
    }

    public void deleteData(Intent intent){
        intent.putExtra(Constants.KEY,Constants.DELETE);
        intent.putExtra(Constants.STUDENT_NAME,mName);
        intent.putExtra(Constants.STUDENT_ROLLNO,mRoll);
        intent.putExtra(Constants.STUDENT_CLASS,mClassName);
        getContext().startService(intent);
    }


    //adds data to Arraylist and notify changes to adapter
    public void sendData(Bundle bundle){
        mResultOfButton= bundle.getString(Constants.BUTTON_ACTION_KEY);
        mName = bundle.getString(Constants.STUDENT_NAME);
        mRoll = bundle.getString(Constants.STUDENT_ROLLNO);
        mClassName = bundle.getString(Constants.STUDENT_CLASS);
        mResultOfButton= bundle.getString(Constants.BUTTON_ACTION_KEY);
        if(mResultOfButton.equals(Constants.BUTTON_ACTION_ASYNCTASK)) {
            if (mResult.equals(Constants.EDIT)) {
                mName = bundle.getString(Constants.STUDENT_NAME);
                mClassName = bundle.getString(Constants.STUDENT_CLASS);
                mResultForAsync = Constants.EDIT;
                StudentAsyncTaskHelper asyncEdit = new StudentAsyncTaskHelper(getContext());
                asyncEdit.execute(mResultForAsync, mName, mRoll, mClassName);
                mStudentslist.remove(mViewPosition);
                mStudentslist.add(mViewPosition, new StudentModel(mName, mRoll, mClassName));
                mAdapter = new StudentRecyclerViewAdapter(mStudentslist, this);
                mStudentRecyclerView.setAdapter(mAdapter);
            }
        }
        else if(mResultOfButton.equals(Constants.BUTTON_ACTION_SERVICE)){
            if (mResult.equals(Constants.EDIT)) {
                mName = bundle.getString(Constants.STUDENT_NAME);
                mClassName = bundle.getString(Constants.STUDENT_CLASS);
                Intent intent = new Intent(getContext(), StudentServiceHelper.class);
                putDataInIntent(intent);
                getContext().startService(intent);
                mStudentslist.remove(mViewPosition);
                mStudentslist.add(mViewPosition, new StudentModel(mName, mRoll, mClassName));
                mAdapter = new StudentRecyclerViewAdapter(mStudentslist, this);
                mStudentRecyclerView.setAdapter(mAdapter);

            }
        }
        else if(mResultOfButton.equals(Constants.BUTTON_ACTION_INTENTSERVICE)){
            if (mResult.equals(Constants.EDIT)) {
                mName = bundle.getString(Constants.STUDENT_NAME);
                mClassName = bundle.getString(Constants.STUDENT_CLASS);
                Intent intent = new Intent(getContext(), StudentIntentServiceHelper.class);
                putDataInIntent(intent);
                getContext().startService(intent);
                mStudentslist.remove(mViewPosition);
                mStudentslist.add(mViewPosition, new StudentModel(mName, mRoll, mClassName));
                mAdapter = new StudentRecyclerViewAdapter(mStudentslist, this);
                mStudentRecyclerView.setAdapter(mAdapter);

            }
        }
        isDataAvailable(Constants.STUDENT_LIST_EMPTY);
    }
    /**
     * add student details in bundle
     * @param bundle bundle in which info will be stored
     */

    public void putDataInBundle(Bundle bundle)
    {
        mName=mStudentslist.get(mViewPosition).getName();
        mRoll=mStudentslist.get(mViewPosition).getRoll();
        mClassName=mStudentslist.get(mViewPosition).getClassName();

        bundle.putString(Constants.STUDENT_NAME,mName);
        bundle.putString(Constants.STUDENT_ROLLNO,mRoll);
        bundle.putString(Constants.STUDENT_CLASS,mClassName);
    }
    public void putDataInIntent(Intent intent){
        intent.putExtra(Constants.KEY,Constants.EDIT);
        intent.putExtra(Constants.STUDENT_NAME,mName);
        intent.putExtra(Constants.STUDENT_ROLLNO,mRoll);
        intent.putExtra(Constants.STUDENT_CLASS,mClassName);
    }
    public void isDataAvailable(int value){
        if(value == Constants.STUDENT_LIST_FILLED) {
            if (mStudentslist.isEmpty())
                noDataFound.setVisibility(View.VISIBLE);
            else
                mStudentRecyclerView.setVisibility(View.VISIBLE);
        }
        else{
            if (!mStudentslist.isEmpty()) {
                noDataFound.setVisibility(View.INVISIBLE);
                mStudentRecyclerView.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * performs edit or view operation as per selected
     * @param choice     which operation is selected
     * @param bundle        bundle to send data to other fragment
     */
    public void dialogSelectedFunction(String choice,Bundle bundle)
    {

        putDataInBundle(bundle);
        if(choice.equals(Constants.VIEW))
        {
            bundle.putString(Constants.KEY,getString(R.string.value_view));
            Intent intent = new Intent(getActivity(), StudentDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
        {
            bundle.putString(Constants.KEY,getString(R.string.value_edit));
            getResult(getString(R.string.value_edit));
            mListener.studentFragment(bundle);
        }
    }
    public void instantiateListener(StudentListFragmentListener mListener){
        this.mListener=mListener;
    }
    //interface to send data between fragments
    public interface StudentListFragmentListener {
        void studentFragment(Bundle bundle);
    }
}