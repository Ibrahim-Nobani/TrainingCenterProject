package com.example.trainingcenterproject.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
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

    boolean called = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_main);
        scrollView = (ScrollView) findViewById(R.id.trainee_scroll_courses);
        userName = (TextView) findViewById(R.id.trainee_name_display);
        numberOfNotifications = (TextView) findViewById(R.id.notifications_number_trainee);
        numberOfNotifications.setVisibility(View.GONE);
        numberOfNotifications.setTextColor(Color.WHITE);
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
        bundle.putString("email", email);
        myCoursesFragment.setArguments(bundle);
        setButtonListener();
    }

    protected void onResume() {
        super.onResume();
        loadUserDetails();
        getNotificationsFromDB();
        popNotifications();
        callNotifications();
    }

    private void setButtonListener() {

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationsScroll.getVisibility() == View.VISIBLE)
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
        DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeMainActivity.this, "training", null, 1);
        SharedPreferences sharedPreferences = getSharedPreferences("TrainingCenterPrefs", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        userName.setText("Hello, " + dataBaseHelper.getUserName(email));
        Bundle result = new Bundle();
        result.putString("userEmail", email);
    }

    private void getNotificationsFromDB() {
        notificationsList.clear();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeMainActivity.this, "training", null, 1);
        Cursor cursor = dataBaseHelper.getUserNotification(email);
        while (cursor.moveToNext()) {
            notificationsList.add(new Notification(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
    }

    private void popNotifications() {
        notificationsLayout.removeAllViews();
        if (notificationsList.isEmpty()) {
            numberOfNotifications.setVisibility(View.GONE);
        } else {
            numberOfNotifications.setVisibility(View.VISIBLE);
            numberOfNotifications.setText(String.valueOf(notificationsList.size()));
            for (Notification notification : notificationsList) {
                LinearLayout linearLayout = new LinearLayout(TraineeMainActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.gravity = Gravity.CENTER;
                linearLayout.setLayoutParams(layoutParams);

                TextView textView = new TextView(TraineeMainActivity.this);
                textView.setText(String.format("Content= %s\n", notification.getContent()));
                textView.setPadding(10, 5, 10, 5);
                textView.setTextSize(18);
                textView.setTextColor(Color.parseColor("#000000"));

                Button button = new Button(TraineeMainActivity.this);
                button.setText("X");
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(TraineeMainActivity.this, "training", null, 1);
                        dataBaseHelper.deleteNotification(notification.getId());
                        getNotificationsFromDB();
                        popNotifications();
                    }
                });
                linearLayout.addView(textView);
                linearLayout.addView(button);
                notificationsLayout.addView(linearLayout);
            }
        }
    }

    public void callNotifications() {
        if(called)
            return;
        else
            called = true;

        for (Notification notification : notificationsList) {
            createNotification("test",notification.getContent());
        }
    }

    private static final String MY_CHANNEL_ID = "my_chanel_1";
    private static final String MY_CHANNEL_NAME = "My channel";

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, MY_CHANNEL_NAME, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

    }

    public void createNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MY_CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(body).setStyle(new NotificationCompat.BigTextStyle().bigText(body)).setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(123, builder.build());
        }
    }
}