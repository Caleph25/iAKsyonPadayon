package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class pagandam extends AppCompatActivity {
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pagandam);
        queue = Volley.newRequestQueue(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetMainCategory(recyclerView,this);
    }
    public void GetMainCategory(RecyclerView recyclerView, Context con) {
        String HOSTurl=ApiClient.VolletURL();
        String url =  HOSTurl + "maincategories";
        JsonObjectRequest
                jsonObjectRequest
                = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            List<MyPagAndamData> myPagAndamData = new ArrayList<>();
                            JSONArray Jarray  = response.getJSONArray("maincategories");
                            for (int i = 0; i < Jarray.length(); i++)
                            {
                                JSONObject Jasonobject = Jarray.getJSONObject(i);
                                String PMCname = Jasonobject.getString("PMCname");
                                String CategoryImageUrl = Jasonobject.getString("pagAdamImagePath");
                                Integer PMCid = Jasonobject.getInt("PMCid");
                                myPagAndamData.add(new MyPagAndamData(PMCid,PMCname, CategoryImageUrl));
                            }
                            MyPagAndamAdapter myPagAndamAdapter = new MyPagAndamAdapter(con,myPagAndamData, pagandam.this);
                            recyclerView.setAdapter(myPagAndamAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(pagandam.this, "Error 1" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }
    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}