package com.example.login_signup;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class pagarobulig_takepicture extends AppCompatActivity {

    ImageView imagecap;
    Button camopen, sendbtn;
    String button = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String button_name = getIntent().getStringExtra("button_name");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagarobulig_takepicture);

        imagecap = findViewById(R.id.image_capture);
        camopen = findViewById(R.id.camera_open_btn);

        if (ContextCompat.checkSelfPermission(pagarobulig_takepicture.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(pagarobulig_takepicture.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
        }
        camopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        sendbtn = findViewById(R.id.sendbtn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                startActivityForResult(intent.creatChooser(intent, "Select Image"), G);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data"); //get capture image
            imagecap.setImageBitmap(captureImage);
        }
    }


}