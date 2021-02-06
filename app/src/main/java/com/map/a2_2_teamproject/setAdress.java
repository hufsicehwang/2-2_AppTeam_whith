package com.map.a2_2_teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class setAdress extends AppCompatActivity {
    double a = 37.33758102688846;
    double b= 127.25225784025962;
    double c =37.385852652152685;
    double d = 127.12314656659213;
    Button btn;
    Button btn_search;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList arrayList;
    SubAdapter subAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_adress);

        recyclerView = (RecyclerView) findViewById(R.id.rc_sub);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        subAdapter = new SubAdapter(arrayList);
        recyclerView.setAdapter(subAdapter);

        btn_search = findViewById(R.id.btn_search);
        final SubData subData1 = new SubData(R.drawable.person1,"첫번째 출발지점 \uD83D\uDE03","서현역");
        final SubData subData2 = new SubData(R.drawable.person2,"두번째 출발지점 \uD83D\uDE04","서현역");
        final SubData subData3 = new SubData(R.drawable.person3,"세번째 출발지점 \uD83D\uDE00","서현역");
        final SubData subData4 = new SubData(R.drawable.person4,"네번째 출발지점 \uD83D\uDE01","서현역");
        final int[] i = {1};
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i[0] == 1){
                    arrayList.add(subData1);
                }
                else if(i[0] == 2){
                    arrayList.add(subData2);
                }
                else if(i[0] == 3){
                    arrayList.add(subData3);
                }
                else if(i[0] == 4){
                    arrayList.add(subData4);
                }
                else{
                    Toast.makeText(getApplicationContext(), "최대 인원수 입니다!", Toast.LENGTH_SHORT).show();
                }
                i[0]++;
                subAdapter.notifyDataSetChanged();
            }
        });





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