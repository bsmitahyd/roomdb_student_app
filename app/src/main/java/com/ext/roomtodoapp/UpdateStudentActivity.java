package com.ext.roomtodoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {
    private EditText editTextName, editTextAge, editTextEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        init();
    }

    private void init() {
        editTextName = findViewById(R.id.edit_name);
        editTextAge = findViewById(R.id.edit_age);
        editTextEmail = findViewById(R.id.edit_email);


        final Student student=(Student) getIntent().getSerializableExtra("student");

    loadStudent(student);

    findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
            updateStudent(student);
        }
    });

    findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStudentActivity.this);
            builder.setTitle("Are you sure?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteTask(student);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog ad = builder.create();
            ad.show();
        }
    });
}

    private void loadStudent(Student student) {
        editTextName.setText(student.getName());
        editTextAge.setText(student.getAge());
        editTextEmail.setText(student.getEmail());
    }

    private void updateStudent(final Student student) {
        final String sName = editTextName.getText().toString().trim();
        final String sAge = editTextAge.getText().toString().trim();
        final String sEmail = editTextEmail.getText().toString().trim();

        if (sName.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (sAge.isEmpty()) {
            editTextAge.setError("Age required");
            editTextAge.requestFocus();
            return;
        }

        if (sEmail.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }

        class UpdateStudent extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                student.setName(sName);
                student.setAge(sAge);
                student.setEmail(sEmail);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .studentDao()
                        .update(student);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateStudentActivity.this, MainActivity.class));
            }
        }

        UpdateStudent ut = new UpdateStudent();
        ut.execute();
    }


    private void deleteTask(final Student student) {
        class DeleteStudent extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .studentDao()
                        .delete(student);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateStudentActivity.this, MainActivity.class));
            }
        }

        DeleteStudent dt = new DeleteStudent();
        dt.execute();

    }

}
