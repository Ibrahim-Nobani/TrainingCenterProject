package com.example.trainingcenterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Add_data() {
        Admin newAdmin1 =new Admin(22, "Ibra@admin.com", "CS", "Ibrahim","N");
        Admin newAdmin2 =new Admin(23, "Ibra2@admin.com", "CSE", "Ibrahim","N");
        Admin newAdmin3 =new Admin(24, "Ibraaaa@admin.com", "eCSE", "Ibrahim","N");

        DataBaseHelper dataBaseHelper =new DataBaseHelper(MainActivity.this,"training",null,1);
        dataBaseHelper.insertAdmin(newAdmin1);
        dataBaseHelper.insertAdmin(newAdmin2);
        dataBaseHelper.insertAdmin(newAdmin3);
    }

}