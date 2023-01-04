package com.example.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.dashboard_drawer);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard_drawer:
                        return true;

                    case R.id.profile_drawer:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.aboutus_drawer:
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.call_drawer:
                        startActivity(new Intent(getApplicationContext(), ContactList.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.faq_drawer:
                        startActivity(new Intent(getApplicationContext(), FAQ.class));
                        overridePendingTransition(0, 0);
                        return true;


                }
                return false;
            }
        });


        TextView textview = findViewById(R.id.pagLikas_text);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, paglikas.class));
            }
        });

        TextView textView1 = findViewById(R.id.pagArobulig_text);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, pagarobulig.class));
            }
        });

        TextView textView2 = findViewById(R.id.pagAndam_text);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, pagandam.class));
            }
        });

        TextView textView3 = findViewById(R.id.reports_text);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, roadreports.class));
            }
        });

        TextView textView4 = findViewById(R.id.history_text);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReportHistory.class));
            }
        });

    }
}