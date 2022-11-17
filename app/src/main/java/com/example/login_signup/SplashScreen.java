package com.example.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    ImageView splashTop, splashBottom, splashCenter, splashLines;
    TextView splashText;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //assign variable

//        splashTop = findViewById(R.id.splash_top);
//        splashBottom = findViewById(R.id.splash_bottom);
        splashCenter = findViewById(R.id.splash_center);
        splashLines = findViewById(R.id.splash_lines);
        splashText = findViewById(R.id.splash_text);

        //set full screening
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //initialize top animation

//        Animation animation1 = AnimationUtils.loadAnimation(this,
//                R.anim.top_wave);
//        //start top animation
//        splashTop.setAnimation(animation1);

        //Initialize object animator
        ObjectAnimator  objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                splashCenter,
                PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2f)

        );
        //set duration
        objectAnimator.setDuration(400);
        //set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //set repeat
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //set animator
        objectAnimator.start();

        //set animate text
        animatText("iAksyonPadayon.ph");

        //Initialize bottom animation
//        Animation animation2 = AnimationUtils.loadAnimation(this,
//                R.anim.botton_wave);
//        //Strat bottom animation
//        splashBottom.setAnimation(animation2);

        //initialize handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //redirect to main act
                startActivity(new Intent(SplashScreen.this, Login_Page.class));

                //Finish activity
                finish();
            }
        },5000);

    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            splashText.setText(charSequence.subSequence(0,index++));
            //check condition
            if(index <= charSequence.length()){
                //When index is equal to text length
                //run handler
                handler.postDelayed(runnable,delay);
            }

        }
    };
    //create animation text method
    public void animatText(CharSequence cs){
        //set text
        charSequence = cs;
        //clear index
        index = 0;
        //clear Text
        splashText.setText("");
        //Remove call back
        handler.removeCallbacks(runnable);
        //run handler
        handler.postDelayed(runnable,delay);
    }
}