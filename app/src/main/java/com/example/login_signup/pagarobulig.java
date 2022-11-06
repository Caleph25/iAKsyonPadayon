package com.example.login_signup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class pagarobulig extends AppCompatActivity {

    ImageView btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagarobulig);

        btn1 = findViewById(R.id.accident_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlertBox();
            }
        });

        btn2 = findViewById(R.id.fire_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlertBox();
            }
        });

        btn3 = findViewById(R.id.disaster_button);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlertBox();
            }
        });
    }
    private void openAlertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(pagarobulig.this);
        builder.setTitle("W A R N I N G");
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setMessage("Sigurado ka bang gusto mong magpatuloy?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(pagarobulig.this, pagarobulig_takepicture.class));
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