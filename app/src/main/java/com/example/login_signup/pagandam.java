package com.example.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;

public class pagandam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagandam);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyPagAndamData[] myPagAndamData = new MyPagAndamData[]{
                new MyPagAndamData("Fire", "Click for Subcategories", R.drawable.button_fireimg),
                new MyPagAndamData("Earthquake", "Click for Subcategories", R.drawable.button_earthquakeimg),
                new MyPagAndamData("Flood", "Click for Subcategories", R.drawable.button_floodimgimg),
                new MyPagAndamData("Car Accident", "Click for Subcategories", R.drawable.button_caraccidentimg)

        };

        MyPagAndamAdapter myPagAndamAdapter = new MyPagAndamAdapter(myPagAndamData, pagandam.this);
        recyclerView.setAdapter(myPagAndamAdapter);

    }

}