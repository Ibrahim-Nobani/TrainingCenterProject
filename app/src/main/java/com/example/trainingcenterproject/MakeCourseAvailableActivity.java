package com.example.trainingcenterproject;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trainingcenterproject.trainee.Notification;

import java.util.ArrayList;
import java.util.List;

public class MakeCourseAvailableActivity extends AppCompatActivity {

    private int courseId;
    private String title;
    private String mainTopics;
    private String prerequisites;
    private Spinner instructorSpinner;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateRegSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_course_available);

        // Get the course ID from the intent extras
        courseId = getIntent().getIntExtra("courseId", -1);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MakeCourseAvailableActivity.this,"training", null,1);
        Course c  = dataBaseHelper.getCourse(courseId);
        title = c.getTitle();
        mainTopics = c.getMainTopics();
        prerequisites = c.getPrerequisites();

        instructorSpinner = findViewById(R.id.instructorSpinner);
        final EditText registrationDeadlineEditText = findViewById(R.id.registrationDeadlineEditText);
        EditText scheduleEditText = findViewById(R.id.scheduleEditText);
        EditText venueEditText = findViewById(R.id.venueEditText);
        final EditText startDateEditText = findViewById(R.id.startDateEditText);

        // Initialize date set listener
        dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            Log.d("MakeCourseAvailable", "onDateSet: mm/dd/yyy: " + month + "/" + dayOfMonth + "/" + year);
            String date = month + "/" + dayOfMonth + "/" + year;
            startDateEditText.setText(date);
        };

        startDateEditText.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    MakeCourseAvailableActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        //Registration date picker
        // Initialize date set listener
        dateRegSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            Log.d("MakeCourseAvailable", "onDateSet: mm/dd/yyy: " + month + "/" + dayOfMonth + "/" + year);
            String date = month + "/" + dayOfMonth + "/" + year;
            registrationDeadlineEditText.setText(date);
        };

        registrationDeadlineEditText.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    MakeCourseAvailableActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateRegSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });


        // Populate the instructor spinner
        populateInstructorSpinner();

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            // Retrieve the entered text from the EditText fields
            String newInstructorEmail = instructorSpinner.getSelectedItem().toString();
            String newRegistrationDeadline = registrationDeadlineEditText.getText().toString();
            String newStartDate = startDateEditText.getText().toString();
            String newSchedule = scheduleEditText.getText().toString();
            String newVenue = venueEditText.getText().toString();

            // Update the course in the database
            DataBaseHelper db = new DataBaseHelper(this, "training", null, 1);
            boolean updated = db.updateCourse(
                    courseId,
                    title,
                    mainTopics,
                    prerequisites,
                    newInstructorEmail,
                    newRegistrationDeadline,
                    newStartDate,
                    newSchedule,
                    newVenue
            );

            // Show a toast message depending on whether the update was successful
            Toast.makeText(this, updated ? "Course updated successfully" : "Error updating course", Toast.LENGTH_SHORT).show();
            sendNotifications(title);
        });
    }

    private void populateInstructorSpinner() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MakeCourseAvailableActivity.this, "training", null, 1);
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

    private void sendNotifications(String name){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MakeCourseAvailableActivity.this, "training", null, 1);
        Cursor cursor = dataBaseHelper.getAllTrainees();
        while (cursor.moveToNext()){
            dataBaseHelper.insertNotification(new Notification(cursor.getString(0),"A new Course is available: " + name));
        }
    }
}
