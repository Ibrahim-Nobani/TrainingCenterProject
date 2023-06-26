package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminSignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    // Create a new admin instance
                    Admin admin = new Admin();
                    admin.setEmail(emailEditText.getText().toString());
                    admin.setPassword(passwordEditText.getText().toString());
                    admin.setFirstName(firstNameEditText.getText().toString());
                    admin.setLastName(lastNameEditText.getText().toString());

                    // Add the new admin to the database
                    DataBaseHelper dbHelper = new DataBaseHelper(AdminSignUpActivity.this, "training", null, 1);
                    dbHelper.insertAdmin(admin);
                    Toast.makeText(AdminSignUpActivity.this, "Admin Registered", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminSignUpActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs() {
        // Add your input validation logic here
        return true;
    }
}
