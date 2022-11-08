package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class pagandamSub extends AppCompatActivity {
    RequestQueue queue;
    String MainCategoryID;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagandam_sub);
        queue = Volley.newRequestQueue(this);
        MainCategoryID = getIntent().getStringExtra("MainCategoryID");
        Toast.makeText(this,"This is the MainCategoryID: " + MainCategoryID, Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetMainCategory(recyclerView);
    }
    public void GetMainCategory(RecyclerView recyclerView) {
        String url = "http://192.168.1.6:8000/api/subcategories/"+MainCategoryID;
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
                            List<MyPagAndamSubData> myPagAndamSubData = new ArrayList<>();
                            JSONArray Jarray  = response.getJSONArray("subcategories");
                            for (int i = 0; i < Jarray.length(); i++)
                            {
                                JSONObject Jasonobject = Jarray.getJSONObject(i);
                                String PSMCname = Jasonobject.getString("PSMCname");
                                String CategoryImageUrl = Jasonobject.getString("subCategoryImage");
                                Integer PSMCid = Jasonobject.getInt("PSMCid");
                                myPagAndamSubData.add(new MyPagAndamSubData(PSMCid,PSMCname,"", CategoryImageUrl));
                            }
                            MyPagAndamSubAdapter myPagAndamSubAdapter = new MyPagAndamSubAdapter(myPagAndamSubData, pagandamSub.this);
                            recyclerView.setAdapter(myPagAndamSubAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(pagandamSub.this, "Error 1" + error.toString(), Toast.LENGTH_SHORT).show();
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