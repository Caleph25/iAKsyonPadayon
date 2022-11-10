package com.example.login_signup;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_signup.databinding.ActivityMainBinding;
import com.example.login_signup.databinding.ActivityPagarobuligTakepictureBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class pagarobulig_takepicture extends AppCompatActivity {

    ImageView imagecap;

    Button camopen, sendbtn;
    Bitmap captureImage;
    public final int GALLERY = 1888;
    String button_name = "";
    String path;
    private double lat;
    private double lng;
    ActivityPagarobuligTakepictureBinding binding;
    private FusedLocationProviderClient client;
    public  static final int RequestPermissionCode  = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarobuligTakepictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        button_name = getIntent().getStringExtra("button_name");
        if (ContextCompat.checkSelfPermission(pagarobulig_takepicture.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(pagarobulig_takepicture.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
        }
        clickListeners();
        requestXYLocation();
    }
    @SuppressLint("MissingPermission")
    private void requestXYLocation(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(pagarobulig_takepicture.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat=location.getLatitude();
                    lng= location.getLongitude();
                    Toast.makeText(pagarobulig_takepicture.this, "X:"+lat+" Y:"+lng, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void clickListeners() {
        binding.selectImage.setOnClickListener(v->{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, GALLERY);
        });
        binding.save.setOnClickListener(v -> {
            uploadImage(createImage());
        });

    }
    //Start activity for result method to set captured image to imagecap after click
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == this.RESULT_CANCELED){
            return;
        }
        if (requestCode == GALLERY && resultCode == Activity.RESULT_OK) {
                captureImage = (Bitmap) data.getExtras().get("data");
                binding.imageview.setImageBitmap(captureImage);
        }

    }

    public addImageRes createImage(){
        TextView name= findViewById(R.id.name);
        TextView desc= findViewById(R.id.descript);

        addImageRes imageRes = new addImageRes();
        imageRes.setReportName(String.valueOf(name.getText()));
        imageRes.setReportDescription(String.valueOf(desc.getText()));
        imageRes.setReportCategoryId(1);
        imageRes.setMobileUserAccountId(1);
        return imageRes;
    }
    //Upload Image Using Retrofit
    private void uploadImage(addImageRes imageRes){
        File file = bitmapToFile(getApplicationContext(),captureImage, "ReportedImage.png");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), requestFile);

        RequestBody ReportName =  RequestBody.create(imageRes.getReportName(), MediaType.parse("multipart/form-data"));
        RequestBody Description =  RequestBody.create(imageRes.getReportDescription(), MediaType.parse("multipart/form-data"));
        RequestBody CategoryId =  RequestBody.create(String.valueOf(imageRes.getReportCategoryId()), MediaType.parse("multipart/form-data"));
        RequestBody MobileUserAccountId =  RequestBody.create(String.valueOf(imageRes.getMobileUserAccountId()), MediaType.parse("multipart/form-data"));

        Call<addImageRes> call = ApiClient.getImages().uploadImage(ReportName,Description,CategoryId,MobileUserAccountId,body);
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
    public static File bitmapToFile(Context context,Bitmap bitmap, String fileNameToSave) {
        File f = new File(context.getCacheDir(), fileNameToSave);
        try {
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = null;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  f;
    }
}