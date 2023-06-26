package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddAdminActivity.this,"TrainingCenter",null,1);

        final EditText emailEditText = findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        final EditText lastNameEditText = findViewById(R.id.lastNameEditText);

        Button addAdminButton = (Button) findViewById(R.id.addAdminButton);
        addAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Admin newAdmin = new Admin();
                if (emailEditText.getText().toString().isEmpty()) {
                    newAdmin.setEmail("No email specified");
                } else {
                    newAdmin.setEmail(emailEditText.getText().toString());
                }

                if (passwordEditText.getText().toString().isEmpty()) {
                    newAdmin.setPassword("No password specified");
                } else {
                    newAdmin.setPassword(passwordEditText.getText().toString());
                }

                if (firstNameEditText.getText().toString().isEmpty()) {
                    newAdmin.setFirstName("No first name specified");
                } else {
                    newAdmin.setFirstName(firstNameEditText.getText().toString());
                }

                if (lastNameEditText.getText().toString().isEmpty()) {
                    newAdmin.setLastName("No last name specified");
                } else {
                    newAdmin.setLastName(lastNameEditText.getText().toString());
                }

                dataBaseHelper.insertAdmin(newAdmin);
                //Intent intent = new Intent(AddCourseActivity.this, viewCourses.class);
                //AddCourseActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}