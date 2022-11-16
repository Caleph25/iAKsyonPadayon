package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Signup_Page extends AppCompatActivity {
    Button btnihave, btnsignup;
    private TextInputEditText fistname, middlename, lastname, username, email, password, contacno, gender, birthday;
    CheckBox terms;

    @SuppressLint("MissingPermission")
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
        contacno = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        gender = findViewById(R.id.gender);
        birthday = findViewById(R.id.birthday);
        btnsignup = findViewById(R.id.signupbutton);
        terms = findViewById(R.id.simpleCheckBox);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser(createRequest());
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

//                String fname = fistname.getText().toString();
//                String mname = middlename.getText().toString();
//                String lname = lastname.getText().toString();
//                String uname = username.getText().toString();
//                String semail = email.getText().toString();
//                String num = contacno.getText().toString();
//                String password_signup = password.getText().toString();
//                String gen = gender.getText().toString();
//                String bday = birthday.getText().toString();
//                String term = terms.getText().toString();
//
//
//                boolean check = validationinfo(fname, mname, lname, uname, semail, num, password_signup, gen, bday,term);
//
//                if (check == true) {
//
//                    Intent i = new Intent(Signup_Page.this, MainActivity.class);
//                    saveUser(createRequest());
//                    startActivity(i);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please check your information again", Toast.LENGTH_SHORT).show();
//                }
//            }
//            private boolean validationinfo(String fname, String mname, String lname, String uname, String semail, String num, String password_signup, String gen, String bday, String term) {
//                if (fname.length() == 0) {
//                    fistname.requestFocus();
//                    fistname.setError("First name cannot be empty");
//                    return false;
//                } else if (!fname.matches("^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$")) {
//                    fistname.requestFocus();
//                    fistname.setError("Re-enter Valid First name");
//                    return false;
//                } else if (mname.length() == 0) {
//                    middlename.requestFocus();
//                    middlename.setError("Middle name cannot be empty");
//                    return false;
//                } else if (!mname.matches("[a-zA-Z]+")) {
//                    middlename.requestFocus();
//                    middlename.setError("Re-enter Valid Middle Name");
//                    return false;
//                } else if (lname.length() == 0) {
//                    lastname.requestFocus();
//                    lastname.setError("Last name cannot be empty");
//                    return false;
//                } else if (!lname.matches("[a-zA-Z]+")) {
//                    lastname.requestFocus();
//                    lastname.setError("Re-enter Valid Last Name");
//                    return false;
//                } else if (uname.length() == 0) {
//                    username.requestFocus();
//                    username.setError("Username cannot be empty");
//                    return false;
//                } else if (!uname.matches("[a-zA-Z0-9_]+")) {
//                    username.requestFocus();
//                    username.setError("Re-enter valid Username");
//                    return false;
//                } else if (uname.length() <= 5) {
//                    username.requestFocus();
//                    username.setError("Username is too short; it should be longer than five characters.");
//                    return false;
//                } else if (semail.length() == 0) {
//                    email.requestFocus();
//                    email.setError("Email cannot be empty");
//                    return false;
//                } else if (!semail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
//                    email.requestFocus();
//                    email.setError("Re-enter Valid Email");
//                    return false;
//                } else if (num.length() == 0) {
//                    contacno.requestFocus();
//                    contacno.setError("Phone number cannot be empty");
//                    return false;
//                } else if (!num.matches("^[09][0-9]{10}$")) {
//                    contacno.requestFocus();
//                    contacno.setError("Phone number must start with 09 and contain 11 characters");
//                    return false;
//                } else if (password_signup.length() == 0) {
//                    password.requestFocus();
//                    password.setError("Password cannot be empty");
//                    return false;
//                }else if(!password_signup.matches(("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])+(?=.*[@#$_=+&*]).{8}"))){
//                    password.requestFocus();
//                    password.setError("Password is to Weak it must have at least 8 character that include 1 uppercase,1 number, and 1 special character (@#$_=+&*)");
//                    return false;
//                } else if (gen.length() == 0) {
//                    gender.requestFocus();
//                    gender.setError("Gender cannot be empty");
//                    return false;
//                } else if (!gen.matches("[a-zA-Z]+")) {
//                    gender.requestFocus();
//                    gender.setError("Re-enter Valid Gender");
//                    return false;
//                } else if (bday.length() == 0) {
//                    birthday.requestFocus();
//                    birthday.setError("Birthday cannot be empty");
//                    return false;
//                } else if (!bday.matches("[0-9]+\\-[0-9]+\\-+[0-9]+")) {
//                    birthday.requestFocus();
//                    birthday.setError("Re-enter valid birthday (yyyy-mm-dd)");
//                    return false;
//                }else if (term.length() == 0) {
//                    terms.requestFocus();
//                    terms.setError("Please check the terms and condition");
//                    return false;
//                } else {
//                    return true;
//                }
//
//            }
//        });

    }
    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname(fistname.getText().toString());
        userRequest.setMiddlename(middlename.getText().toString());
        userRequest.setLastname(lastname.getText().toString());
        userRequest.setContacno(contacno.getText().toString());
        userRequest.setEmail(email.getText().toString());
        userRequest.setGender(gender.getText().toString());
        userRequest.setBirthday(birthday.getText().toString());
        userRequest.setUsername(username.getText().toString());
        userRequest.setPassword(password.getText().toString());
        userRequest.setIsDeactivated(0);
        return userRequest;
    }
    public void saveUser(UserRequest userRequest){

        Call<UserResponse> userResponseCall = ApiClient.getUserService().saveUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    Intent i = new Intent(Signup_Page.this, MainActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(Signup_Page.this, "Field to request to Signup" , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(Signup_Page.this, "Signup request field" + t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}