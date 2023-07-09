package com.example.trainingcenterproject;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;




public class EditInstructorActivity extends AppCompatActivity {

    private int email;
    private Spinner instructorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instructor);

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        EditText addressEditText = findViewById(R.id.addressEditText);
        EditText specializationEditText = findViewById(R.id.specializationEditText);
        EditText degreeEditText = findViewById(R.id.degreeEditText);



        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            String newEmail = emailEditText.getText().toString();
            String newfirstName = firstNameEditText.getText().toString();
            String newlastName = lastNameEditText.getText().toString();
            String newpassword = passwordEditText.getText().toString();
            String newmobileNumber = mobileNumberEditText.getText().toString();
            String newaddress = addressEditText.getText().toString();
            String newspecialization = specializationEditText.getText().toString();
            String newdegree = degreeEditText.getText().toString();

            DataBaseHelper db = new DataBaseHelper(this, "training", null, 1);
            boolean updated = db.updateInstructor(
                    newEmail,
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