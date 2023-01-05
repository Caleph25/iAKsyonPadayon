package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportHistory extends AppCompatActivity {
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_history);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        queue = Volley.newRequestQueue(this);
        RecyclerView recyclerView = findViewById(R.id.Historyrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setHistory(recyclerView, this);
    }
    public void setHistory(RecyclerView recyclerView, Context con){

        String HOSTurl=ApiClient.VolletURL();
        String url = HOSTurl+"ReportEvent";
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
                            List<ReportHistoryListData> myReportList = new ArrayList<>();
                            JSONArray Jarray  = response.getJSONArray("ReportEvent");
                            for (int i = 0; i < Jarray.length(); i++)
                            {
                                JSONObject Jasonobject = Jarray.getJSONObject(i);
                                int id = Integer.parseInt(Jasonobject.getString("ReportId"));
                                int fk_reportCategoryid = Integer.parseInt(Jasonobject.getString("fk_ReportCategoryId"));
                                int fk_mobileAccountid = Integer.parseInt(Jasonobject.getString("fk_MobileUserAccountId"));
                                String reportName = Jasonobject.getString("reportName");
                                String description = Jasonobject.getString("reportDescription");
                                String status = Jasonobject.getString("isCompleted");
                                String lat = Jasonobject.getString("lat");
                                String lng = Jasonobject.getString("lat");
                                String timeDate = Jasonobject.getString("created_at");

                                myReportList.add(new ReportHistoryListData(id, fk_reportCategoryid, fk_mobileAccountid,
                                        reportName,description, status, lat,lng, timeDate));

                            }
                            ReportHistoryAdapter myReportHistAdapter = new ReportHistoryAdapter(myReportList, ReportHistory.this, con);
                            recyclerView.setAdapter(myReportHistAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(ReportHistory.this, "Error 1" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }
}