package com.example.trainingcenterproject;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;

        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.ContextThemeWrapper;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import com.example.trainingcenterproject.trainee.Notification;

public class PendingCoursesActivity extends AppCompatActivity {
    LinearLayout courseList;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_decision);

        courseList = findViewById(R.id.courseList);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PendingCoursesActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(PendingCoursesActivity.this,"training", null,1);
        Cursor allCoursesCursor = dataBaseHelper.getPendingCoursesAdmin();
        courseList.removeAllViews();
        while (allCoursesCursor.moveToNext()){
            final String courseName = allCoursesCursor.getString(0); // Save courseId as a final variable
            final String email = allCoursesCursor.getString(1); // Save email as a final variable

            CardView cardView = new CardView(new ContextThemeWrapper(PendingCoursesActivity.this, R.style.CardViewStyle), null, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            cardView.setLayoutParams(params);

            TextView textView = new TextView(PendingCoursesActivity.this);
            textView.setText(
                    "Course Name= "+ courseName
                            +"\nStudent Name= "+allCoursesCursor.getString(2) + " "  +allCoursesCursor.getString(3)
                            +"\n\n" );
            textView.setPadding(10,10,10,10);
            textView.setTextSize(18);
            textView.setTextColor(Color.parseColor("#000000"));

            Button acceptButton = new Button(PendingCoursesActivity.this);
            acceptButton.setText("Accept");
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseHelper.acceptRegistration(courseName, email);
                    sendNotifications(courseName,"Accepted",email);
                    onResume();
                }
            });

            Button rejectButton = new Button(PendingCoursesActivity.this);
            rejectButton.setText("Reject");
            rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseHelper.rejectRegistration(courseName, email);
                    sendNotifications(courseName,"Rejected",email);
                    onResume(); // Refresh the list
                }
            });

            LinearLayout cardContent = new LinearLayout(PendingCoursesActivity.this);
            cardContent.setOrientation(LinearLayout.VERTICAL);
            cardContent.addView(textView);
            cardContent.addView(acceptButton);
            cardContent.addView(rejectButton);
            cardView.addView(cardContent);

            courseList.addView(cardView);
        }
    }

    private void sendNotifications(String name, String result,String email){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(PendingCoursesActivity.this, "training", null, 1);
            dataBaseHelper.insertNotification(new Notification(email,"You Have been "+result+": " + name));
    }



}

