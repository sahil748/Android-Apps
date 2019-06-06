package com.example.assignment4.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment4.R;
import com.example.assignment4.models.StudentModel;

import java.util.ArrayList;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<StudentModel> mAl_StudentsDetails;
    public TextView tvName, tvRoll, tvClass;
    private RecyclerViewClickListener mClickListener;

    public StudentRecyclerViewAdapter(ArrayList<StudentModel> mAl_StudentsDetails, RecyclerViewClickListener mClickListener) {
        this.mAl_StudentsDetails = mAl_StudentsDetails;
        this.mClickListener = mClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            tvName = itemView.findViewById(R.id.model_student_details_tv_name);
            tvRoll = itemView.findViewById(R.id.model_student_details_tv_roll);
            tvClass = itemView.findViewById(R.id.model_student_details_tv_class);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClicked(v, getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.student_details, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        StudentModel student = mAl_StudentsDetails.get(i);
        tvName.setText(student.getName());
        tvRoll.setText(student.getRoll());
        tvClass.setText(student.getClassName());
    }

    @Override
    public int getItemCount() {
        return mAl_StudentsDetails.size();
    }

    public interface RecyclerViewClickListener {
        void onItemClicked(View view, int position);
    }
}