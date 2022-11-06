package com.example.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class earthquake_pagandam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_pagandam);

        BottomNavigationView bottomNavigationView =findViewById(R.id.nav_pagandam);
        //bottomNavigationView.setSelectedItemId(R.id.earthquake_drawer);

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
//                        startActivity(new Intent(getApplicationContext(),flood_pagandam.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.earthquake_drawer:
//                        return true;
//
//                }
                return false;
            }
        });

        LinearLayout img2= findViewById(R.id.backarrow);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(earthquake_pagandam.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}