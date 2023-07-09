package com.example.trainingcenterproject;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.trainingcenterproject.trainee.Notification;

import java.util.ArrayList;
import java.util.List;

public class EditCourseActivity extends AppCompatActivity {

    private int courseId;
    private Spinner instructorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // Get the course ID from the intent extras
        courseId = getIntent().getIntExtra("courseId", -1);

        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText mainTopicsEditText = findViewById(R.id.mainTopicsEditText);
        EditText prerequisitesEditText = findViewById(R.id.prerequisitesEditText);
        instructorSpinner = findViewById(R.id.instructorSpinner);
        EditText registrationDeadlineEditText = findViewById(R.id.registrationDeadlineEditText);
        EditText startDateEditText = findViewById(R.id.startDateEditText);
        EditText scheduleEditText = findViewById(R.id.scheduleEditText);
        EditText venueEditText = findViewById(R.id.venueEditText);

        // Populate the instructor spinner
        populateInstructorSpinner();

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            // Retrieve the entered text from the EditText fields
            String newTitle = titleEditText.getText().toString();
            String newMainTopics = mainTopicsEditText.getText().toString();
            String newPrerequisites = prerequisitesEditText.getText().toString();
            String newInstructorEmail = instructorSpinner.getSelectedItem().toString();
            String newRegistrationDeadline = registrationDeadlineEditText.getText().toString();
            String newStartDate = startDateEditText.getText().toString();
            String newSchedule = scheduleEditText.getText().toString();
            String newVenue = venueEditText.getText().toString();

            // Update the course in the database
            DataBaseHelper db = new DataBaseHelper(this, "training", null, 1);
            boolean updated = db.updateCourse(
                    courseId,
                    newTitle,
                    newMainTopics,
                    newPrerequisites,
                    newInstructorEmail,
                    newRegistrationDeadline,
                    newStartDate,
                    newSchedule,
                    newVenue
            );

            // Show a toast message depending on whether the update was successful
            Toast.makeText(this, updated ? "Course updated successfully" : "Error updating course", Toast.LENGTH_SHORT).show();
            sendNotifications(newTitle,courseId);
        });
    }

    private void populateInstructorSpinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(EditCourseActivity.this, "training", null, 1);
        Cursor instructorCursor = dataBaseHelper.getAllInstructors();

        List<String> instructorEmails = new ArrayList<>();
        while (instructorCursor.moveToNext()) {
            int columnIndex = instructorCursor.getColumnIndex("email");
            if (columnIndex != -1) {
                instructorEmails.add(instructorCursor.getString(columnIndex));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instructorEmails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorSpinner.setAdapter(adapter);
    }

    private void sendNotifications(String name,int courseId){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(EditCourseActivity.this, "training", null, 1);
        Cursor cursor = dataBaseHelper.getTraineesForCourse(courseId);
        while (cursor.moveToNext()){
            dataBaseHelper.insertNotification(new Notification(cursor.getString(0),"A you take has been edited: " + name));
        }
    }
}
