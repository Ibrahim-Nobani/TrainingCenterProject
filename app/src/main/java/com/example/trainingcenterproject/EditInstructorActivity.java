package com.example.trainingcenterproject;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class EditInstructorActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instructor);

        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        EditText addressEditText = findViewById(R.id.addressEditText);
        EditText specializationEditText = findViewById(R.id.specializationEditText);
        EditText degreeEditText = findViewById(R.id.degreeEditText);
        DataBaseHelper db = new DataBaseHelper(this, "training", null, 1);
        email = getIntent().getStringExtra("email");
        if (email == null) {
            Log.e("EditInstructorActivity", "Email is null!");
        } else {
            Log.i("EditInstructorActivity", "Email is: " + email);
        }

        Cursor res = db.getInstructor(email);

        if (res != null && res.moveToFirst()) {
            // Update the EditText fields with the current information
            firstNameEditText.setText(res.getString(1));
            lastNameEditText.setText(res.getString(2));
            passwordEditText.setText(res.getString(3));
            mobileNumberEditText.setText(res.getString(4));
            addressEditText.setText(res.getString(5));
            specializationEditText.setText(res.getString(6));
            degreeEditText.setText(res.getString(7));
        } else {
            Log.e("EditInstructorActivity", "Cursor is empty. No data found for the email: " + email);
        }

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            String newfirstName = firstNameEditText.getText().toString();
            String newlastName = lastNameEditText.getText().toString();
            String newpassword = passwordEditText.getText().toString();
            String newmobileNumber = mobileNumberEditText.getText().toString();
            String newaddress = addressEditText.getText().toString();
            String newspecialization = specializationEditText.getText().toString();
            String newdegree = degreeEditText.getText().toString();



            boolean updated = db.updateInstructor(
                    email,
                    newfirstName,
                    newlastName,
                    newpassword,
                    newmobileNumber,
                    newaddress,
                    newspecialization,
                    newdegree
            );

            // Show a toast message depending on whether the update was successful
            Toast.makeText(this, updated ? "Instructor updated successfully" : "Error updating Instructor", Toast.LENGTH_SHORT).show();
        });
    }
}