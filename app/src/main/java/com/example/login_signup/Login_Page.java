package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Login_Page extends AppCompatActivity {
    TextInputEditText username, passwordlogin;
    Button callSignUp, btnlogin;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        queue = Volley.newRequestQueue(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        username = findViewById(R.id.username);
        passwordlogin = findViewById(R.id.passwordlogin);
        btnlogin = findViewById(R.id.loginButton);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = username.getText().toString();
                String password_login = passwordlogin.getText().toString();
                String url = "http://192.168.1.6:8000/api/mobile_user_account?username="+user_name+"&password="+password_login;
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
                                    JSONArray Jarray  = response.getJSONArray("MobileUserAccount");
                                     if(Jarray.length()!=0){
                                         Intent i = new Intent(Login_Page.this,MainActivity.class);
                                         startActivity(i);
                                     }else{
                                         Toast.makeText(Login_Page.this, "Submission Field Please Re-enter your information", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Login_Page.this, "Submission Field Please Re-enter your information", Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(jsonObjectRequest);

            }
        });

        callSignUp = findViewById(R.id.signup_screen);
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Page.this,Signup_Page.class);
                startActivity(intent);
            }
        });
    }
}