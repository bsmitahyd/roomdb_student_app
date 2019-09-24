package com.ext.roomtodoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> {

    private Context mCtx;
    private List<Student> studentList;

    public StudentsAdapter(Context mCtx, List<Student> studentList) {
        this.mCtx = mCtx;
        this.studentList = studentList;
    }

    @Override
    public StudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_student, parent, false);
        return new StudentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentsViewHolder holder, int position) {
        Student t = studentList.get(position);
        holder.textViewName.setText(t.getName());
        holder.textViewAge.setText(t.getAge());
        holder.textViewEmail.setText(t.getEmail());


    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  textViewName, textViewAge, textViewEmail;

        public StudentsViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Student student = studentList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateStudentActivity.class);
            intent.putExtra("student", student);

            mCtx.startActivity(intent);
        }
    }
}