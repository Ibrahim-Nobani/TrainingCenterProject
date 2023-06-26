package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddCourseActivity.this,"TrainingCenter",null,1);

        final EditText nameEditText = findViewById(R.id.courseNameEditText);
        final EditText mainTopicsEditText = findViewById(R.id.mainTopicsEditText);
        final EditText prerequisitesEditText = findViewById(R.id.prerequisitesEditText);
        final EditText instructorEmailEditText = findViewById(R.id.instructorEmailEditText);
        final EditText registrationDeadlineEditText = findViewById(R.id.registrationDeadlineEditText);
        final EditText startDateEditText = findViewById(R.id.startDateEditText);
        final EditText scheduleEditText = findViewById(R.id.scheduleEditText);
        final EditText venueEditText = findViewById(R.id.venueEditText);


        Button addCourseButton = (Button) findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course newCourse =new Course();
                if (nameEditText.getText().toString().isEmpty()) {
                    newCourse.setTitle("Nameless");
                } else {
                    newCourse.setTitle(nameEditText.getText().toString());
                }

                if (mainTopicsEditText.getText().toString().isEmpty()) {
                    newCourse.setMainTopics("No topics specified");
                } else {
                    newCourse.setMainTopics(mainTopicsEditText.getText().toString());
                }

                if (prerequisitesEditText.getText().toString().isEmpty()) {
                    newCourse.setPrerequisites("No prerequisites");
                } else {
                    newCourse.setPrerequisites(prerequisitesEditText.getText().toString());
                }

                if (instructorEmailEditText.getText().toString().isEmpty()) {
                    newCourse.setInstructorEmail("Unknown");
                } else {
                    newCourse.setInstructorEmail(instructorEmailEditText.getText().toString());
                }

                if (registrationDeadlineEditText.getText().toString().isEmpty()) {
                    newCourse.setRegistrationDeadline("No deadline");
                } else {
                    newCourse.setRegistrationDeadline(registrationDeadlineEditText.getText().toString());
                }

                if (startDateEditText.getText().toString().isEmpty()) {
                    newCourse.setStartDate("Unknown");
                } else {
                    newCourse.setStartDate(startDateEditText.getText().toString());
                }

                if (scheduleEditText.getText().toString().isEmpty()) {
                    newCourse.setSchedule("No schedule");
                } else {
                    newCourse.setSchedule(scheduleEditText.getText().toString());
                }

                if (venueEditText.getText().toString().isEmpty()) {
                    newCourse.setVenue("No venue specified");
                } else {
                    newCourse.setVenue(venueEditText.getText().toString());
                }
                dataBaseHelper.insertCourse(newCourse);
                //Intent intent = new Intent(AddCourseActivity.this, viewCourses.class);
                //AddCourseActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}