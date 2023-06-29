package com.example.trainingcenterproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DeleteCourseActivity extends AppCompatActivity {

    private Spinner coursesSpinner;
    private Button deleteButton;
    private Button editButton;
    private Button viewStudentsButton;

    private Button makeCourseAvailableButton;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        coursesSpinner = findViewById(R.id.coursesSpinner);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        makeCourseAvailableButton = findViewById(R.id.makeCourseAvailableButton);
        viewStudentsButton = findViewById(R.id.viewStrudentsButton);

        db = new DataBaseHelper(this, "training", null, 1);

        // Populate Spinner with courses
        List<String> courses = new ArrayList<>();
        Cursor cursor = db.getAllCourses();

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndexOrThrow("courseId");
            int titleIndex = cursor.getColumnIndexOrThrow("title");
            if (idIndex >= 0 && titleIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                courses.add(id + ": " + title);
            } else {
                //Exceptions
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coursesSpinner.setAdapter(adapter);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the course ID from the selected item in the spinner
                String course = (String) coursesSpinner.getSelectedItem();
                if (course != null) {
                    int id = Integer.parseInt(course.split(":")[0]);

                    // Create a new intent for the EditCourseActivity
                    Intent intent = new Intent(DeleteCourseActivity.this, EditCourseActivity.class);

                    // Pass the course ID to the intent
                    intent.putExtra("courseId", id);

                    // Start the EditCourseActivity
                    startActivity(intent);
                }
            }
        });

        viewStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the course ID from the selected item in the spinner
                String course = (String) coursesSpinner.getSelectedItem();
                if (course != null) {
                    int id = Integer.parseInt(course.split(":")[0]);

                    // Create a new intent for the EditCourseActivity
                    Intent intent = new Intent(DeleteCourseActivity.this, CourseTraineeListActivity.class);

                    // Pass the course ID to the intent
                    intent.putExtra("courseId", id);

                    // Start the EditCourseActivity
                    startActivity(intent);
                }
            }
        });

        makeCourseAvailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the course ID from the selected item in the spinner
                String course = (String) coursesSpinner.getSelectedItem();
                if (course != null) {
                    int id = Integer.parseInt(course.split(":")[0]);

                    // Create a new intent for the EditCourseActivity
                    Intent intent = new Intent(DeleteCourseActivity.this, MakeCourseAvailableActivity.class);

                    // Pass the course ID to the intent
                    intent.putExtra("courseId", id);

                    // Start the EditCourseActivity
                    startActivity(intent);
                }
            }
        });

    }

    private void deleteCourse() {
        String course = (String) coursesSpinner.getSelectedItem();
        if (course == null) {
            Toast.makeText(this, "No course selected", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(course.split(":")[0]);
        boolean deleted = db.deleteCourse(id);

        if (deleted) {
            Toast.makeText(this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
            // Refresh the activity to update spinner contents
            finish();
            startActivity(getIntent());
        } else {
            Toast.makeText(this, "Error deleting course", Toast.LENGTH_SHORT).show();
        }
    }
}

