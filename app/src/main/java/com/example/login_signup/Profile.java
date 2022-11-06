package com.example.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        BottomNavigationView bottomNavigationView =findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.profile_drawer);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard_drawer:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile_drawer:
                        return true;

                    case R.id.aboutus_drawer:
                        startActivity(new Intent(getApplicationContext(),AboutUs.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.call_drawer:
                        startActivity(new Intent(getApplicationContext(),ContactList.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.logout_drawer:
                        startActivity(new Intent(getApplicationContext(),Login_Page.class));
                        overridePendingTransition(0,0);
                        return true;


                }

                return false;
            }
        });
    }
}