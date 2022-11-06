package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Signup_Page extends AppCompatActivity {
    Button btnihave, btnsignup;
    TextInputEditText fistname, middlename, lastname, username, email, password, number,gender, birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        fistname = findViewById(R.id.fistname);
        middlename = findViewById(R.id.middlename);
        lastname = findViewById(R.id.lastname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        number = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        gender = findViewById(R.id.gender);
        birthday = findViewById(R.id.birthday);
        btnsignup = findViewById(R.id.signupbutton);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = fistname.getText().toString();
                String mname = middlename.getText().toString();
                String lname = lastname.getText().toString();
                String uname = username.getText().toString();
                String semail = email.getText().toString();
                String num = number.getText().toString();
                String password_signup= password.getText().toString();
                String gen=gender.getText().toString();
                String bday = birthday.getText().toString();

               boolean check = validationinfo(fname, mname, lname,uname,semail,num, password_signup, gen,bday);

                if(check == true){
                    Intent i = new Intent(Signup_Page.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Please check your information again",Toast.LENGTH_SHORT).show();
                }
            }

            private boolean validationinfo(String fname, String mname, String lname, String uname, String semail, String num, String password_signup, String gen, String bday) {
                if (fname.length() == 0) {
                    fistname.requestFocus();
                    fistname.setError("First Name cannot be empty");
                    return false;
                } else if (!fname.matches("^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$")) {
                    fistname.requestFocus();
                    fistname.setError("Re-enter First name");
                    return false;
                }else if(mname.length() == 0){
                    middlename.requestFocus();
                    middlename.setError("Middle name cannot be empty");
                    return false;
                }else if (!mname.matches("[a-zA-Z]+")) {
                    middlename.requestFocus();
                    middlename.setError("Re-enter Middle Name");
                    return false;
                }else if(lname.length() == 0) {
                    lastname.requestFocus();
                    lastname.setError("Last Name cannot be empty");
                    return false;
                }else if (!lname.matches("[a-zA-Z]+")) {
                    lastname.requestFocus();
                    lastname.setError("Re-enter Last Name");
                    return false;
                }else if(uname.length() == 0) {
                    username.requestFocus();
                    username.setError("Username cannot be empty");
                    return false;
                }else if (!uname.matches("[a-zA-Z0-9_]+")) {
                    username.requestFocus();
                    username.setError("Re-enter Username");
                    return false;
                }else if(semail.length() == 0) {
                    email.requestFocus();
                    email.setError("Email cannot be empty");
                    return false;
                }else if (!semail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    email.requestFocus();
                    email.setError("Re-enter Valid Email");
                    return false;
                }else if(num.length() == 0) {
                    number.requestFocus();
                    number.setError("Phone number cannot be empty");
                    return false;
                }else if (!num.matches("^[09][0-9]{10}$")) {
                    number.requestFocus();
                    number.setError("Phone number must begin with 09 and contain 11 characters.");
                    return false;
                }else if(password_signup.length() <= 9){
                    password.requestFocus();
                    password.setError("Minimum 8 character required");
                    return false;
                }else if(gen.length() == 0){
                    gender.requestFocus();
                    gender.setError("Gender cannot be empty");
                    return false;
                }else if (!gen.matches("[a-zA-Z]+")) {
                    gender.requestFocus();
                    gender.setError("Re-enter Gender");
                    return false;
                }else if(bday.length() == 0){
                    birthday.requestFocus();
                    birthday.setError("Birthday cannot be empty");
                    return false;
                }else if (!bday.matches("[0-9]+\\-[0-9]+\\-+[0-9]+")) {
                    birthday.requestFocus();
                    birthday.setError("Invalid Birthday it must be like this (dd-mm-yyyy)");
                    return false;
                }else{
                    return true;
                }
            }
        });

        btnihave = findViewById(R.id.login_screen);
        btnihave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_Page.this,Login_Page.class);
                startActivity(intent);
            }
        });


    }
}