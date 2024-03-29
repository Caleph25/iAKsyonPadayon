package com.example.login_signup;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static com.google.gson.internal.$Gson$Types.arrayOf;

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
import android.graphics.Path;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
    private float latDestination;
    private float lngDestination;
    private String POIDestinationName;
    private String POIDestinationImage;
    LocationManager locationManager;
    ProgressBar SHOW_PROGRESS;
    String address;
    private FusedLocationProviderClient client;
    TextView txtduration, txtdistance;
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
        txtduration =(TextView)findViewById(R.id.txtduration);
        txtdistance =(TextView)findViewById(R.id.txtdistance);
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
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Alternative Route");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.addMarker(markerOptions);
                setRouteAlternative(Float.parseFloat(String.valueOf(latLng.latitude)),Float.parseFloat(String.valueOf(latLng.longitude)),latDestination,lngDestination,POIDestinationName,POIDestinationImage);
            }
        });
        queue.add(jsonObjectRequest);
        // Set the map coordinates to Catbalogan Samar
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
                        Button button1 = (Button) findViewById(R.id.evacbutton);
                        button1.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                button1.setEnabled(true);
                                try {
                                    List<POIListData> myPOIListData= new ArrayList<>();
                                    JSONArray Jarray  = response.getJSONArray("maincategories");
                                    for (int i = 0; i < Jarray.length(); i++)
                                    {
                                        JSONObject Jasonobject = Jarray.getJSONObject(i);
                                        int fk_categoryId = Jasonobject.getInt("fk_categoryid");
                                        String POIname = Jasonobject.getString("POIname");
                                        String POIdescription = Jasonobject.getString("POIdescription");
                                        String categoryImage = Jasonobject.getString("categoryImage");
                                        String POIlat = Jasonobject.getString("POIlat");
                                        String POIlng = Jasonobject.getString("POIlng");

                                        float xCurrentDistance=Float.parseFloat(String.valueOf(lat));
                                        float yCurrentDistance=Float.parseFloat(String.valueOf(lng));

                                        float xDestination= Float.parseFloat(POIlat);
                                        float yDestination= Float.parseFloat(POIlng);

//                                        LatLng Currentlocation = new LatLng(xCurrentDistance, yCurrentDistance);
//                                        LatLng Destinationlocation = new LatLng(xDestination, yDestination);
//                                        Double distance = SphericalUtil.computeDistanceBetween(Currentlocation, Destinationlocation);

                                        double lat1 = Math.toRadians(xCurrentDistance);
                                        double lat2 = Math.toRadians(xDestination);
                                        double lng1 = Math.toRadians(yCurrentDistance);
                                        double lng2 = Math.toRadians(yDestination);

                                        double dlon = lng2 - lng1;
                                        double dlat = lat2 - lat1;

                                        double a = Math.pow (Math.sin(dlat/2), 2)
                                                + Math.cos(lat1) * Math.cos(lat2)
                                                * Math.pow(Math.sin(dlon/2),2);

                                        double c = 2 * Math.asin(Math.sqrt(a));

                                        double r = 6371;

                                        String distance = String.valueOf(c * r);
                                        System.out.println(distance);
                                        if(fk_categoryId == 1) {
                                            myPOIListData.add(new POIListData(POIname, categoryImage, POIlat, POIlng,Float.parseFloat(String.valueOf(lat)),Float.parseFloat(String.valueOf(lng)), distance));
                                        }
                                    }
                                    POIListAdapter myPOIListAdapter= new POIListAdapter(myPOIListData, paglikas.this,con);
                                    recyclerView.setAdapter(myPOIListAdapter);

                                    Collections.sort(myPOIListData, new Comparator<POIListData>() {
                                        @Override
                                        public int compare(POIListData o1, POIListData o2) {
                                            return (int) (Float.parseFloat(o1.getDistance()) - Float.parseFloat(o2.getDistance()));
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Button button2 = (Button) findViewById(R.id.policebutton);
                        button2.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                try {
                                    List<POIListData> myPOIListData= new ArrayList<>();
                                    JSONArray Jarray  = response.getJSONArray("maincategories");
                                    for (int i = 0; i < Jarray.length(); i++)
                                    {
                                        JSONObject Jasonobject = Jarray.getJSONObject(i);
                                        int fk_categoryId = Jasonobject.getInt("fk_categoryid");
                                        String POIname = Jasonobject.getString("POIname");
                                        String POIdescription = Jasonobject.getString("POIdescription");
                                        String categoryImage = Jasonobject.getString("categoryImage");
                                        String POIlat = Jasonobject.getString("POIlat");
                                        String POIlng = Jasonobject.getString("POIlng");

                                        float xCurrentDistance=Float.parseFloat(String.valueOf(lat));
                                        float yCurrentDistance=Float.parseFloat(String.valueOf(lng));

                                        float xDestination= Float.parseFloat(POIlat);
                                        float yDestination= Float.parseFloat(POIlng);

//                                        LatLng Currentlocation = new LatLng(xCurrentDistance, yCurrentDistance);
//                                        LatLng Destinationlocation = new LatLng(xDestination, yDestination);
//                                        Double distance = SphericalUtil.computeDistanceBetween(Currentlocation, Destinationlocation);

                                        double lat1 = Math.toRadians(xCurrentDistance);
                                        double lat2 = Math.toRadians(xDestination);
                                        double lng1 = Math.toRadians(yCurrentDistance);
                                        double lng2 = Math.toRadians(yDestination);

                                        double dlon = lng2 - lng1;
                                        double dlat = lat2 - lat1;

                                        double a = Math.pow (Math.sin(dlat/2), 2)
                                                + Math.cos(lat1) * Math.cos(lat2)
                                                * Math.pow(Math.sin(dlon/2),2);

                                        double c = 2 * Math.asin(Math.sqrt(a));

                                        double r = 6371;

                                        String distance = String.valueOf(c * r);

                                        if(fk_categoryId == 3) {
                                            myPOIListData.add(new POIListData(POIname, categoryImage, POIlat, POIlng,Float.parseFloat(String.valueOf(lat)),Float.parseFloat(String.valueOf(lng)),distance));
                                        }
                                    }
                                    POIListAdapter myPOIListAdapter= new POIListAdapter(myPOIListData, paglikas.this,con);
                                    recyclerView.setAdapter(myPOIListAdapter);
                                    Collections.sort(myPOIListData, new Comparator<POIListData>() {
                                        @Override
                                        public int compare(POIListData o1, POIListData o2) {
                                            return (int) (Float.parseFloat(o1.getDistance()) - Float.parseFloat(o2.getDistance()));
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Button button3 = (Button) findViewById(R.id.hospitalbutton);
                        button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    List<POIListData> myPOIListData= new ArrayList<>();
                                    JSONArray Jarray  = response.getJSONArray("maincategories");
                                    for (int i = 0; i < Jarray.length(); i++)
                                    {
                                        JSONObject Jasonobject = Jarray.getJSONObject(i);
                                        int fk_categoryId = Jasonobject.getInt("fk_categoryid");
                                        String POIname = Jasonobject.getString("POIname");
                                        String POIdescription = Jasonobject.getString("POIdescription");
                                        String categoryImage = Jasonobject.getString("categoryImage");
                                        String POIlat = Jasonobject.getString("POIlat");
                                        String POIlng = Jasonobject.getString("POIlng");

                                        float xCurrentDistance=Float.parseFloat(String.valueOf(lat));
                                        float yCurrentDistance=Float.parseFloat(String.valueOf(lng));

                                        float xDestination= Float.parseFloat(POIlat);
                                        float yDestination= Float.parseFloat(POIlng);

//                                        LatLng Currentlocation = new LatLng(xCurrentDistance, yCurrentDistance);
//                                        LatLng Destinationlocation = new LatLng(xDestination, yDestination);
//                                        Double distance = SphericalUtil.computeDistanceBetween(Currentlocation, Destinationlocation);

                                        double lat1 = Math.toRadians(xCurrentDistance);
                                        double lat2 = Math.toRadians(xDestination);
                                        double lng1 = Math.toRadians(yCurrentDistance);
                                        double lng2 = Math.toRadians(yDestination);

                                        double dlon = lng2 - lng1;
                                        double dlat = lat2 - lat1;

                                        double a = Math.pow (Math.sin(dlat/2), 2)
                                                + Math.cos(lat1) * Math.cos(lat2)
                                                * Math.pow(Math.sin(dlon/2),2);

                                        double c = 2 * Math.asin(Math.sqrt(a));

                                        double r = 6371;

                                        String distance = String.valueOf(c * r);

                                        if(fk_categoryId == 4) {
                                            myPOIListData.add(new POIListData(POIname, categoryImage, POIlat, POIlng,Float.parseFloat(String.valueOf(lat)),Float.parseFloat(String.valueOf(lng)),distance));
                                        }
                                    }

                                    POIListAdapter myPOIListAdapter= new POIListAdapter(myPOIListData, paglikas.this,con);
                                    recyclerView.setAdapter(myPOIListAdapter);
                                    Collections.sort(myPOIListData, new Comparator<POIListData>() {
                                        @Override
                                        public int compare(POIListData o1, POIListData o2) {
                                            return (int) (Float.parseFloat(o1.getDistance()) - Float.parseFloat(o2.getDistance()));
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Button button4 = (Button) findViewById(R.id.fire);
                        button4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    List<POIListData> myPOIListData= new ArrayList<>();
                                    JSONArray Jarray  = response.getJSONArray("maincategories");
                                    for (int i = 0; i < Jarray.length(); i++)
                                    {
                                        JSONObject Jasonobject = Jarray.getJSONObject(i);
                                        int fk_categoryId = Jasonobject.getInt("fk_categoryid");
                                        String POIname = Jasonobject.getString("POIname");
                                        String POIdescription = Jasonobject.getString("POIdescription");
                                        String categoryImage = Jasonobject.getString("categoryImage");
                                        String POIlat = Jasonobject.getString("POIlat");
                                        String POIlng = Jasonobject.getString("POIlng");

                                        float xCurrentDistance=Float.parseFloat(String.valueOf(lat));
                                        float yCurrentDistance=Float.parseFloat(String.valueOf(lng));

                                        float xDestination= Float.parseFloat(POIlat);
                                        float yDestination= Float.parseFloat(POIlng);

//                                        LatLng Currentlocation = new LatLng(xCurrentDistance, yCurrentDistance);
//                                        LatLng Destinationlocation = new LatLng(xDestination, yDestination);
//                                        Double distance = SphericalUtil.computeDistanceBetween(Currentlocation, Destinationlocation);

                                        double lat1 = Math.toRadians(xCurrentDistance);
                                        double lat2 = Math.toRadians(xDestination);
                                        double lng1 = Math.toRadians(yCurrentDistance);
                                        double lng2 = Math.toRadians(yDestination);

                                        double dlon = lng2 - lng1;
                                        double dlat = lat2 - lat1;

                                        double a = Math.pow (Math.sin(dlat/2), 2)
                                                + Math.cos(lat1) * Math.cos(lat2)
                                                * Math.pow(Math.sin(dlon/2),2);

                                        double c = 2 * Math.asin(Math.sqrt(a));

                                        double r = 6371;

                                        String distance = String.valueOf(c * r);

                                        if(fk_categoryId == 2) {
                                            myPOIListData.add(new POIListData(POIname, categoryImage, POIlat, POIlng,Float.parseFloat(String.valueOf(lat)),Float.parseFloat(String.valueOf(lng)),distance));
                                        }
                                    }
                                    POIListAdapter myPOIListAdapter= new POIListAdapter(myPOIListData, paglikas.this,con);
                                    recyclerView.setAdapter(myPOIListAdapter);
                                    Collections.sort(myPOIListData, new Comparator<POIListData>() {
                                        @Override
                                        public int compare(POIListData o1, POIListData o2) {
                                            return (int) (Float.parseFloat(o1.getDistance()) - Float.parseFloat(o2.getDistance()));
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
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
        latDestination=DestinationX;
        lngDestination=DestinationY;
        POIDestinationName=DestinationName;
        POIDestinationImage=POIyImage;
        map.clear();
        LatLng location = new LatLng(CurrentX, CurrentY);
        LatLng deslocation = new LatLng(DestinationX, DestinationY);

        LatLng currentLatLng = new LatLng(CurrentX, CurrentY);
        map.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));

        String serverKey = "AIzaSyCacAXb0f0MCKnZNK7Xbev0FZT5K8lQTak";
        GoogleDirection.withServerKey(serverKey)
                .from(location)
                .to(deslocation)
                .alternativeRoute(true)
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        if (status.equals(RequestResult.OK)) {
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            Info durationInfo = leg.getDuration();
                            String duration = durationInfo.getText();

                            double lat1 = Math.toRadians(CurrentX);
                            double lat2 = Math.toRadians(DestinationX);
                            double lng1 = Math.toRadians(CurrentY);
                            double lng2 = Math.toRadians(DestinationY);

                            double dlon = lng2 - lng1;
                            double dlat = lat2 - lat1;

                            double a = Math.pow (Math.sin(dlat/2), 2)
                                    + Math.cos(lat1) * Math.cos(lat2)
                                    * Math.pow(Math.sin(dlon/2),2);

                            double c = 2 * Math.asin(Math.sqrt(a));

                            double r = 6371;
                            double answer = c * r;

                            String distance = String.format("%.2f",answer);
                            origin = new MarkerOptions().position(new LatLng(CurrentX, CurrentY)).title("You are here.").snippet("Distance: "+distance + "km" + " Duration: "+duration);
                            destination = new MarkerOptions().position(new LatLng(DestinationX, DestinationY)).title("Destination").snippet(DestinationName).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromURL(POIyImage)));
                            Marker markerOrigin = map.addMarker(new MarkerOptions().position(new LatLng(CurrentX, CurrentY)).title("You are here.").snippet("Distance: "+distance + "km " + "Duration: "+duration));
                            markerOrigin.showInfoWindow();
                            map.addMarker(destination);

                            txtduration.setText(duration);
                            txtdistance.setText(distance + " km");

                            int[] colors = {R.color.cyan, R.color.pusha, R.color.magenta};
                            for (int i = 0; i < direction.getRouteList().size(); i++){
                                Route route1 =direction.getRouteList().get(i);
                                Leg leg1 = route1.getLegList().get(0);
                                int color = ContextCompat.getColor(getApplicationContext(), colors[i%colors.length]);
                                ArrayList<LatLng> directionPositionList = leg1.getDirectionPoint();
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(),
                                        directionPositionList, 5, color);
                                map.addPolyline(polylineOptions);
                            }

                            //-----------Zooming the map according to marker bounds-------------\\
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(location);
                            builder.include(deslocation);
                            LatLngBounds bounds = builder.build();

                            int width = getResources().getDisplayMetrics().widthPixels;
                            int height = getResources().getDisplayMetrics().heightPixels;
                            int padding = (int) (width * 0.20); // offset from edges of the map 10% of screen

                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                            map.animateCamera(cu);
                        } else {
                            Toast.makeText(paglikas.this, "Error: No routes found (" + status + ")", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onDirectionFailure(Throwable t) {
                    }
                });
    }
    public void setRouteAlternative(float CurrentX, float CurrentY,float DestinationX, float DestinationY,String DestinationName,String POIyImage) {
        LatLng location = new LatLng(CurrentX, CurrentY);
        LatLng deslocation = new LatLng(DestinationX, DestinationY);
        LatLng currentLatLng = new LatLng(CurrentX, CurrentY);
        map.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        String serverKey = "AIzaSyCacAXb0f0MCKnZNK7Xbev0FZT5K8lQTak";
        GoogleDirection.withServerKey(serverKey)
                .from(location)
                .to(deslocation)
                .transportMode(TransportMode.TRANSIT)
                .transitMode(TransitMode.BUS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        if (status.equals(RequestResult.OK)) {
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            Info distanceInfo = leg.getDistance();
                            Info durationInfo = leg.getDuration();
                            String distance = distanceInfo.getText();
                            String duration = durationInfo.getText();

                            origin = new MarkerOptions().position(new LatLng(CurrentX, CurrentY)).title("You are here.").snippet("Distance: "+distance + "Duration: "+duration);
                            destination = new MarkerOptions().position(new LatLng(DestinationX, DestinationY)).title("Destination").snippet(DestinationName).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromURL(POIyImage)));
                            Marker markerOrigin = map.addMarker(new MarkerOptions().position(new LatLng(CurrentX, CurrentY)).title("You are here.").snippet("Distance: "+distance + "Duration: "+duration));
                            markerOrigin.showInfoWindow();
                            map.addMarker(destination);

                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(),
                                    directionPositionList, 5, Color.GREEN);
                            map.addPolyline(polylineOptions);
                            //-----------Zooming the map according to marker bounds-------------\\
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(location);
                            builder.include(deslocation);
                            LatLngBounds bounds = builder.build();

                            int width = getResources().getDisplayMetrics().widthPixels;
                            int height = getResources().getDisplayMetrics().heightPixels;
                            int padding = (int) (width * 0.20); // offset from edges of the map 10% of screen

                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                            map.animateCamera(cu);
                        } else {
                            Toast.makeText(paglikas.this, "Error: No routes found (" + status + ")", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onDirectionFailure(Throwable t) {
                    }
                });
    }
    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        ImageButton drive = (ImageButton) findViewById(R.id.drive);
        ImageButton walk = (ImageButton) findViewById(R.id.walk);
        ImageButton transit = (ImageButton) findViewById(R.id.transit);

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
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:" + "q="+ dest.latitude + "," + dest.longitude + "&" + mode));
        intent.setPackage("com.google.android.apps.maps");
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
            setRoute(Float.parseFloat(String.valueOf(lat)),Float.parseFloat(String.valueOf(lng)),latDestination,lngDestination,POIDestinationName,POIDestinationImage);
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
                lineOptions.color(Color.CYAN);
                lineOptions.geodesic(true);
                lineOptions.clickable(true);


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