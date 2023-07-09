package com.example.trainingcenterproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
public class AdminDashboardActivity extends AppCompatActivity {
    private String email;
    private ImageView adminPhoto;
    private Bitmap defaultBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);
        defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.andrew);
        Button createCourseButton = findViewById(R.id.create_course_button);
        Button editDeleteCourseButton = findViewById(R.id.edit_delete_course_button);
        Button decisionRegistrationButton = findViewById(R.id.decision_registration_button);
        Button viewProfilesButton = findViewById(R.id.view_profiles_button);
        Button viewCoursesButton = findViewById(R.id.view_courses);
        adminPhoto = findViewById(R.id.admin_photo);
        DataBaseHelper dbHelper = new DataBaseHelper(this, "training", null, 1);
        email = getIntent().getStringExtra("email");
        if (email == null) {
            Log.e("Email", "Email is null!");
        } else {
            Log.i("Email", "Email is: " + email);
        }

        Cursor res = dbHelper.getAdminPhoto(email);

        if (res != null && res.moveToFirst()){
            String fileName = res.getString(0);
            if (fileName != null) {  // check if fileName is null
                try {
                    FileInputStream fis = openFileInput(fileName);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    adminPhoto.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    adminPhoto.setImageBitmap(defaultBitmap);
                    e.printStackTrace();
                } finally {
                    res.close();
                }
            } else {
                // fileName is null, set to default
                adminPhoto.setImageBitmap(defaultBitmap);
            }
        } else {
            // cursor is null or empty, set to default
            adminPhoto.setImageBitmap(defaultBitmap);
        }
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, AddCourseActivity.class);
                startActivity(addCourse);
            }
        });

        viewCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCourses = new Intent(AdminDashboardActivity.this, CourseListActivity.class);
                startActivity(viewCourses);
            }
        });

        editDeleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, DeleteCourseActivity.class);
                startActivity(addCourse);


            }
        });


        decisionRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, PendingCoursesActivity.class);
                startActivity(addCourse);
            }
        });

        viewProfilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourse = new Intent(AdminDashboardActivity.this, ViewAllActivity.class);
                startActivity(addCourse);
            }
        });


    }
}
