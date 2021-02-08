package com.map.a2_2_teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import com.map.a2_2_teamproject.adapter.LocationAdapter;
import com.map.a2_2_teamproject.model.category_search.Document;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class setAdress extends AppCompatActivity {
    double a = 37.33758102688846;
    double b= 127.25225784025962;
    double c =37.385852652152685;
    double d = 127.12314656659213;
    Button btn;

    //태혁이 넣은 변수
    EditText mSearchEdit;
    RecyclerView recyclerView;
    TextView textView;
    private double mSearchLng;
    private double mSearchLat;
    private String mSearchName;

    Bus bus = BusProvider.getInstance();
    ArrayList<Document> documentArrayList = new ArrayList<>(); //지역명 검색 결과 리스트
    MapPOIItem searchMarker = new MapPOIItem();

    Button btn_search;
    LinearLayoutManager linearLayoutManager;
    ArrayList arrayList;
    SubAdapter subAdapter;

    //태혁 변수 끝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_adress);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//화면 못움직이게
        bus.register(this); //정류소 등록




        recyclerView = (RecyclerView) findViewById(R.id.rc_sub);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        subAdapter = new SubAdapter(arrayList);
        recyclerView.setAdapter(subAdapter);


        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setAdress.this, searchActivity.class);
                startActivity(intent);
            }
        });
        /* 아이템 리스트 add
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

         */



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

//    @Subscribe //검색예시 클릭시 이벤트 오토버스 버스 일루온나
//    public void search(Document document) {//public항상 붙여줘야함
//        mSearchName = document.getPlaceName();
//        mSearchLng = Double.parseDouble(document.getX());
//        mSearchLat = Double.parseDouble(document.getY());
//
////        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mSearchLat, mSearchLng), true);
////        mapView.removePOIItem(searchMarker);
//        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
//        mMapView.removeAllCircles();
////        mMapView.removeAllPOIItems();
//
//
////        searchMarker.setItemName(mSearchName);
////        searchMarker.setTag(10000);
////        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(mSearchLat, mSearchLng);
////        searchMarker.setMapPoint(mapPoint);
////        searchMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
////        searchMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
////        //마커 드래그 가능하게 설정
//////        searchMarker.setDraggable(true);
////        mMapView.addPOIItem(searchMarker);

//    }

/*    @Override
    public void finish() {
        super.finish();
        bus.unregister(this); //이액티비티 떠나면 정류소 해제해줌
    }


*/
}
