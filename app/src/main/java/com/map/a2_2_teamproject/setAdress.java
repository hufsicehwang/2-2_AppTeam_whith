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


    //밑에는 xml에 만들고 mSearchEdit 변수 선언
//    <EditText
//
//    android:id="@+id/map_et_search"
//    android:layout_width="400dp"
//    android:layout_height="wrap_content"
//    android:hint="검색 할 장소를 입력 후 선택해주세요" />

    //밑에는 xml에 만들고 map_recyclerview 변수 선언
//    <androidx.recyclerview.widget.RecyclerView
//    android:id="@+id/map_recyclerview"
//    android:layout_width="match_parent"
//    android:layout_height="400dp"
//    android:visibility="gone" />


    //태혁 변수 끝




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//화면 못움직이게
        setContentView(R.layout.activity_set_adress);

        bus.register(this); //정류소 등록

//        mSearchEdit = findViewById(R.id.map_et_search);
//    recyclerView = findViewById(R.id.map_recyclerview);
//    LocationAdapter locationAdapter = new LocationAdapter(documentArrayList, getApplicationContext(), mSearchEdit, recyclerView);
//    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); //레이아웃매니저 생성
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL)); //아래구분선 세팅
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(locationAdapter);


//    mSearchEdit.addTextChangedListener(new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//            // 입력하기 전에
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//            if (charSequence.length() >= 1) {
//                // if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
//
//                documentArrayList.clear();
//                locationAdapter.clear();
//                locationAdapter.notifyDataSetChanged();
//                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//                Call<CategoryResult> call = apiInterface.getSearchLocation( charSequence.toString(), 15); //통신하기위한 기본 세팅
//                call.enqueue(new Callback<CategoryResult>() {
//                    @Override
//                    public void onResponse(@NotNull Call<CategoryResult> call, @NotNull Response<CategoryResult> response) {
//                        if (response.isSuccessful()) {
//                            assert response.body() != null;
//                            for (Document document : response.body().getDocuments()) {
//                                locationAdapter.addItem(document);
//                            }
//                            locationAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull Call<CategoryResult> call, @NotNull Throwable t) {
//
//                    }
//                });
//                //}
//                //mLastClickTime = SystemClock.elapsedRealtime();
//            } else {
//                if (charSequence.length() <= 0) {
//                    recyclerView.setVisibility(View.GONE);
//                }
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            // 입력이 끝났을 때
//        }
//    });
//
//        mSearchEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View view, boolean hasFocus) {
//            if (hasFocus) {
//            } else {
//                recyclerView.setVisibility(View.GONE);
//            }
//        }
//    });
      
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

    @Override
    public void finish() {
        super.finish();
        bus.unregister(this); //이액티비티 떠나면 정류소 해제해줌
    }



}