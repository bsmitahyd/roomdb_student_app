package com.ext.roomtodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addStudentActionButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addStudentActionButton=findViewById(R.id.floating_button_add);
        addStudentActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentsActivity.class);
                startActivity(intent);
            }
        });
        getTask();
    }

    private void getTask() {
        class GetTasks extends AsyncTask<Void, Void, List<Student>> {

            @Override
            protected List<Student> doInBackground(Void... voids) {
                List<Student> studentList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .studentDao()
                        .getAll();
                return studentList;
            }

            @Override
            protected void onPostExecute(List<Student> tasks) {
                super.onPostExecute(tasks);
                StudentsAdapter studentsAdapter = new StudentsAdapter(MainActivity.this, tasks);
                recyclerView.setAdapter(studentsAdapter);
            }
        }
        GetTasks getTasks = new GetTasks();
        getTasks.execute();
    }

}
