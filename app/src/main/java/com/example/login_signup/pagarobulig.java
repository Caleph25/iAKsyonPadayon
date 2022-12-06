package com.example.login_signup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class pagarobulig extends AppCompatActivity {

    ImageView btn1, btn2, btn3;
    String button = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagarobulig);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        btn1 = findViewById(R.id.accident_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button = "Accident";
                openAlertBox(button);
            }
        });

        btn2 = findViewById(R.id.fire_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button = "Fire";
                openAlertBox(button);
            }
        });

        btn3 = findViewById(R.id.disaster_button);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button = "Disaster";
                openAlertBox(button);
            }
        });
    }
    private void openAlertBox(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(pagarobulig.this);
        builder.setTitle("W A R N I N G");
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setMessage("Sigurado ka bang gusto mong magpatuloy?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(pagarobulig.this, pagarobulig_takepicture.class);
                intent.putExtra("name",name);
                startActivity(intent);
                Toast.makeText(pagarobulig.this,  name  , Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}