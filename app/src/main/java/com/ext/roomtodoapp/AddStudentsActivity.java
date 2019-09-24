package com.ext.roomtodoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddStudentsActivity extends AppCompatActivity {
    private EditText editTextName, editTextAge, editTextEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        editTextName = findViewById(R.id.edit_name);
        editTextAge = findViewById(R.id.edit_age);
        editTextEmail = findViewById(R.id.edit_email);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

    }

    private void saveTask() {
        final String sName = editTextName.getText().toString().trim();
        final String sAge = editTextAge.getText().toString().trim();
        final String sEmail = editTextEmail.getText().toString().trim();

        if (sName.isEmpty()) {
            editTextName.setError("Name Required");
            editTextName.requestFocus();
            return;
        }

        if(sAge.isEmpty()){
            editTextAge.setError("Age Required");
            editTextAge.requestFocus();
            return;
        }
        if(sEmail.isEmpty()){
            editTextEmail.setError("Email Required");
            editTextEmail.requestFocus();
            return;
        }
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Student student = new Student();
                student.setName(sName);
                student.setAge(sAge);
                student.setEmail(sEmail);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .studentDao()
                        .insert(student);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}
