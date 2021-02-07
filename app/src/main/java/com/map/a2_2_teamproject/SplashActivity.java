package com.map.a2_2_teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView text1;
    TextView text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        text1 = findViewById(R.id.text_splash);
        text2 = findViewById(R.id.text_Splash2);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        text1.startAnimation(animation);
        text2.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),setAdress.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}