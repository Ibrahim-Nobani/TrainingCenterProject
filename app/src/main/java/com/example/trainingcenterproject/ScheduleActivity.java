package com.example.trainingcenterproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity {

    private String email;
    Map<String, LinearLayout> dayLayouts;
    Map<String, String> dayNames;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        dayLayouts = new HashMap<>();
        dayNames = new HashMap<>();

        dayLayouts.put("mon", findViewById(R.id.monLayout));
        dayLayouts.put("tue", findViewById(R.id.tueLayout));
        dayLayouts.put("wed", findViewById(R.id.wedLayout));
        dayLayouts.put("thu", findViewById(R.id.thuLayout));
        dayLayouts.put("fri", findViewById(R.id.friLayout));
        dayLayouts.put("sat", findViewById(R.id.satLayout));
        dayLayouts.put("sun", findViewById(R.id.sunLayout));

        dayNames.put("mon", "Mon");
        dayNames.put("tue", "Tue");
        dayNames.put("wed", "Wed");
        dayNames.put("thu", "R");
        dayNames.put("fri", "Fri");
        dayNames.put("sat", "Sat");
        dayNames.put("sun", "Sun");

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ScheduleActivity.this,"training", null,1);
        email = getIntent().getStringExtra("email");
        Cursor allCoursesCursor = dataBaseHelper.getCurrentCoursesByInstructor(email);
        for (LinearLayout layout : dayLayouts.values()) {
            layout.removeAllViews();
        }
        while (allCoursesCursor.moveToNext()){
            String[] schedules = allCoursesCursor.getString(7).split(" ");
            for (int i = 0; i < schedules.length; i += 2) {
                String day = schedules[i];
                String time = schedules[i+1];
                TextView textView = new TextView(ScheduleActivity.this);
                textView.setText(dayNames.get(day) + "\n" + allCoursesCursor.getString(1) + "\n" + time);
                textView.setPadding(16, 16, 16, 16);
                textView.setTextColor(Color.BLACK); // change as per your need
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(10, 10, 10, 10);
                textView.setLayoutParams(params);
                dayLayouts.get(day).addView(textView);

            }
        }
    }
}