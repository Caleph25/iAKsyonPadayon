package com.example.login_signup;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class paglikas extends FragmentActivity implements OnMapReadyCallback,OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {
    RequestQueue queue;
    private GoogleMap map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    MarkerOptions origin, destination;
    private double lat;
    private double lng;
    LocationManager locationManager;
    ProgressBar SHOW_PROGRESS;
    String address;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestPermission();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_paglikas);
        queue = Volley.newRequestQueue(this);
        RecyclerView recyclerView = findViewById(R.id.maprecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SetPOIList(recyclerView,this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @SuppressLint("MissingPermission")
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(paglikas.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat=location.getLatitude();
                    lng= location.getLongitude();
                    TextView xCurrent = (TextView)findViewById(R.id.xCurrent);
                    xCurrent.setText(String.valueOf(lat));
                    TextView yCurrent = (TextView)findViewById(R.id.yCurrent);
                    yCurrent.setText(String.valueOf(lng));
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Display traffic.
        googleMap.setTrafficEnabled(false);
        String HOSTurl=ApiClient.VolletURL();
        String url = HOSTurl+"pointofinterest";
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
                            JSONArray Jarray  = response.getJSONArray("maincategories");
                            for (int i = 0; i < Jarray.length(); i++)
                            {
                                JSONObject Jasonobject = Jarray.getJSONObject(i);
                                Float POIlat =Float.parseFloat( Jasonobject.getString("POIlat"));
                                Float POIlng = Float.parseFloat(Jasonobject.getString("POIlng"));
                                String POIname = Jasonobject.getString("POIname");
                                String categoryImage = Jasonobject.getString("categoryImage");
                                LatLng markerPOsition = new LatLng(POIlat, POIlng);
                                googleMap.addMarker(new MarkerOptions()
                                        .position(markerPOsition)
                                        .title(POIname)
                                        .icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromURL(categoryImage))));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(paglikas.this, "Error 1" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                lat = marker.getPosition().latitude;
                lng = marker.getPosition().longitude;
                return false;
            }
        });
        queue.add(jsonObjectRequest);
        // Set the map coordinates to Kyoto Japan.
        LatLng samar = new LatLng(11.776840, 124.884630);
        // Set the map type to Hybrid.
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.mapstyleraw));
        // Move the camera to the map coordinates and zoom in closer.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(samar));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
    }
    public void SetPOIList(RecyclerView recyclerView, Context con) {
        String HOSTurl=ApiClient.VolletURL();
        String url = HOSTurl+"pointofinterest";
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
                            List<POIListData> myPOIListData= new ArrayList<>();
                            JSONArray Jarray  = response.getJSONArray("maincategories");
                            for (int i = 0; i < Jarray.length(); i++)
                            {
                                JSONObject Jasonobject = Jarray.getJSONObject(i);
                                String POIname = Jasonobject.getString("POIname");
                                String POIdescription = Jasonobject.getString("POIdescription");
                                String categoryImage = Jasonobject.getString("poiCategoryImage");
                                String POIlat = Jasonobject.getString("POIlat");
                                String POIlng = Jasonobject.getString("POIlng");

                                myPOIListData.add(new POIListData(POIname, categoryImage,POIlat,POIlng));
                            }
                            POIListAdapter myPOIListAdapter= new POIListAdapter(myPOIListData, paglikas.this,con);
                            recyclerView.setAdapter(myPOIListAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(paglikas.this, "Error 1" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }
    public void setRoute(float CurrentX, float CurrentY,float DestinationX, float DestinationY,String DestinationName,String POIyImage) {
        map.clear();
        Toast.makeText(paglikas.this, "Lat: " +String.valueOf(DestinationX)+" Long: " +String.valueOf(DestinationY), Toast.LENGTH_SHORT).show();
        origin = new MarkerOptions().position(new LatLng(CurrentX, CurrentY)).title("You are here.").snippet("origin");
        destination = new MarkerOptions().position(new LatLng(DestinationX, DestinationY)).title("Destination").snippet(DestinationName).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromURL(POIyImage)));

        map.addMarker(origin);
        map.addMarker(destination);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(origin.getPosition(), 10));
        String url = getDirectionsUrl(origin.getPosition(), destination.getPosition());
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
        LatLng currentLatLng = new LatLng(CurrentX, CurrentY);
        map.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Setting mode
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyB2X39piAmceakPNZePVI_Ptdytv_e1ZZY";
        return url;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder=new Geocoder(paglikas.this, Locale.getDefault());
            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            address=addresses.get(0).getAddressLine(0);
            SHOW_PROGRESS.setVisibility(View.GONE);
            lat=location.getLatitude();
            lng=location.getLongitude();
            TextView xCurrent = (TextView)findViewById(R.id.xCurrent);
            xCurrent.setText(String.valueOf(lat));
            TextView yCurrent = (TextView)findViewById(R.id.yCurrent);
            yCurrent.setText(String.valueOf(lng));
            Toast.makeText(paglikas.this, "Lat: " +String.valueOf(lat)+" Long: " +String.valueOf(lng), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

            for (int i = 0; i < result.size(); i++) {

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);

            }
            if (points.size() != 0)
                map.addPolyline(lineOptions);
        }
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
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
    }
    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        float CurrentX= (float) location.getLatitude();
        float CurrentY= (float) location.getLongitude();
        lat=CurrentX;
        lng=CurrentY;
        Toast.makeText(paglikas.this, "Lat: " +String.valueOf(CurrentX)+" Long: " +String.valueOf(CurrentY), Toast.LENGTH_SHORT).show();
        TextView xCurrent = (TextView)findViewById(R.id.xCurrent);
        xCurrent.setText(String.valueOf(CurrentX));
        TextView yCurrent = (TextView)findViewById(R.id.yCurrent);
        yCurrent.setText(String.valueOf(CurrentY));
        LatLng currentLatLng = new LatLng(CurrentX, CurrentY);
        map.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
        map.moveCamera(CameraUpdateFactory.zoomTo(30));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }
    private void showMissingPermissionError() {
    }

}