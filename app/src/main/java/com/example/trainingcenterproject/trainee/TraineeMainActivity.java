package com.example.trainingcenterproject.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.trainingcenterproject.DataBaseHelper;
import com.example.trainingcenterproject.MainActivity;
import com.example.trainingcenterproject.R;

import java.util.ArrayList;

public class TraineeMainActivity extends AppCompatActivity {

    private String email;
    FragmentManager fragmentManager;
    MyCoursesFragment myCoursesFragment;

    TextView userName;
    TextView numberOfNotifications;

    ScrollView scrollView;
    Button notifications;
    Button editProfile;
    Button signOut;
    Button courseHistory;
    Button courseBrowser;
    Button myCourses;

    ScrollView notificationsScroll;
    LinearLayout notificationsLayout;

    ArrayList<Notification> notificationsList = new ArrayList<Notification>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_main);
        scrollView = (ScrollView) findViewById(R.id.trainee_scroll_courses);
        userName = (TextView) findViewById(R.id.trainee_name_display);
        numberOfNotifications = (TextView) findViewById(R.id.notifications_number_trainee);

        notifications = (Button) findViewById(R.id.trainee_notifications_button);
        editProfile = (Button) findViewById(R.id.edit_profile_trainee);
        signOut = (Button) findViewById(R.id.sign_out_trainee);
        courseHistory = (Button) findViewById(R.id.course_history_button_trainee);
        courseBrowser = (Button) findViewById(R.id.course_browser_trainee);
        myCourses = (Button) findViewById(R.id.trainee_courses);
        notificationsScroll = (ScrollView) findViewById(R.id.trainee_notification_scroll);
        notificationsScroll.setVisibility(View.GONE);
        notificationsLayout = (LinearLayout) findViewById(R.id.trainee_notification_layout);
        fragmentManager = getSupportFragmentManager();

        loadUserDetails();
        myCoursesFragment = new MyCoursesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        myCoursesFragment.setArguments(bundle);
        setButtonListener();
    }

    protected void onResume() {
        super.onResume();
        loadUserDetails();
        setNotifications();
    }

    private void setButtonListener(){

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationsScroll.getVisibility() == View.VISIBLE)
                    notificationsScroll.setVisibility(View.GONE);
                else
                    notificationsScroll.setVisibility(View.VISIBLE);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signOut = new Intent(TraineeMainActivity.this, MainActivity.class);
                startActivity(signOut);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(TraineeMainActivity.this, TraineeEditProfileActivity.class);
                startActivity(edit);
            }
        });

        courseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(TraineeMainActivity.this, Trainee_Course_History.class);
                startActivity(history);
            }
        });

        courseBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchwindow = new Intent(TraineeMainActivity.this, TraineeSearchCourses.class);
                startActivity(searchwindow);
            }
        });

        myCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (!myCoursesFragment.isAdded()) {
                    fragmentTransaction.add(R.id.trainee_scroll_courses, myCoursesFragment, "FirstFrag");
                } else {
                    fragmentTransaction.remove(myCoursesFragment);
                }
                fragmentTransaction.commit();
            }
        });
    }

    private void loadUserDetails() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeMainActivity.this,"training", null,1);
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        userName.setText("Hello, " + dataBaseHelper.getUserName(email));
        Bundle result = new Bundle();
        result.putString("userEmail", email);
    }

    private void setNotifications(){
        notificationsLayout.removeAllViews();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeMainActivity.this,"training", null,1);
        Cursor cursor = dataBaseHelper.getUserNotification(email);
        while(cursor.moveToNext()){
            TextView textView = new TextView(TraineeMainActivity.this);
            textView.setText(String.format("Content= %s\n", cursor.getString(1)));
            textView.setPadding(10,5,10,5);
            textView.setTextSize(18);
            textView.setTextColor(Color.parseColor("#000000"));
            notificationsLayout.addView(textView);
        }
    }


}