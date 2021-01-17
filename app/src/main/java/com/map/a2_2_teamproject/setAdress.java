package com.map.a2_2_teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class setAdress extends AppCompatActivity {
    double a = 37.33758102688846;
    double b= 127.25225784025962;
    double c =37.385852652152685;
    double d = 127.12314656659213;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_adress);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setAdress.this, MainActivity.class);
                intent.putExtra("위도1",a);
                intent.putExtra("경도1",b);
                intent.putExtra("위도2",c);
                intent.putExtra("경도2",d);
                startActivity(intent);
            }
        });
    }
}