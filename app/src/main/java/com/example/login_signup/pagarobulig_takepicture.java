package com.example.login_signup;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login_signup.databinding.ActivityMainBinding;
import com.example.login_signup.databinding.ActivityPagarobuligTakepictureBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class pagarobulig_takepicture extends AppCompatActivity {

    ImageView imagecap;

    Button camopen, sendbtn;
    Bitmap captureImage;
    private final int GALLERY = 1;
    String button_name = "";
    String path;
    ActivityPagarobuligTakepictureBinding binding;
    public  static final int RequestPermissionCode  = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarobuligTakepictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
   //     EnableCameraWhileRunning();
        button_name = getIntent().getStringExtra("button_name");
        clickListeners();
    }
    private void clickListeners() {
        binding.selectImage.setOnClickListener(v->{
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY);
            } else {
                ActivityCompat.requestPermissions(pagarobulig_takepicture.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        });
        binding.save.setOnClickListener(v -> {
            uploadImage(createImage());
        });

    }
    //Start activity for result method to set captured image to imagecap after click
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    captureImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    binding.imageview.setImageBitmap(captureImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(pagarobulig_takepicture.this, "Failed to select image!", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
    public addImageRes createImage(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        captureImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String name = String.valueOf(Calendar.getInstance().getTimeInMillis());

        addImageRes imageRes = new addImageRes();
        imageRes.setReportImagePath(image);
        imageRes.setReportName(button_name);
        imageRes.setReportDescription(name);
        return imageRes;
    }
    //Upload Image Using Retrofit
    private void uploadImage(addImageRes imageRes){
        Call<addImageRes> call = ApiClient.getImages().uploadImage(imageRes);
        call.enqueue(new Callback<addImageRes>() {
            @Override
            public void onResponse(Call<addImageRes> call, Response<addImageRes> response) {
                if(response.isSuccessful()){
                    Toast.makeText(pagarobulig_takepicture.this, "Image Uploaded!" , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(pagarobulig_takepicture.this, "Response Failed" , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<addImageRes> call, @NonNull Throwable t) {
                Toast.makeText(pagarobulig_takepicture.this, "Error When Uploading Image!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}