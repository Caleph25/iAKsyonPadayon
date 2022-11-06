package com.example.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class flood_pagandam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood_pagandam);

        BottomNavigationView bottomNavigationView =findViewById(R.id.nav_pagandam);
        //bottomNavigationView.setSelectedItemId(R.id.flood_drawer);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.fire_drawer:
//                        startActivity(new Intent(getApplicationContext(),pagandam.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.flood_drawer:
//                        return true;
//
//                    case R.id.earthquake_drawer:
//                        startActivity(new Intent(getApplicationContext(),earthquake_pagandam.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//
//                }
                return false;
            }
        });
        LinearLayout img1= findViewById(R.id.backarrow);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(flood_pagandam.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}