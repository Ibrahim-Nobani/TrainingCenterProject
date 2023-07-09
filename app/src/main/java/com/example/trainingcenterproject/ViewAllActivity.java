package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
public class ViewAllActivity extends AppCompatActivity {
    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout firstLinearLayout = new LinearLayout(this);
        Button backButton = new Button(this);
        mLinearLayout = new LinearLayout(this);
        ScrollView scrollView = new ScrollView(this);

        firstLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);

        backButton.setText("Back To Main");
        backButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        backButton.setPadding(20, 20, 20, 20);
        backButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        backButton.setTextColor(getResources().getColor(android.R.color.white));
        firstLinearLayout.addView(backButton);
        scrollView.addView(mLinearLayout);
        firstLinearLayout.addView(scrollView);

        setContentView(firstLinearLayout);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(ViewAllActivity.this, AdminDashboardActivity.class);
                ViewAllActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DataBaseHelper dbHelper = new DataBaseHelper(ViewAllActivity.this, "training", null, 1);
        mLinearLayout.removeAllViews();

        Cursor allTraineesCursor = dbHelper.getAllTrainees();
        addUsersToView("Trainees", allTraineesCursor);

        // Add a divider
        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        divider.setBackgroundColor(Color.GRAY);
        mLinearLayout.addView(divider);

        Cursor allInstructorsCursor = dbHelper.getAllInstructors();
        addUsersToView("Instructors", allInstructorsCursor);
    }

    private void addUsersToView(String userType, Cursor cursor) {
        TextView headerTextView = new TextView(ViewAllActivity.this);
        headerTextView.setText(userType);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setTextSize(20);
        headerTextView.setPadding(20, 20, 20, 20);
        mLinearLayout.addView(headerTextView);

        while (cursor.moveToNext()) {
            TextView userTextView = new TextView(ViewAllActivity.this);
            userTextView.setText(
                    "Email: " + cursor.getString(0)
                            + "\nFirst Name: " + cursor.getString(1)
                            + "\nLast Name: " + cursor.getString(2)
                            + "\n\n");
            userTextView.setPadding(40, 20, 20, 20);
            mLinearLayout.addView(userTextView);
        }
    }
}
