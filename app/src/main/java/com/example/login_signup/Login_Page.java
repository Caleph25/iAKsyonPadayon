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

import com.google.android.material.textfield.TextInputEditText;


public class Login_Page extends AppCompatActivity {
    TextInputEditText username, passwordlogin;
    Button callSignUp, btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

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

                boolean check = validationinfo(user_name, password_login);

                if(check == true){
                    Intent i = new Intent(Login_Page.this,MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Please check your information again",Toast.LENGTH_SHORT).show();
                }
            }

            private Boolean validationinfo(String user_name, String password_login) {
                if(user_name.length() == 0){
                    username.requestFocus();
                    username.setError("Username can't be blank");
                    return false;
                }else if(!user_name.matches("[a-zA-Z0-9_]+")){
                    username.requestFocus();
                    username.setError("Re-enter your name");
                    return false;
                }else if(password_login.length() <= 9){
                    passwordlogin.requestFocus();
                    passwordlogin.setError("Minimum 8 character required");
                    return false;
                }else{
                    return true;
                }
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

       LinearLayout imgbutton = findViewById(R.id.imgarrow);
       imgbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(Login_Page.this,MainActivity.class);
               startActivity(i);
           }
       });

    }
}