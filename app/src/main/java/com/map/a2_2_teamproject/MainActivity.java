package com.map.a2_2_teamproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.map.a2_2_teamproject.ApiInterface.ApiClient;
import com.map.a2_2_teamproject.ApiInterface.ApiInterface;
import com.map.a2_2_teamproject.model.category_search.CategoryResult;
import com.map.a2_2_teamproject.model.category_search.Document;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;
import static net.daum.mf.map.api.MapView.setMapTilePersistentCacheEnabled;
public class MainActivity extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.OpenAPIKeyAuthenticationResultListener,MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
    MapView mapView;
    double x;    // 위도 변수
    double y;   // 경도 변수
    int i = 0;
    Button up;
    Button down;
    Button back;

    //태혁이 넣은 변수
    MapPoint currentMapPoint;
    private double mCurrentLng; //Long = X, Lat = Yㅌ
    private double mCurrentLat;
    private double mSearchLng;
    private double mSearchLat;
    private String mSearchName;
    boolean isTrackingMode = false;

    ArrayList<Document> bigMartList = new ArrayList<>();
    ArrayList<Document> documentArrayList = new ArrayList<>(); //지역명 검색 결과 리스트

    MapPOIItem searchMarker = new MapPOIItem();

    //태혁 변수 끝


    //내위치
    private static final String LOG_TAG = "MainActivity";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//화면 못움직이게
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        //tab, viewpager연동
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(viewPager);


        mapView = new MapView(this); // MapView 객체
        setMapTilePersistentCacheEnabled(true); // 한번 로드시 캐시에 저장
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view); // id 연결

        mapViewContainer.addView(mapView);

        Intent intent = getIntent();
        double a = intent.getDoubleExtra("위도1", 1);
        double b = intent.getDoubleExtra("경도1", 2);
        double c = intent.getDoubleExtra("위도2", 3);
        double d = intent.getDoubleExtra("경도2", 4);

        //marker 지정!!
        final MapPOIItem marker1 = new MapPOIItem();
        final MapPOIItem marker2 = new MapPOIItem();
        final MapPOIItem marker3 = new MapPOIItem();
        final MapPOIItem marker4 = new MapPOIItem();
        final MapPOIItem marker5 = new MapPOIItem();

        marker3.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven); // 하늘에서 날라오는 애니메이션 마커 생성 전에 선언 해야함.


        //마크1 지정!!!!!!!!!
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(a, b);   // 임의의 MapPoint 객체를 만듬
        marker1.setItemName("나의 위치");
        marker1.setTag(0);
        marker1.setMapPoint(mapPoint);
        marker1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker1);
        mapView.selectPOIItem(marker1, true); // 말풍선 항상 노출 허용
        //마크1지정!!!!!!!!!
        /* custom marker
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(a, b);
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName("나의 위치");
        customMarker.setTag(1);
        customMarker.setMapPoint(mapPoint);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.marker1); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        customMarker.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(30,0));
        mapView.addPOIItem(customMarker);
        */
        //마크2 지정!!!!!!!!!
        MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(c, d);   // 임의의 MapPoint 객체를 만듬
        marker2.setItemName("친구 위치");
        marker2.setTag(1);
        marker2.setMapPoint(mapPoint2);
        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker2);
        mapView.selectPOIItem(marker2, true); // 말풍선 항상 노출 허용
        //마크2지정!!!!!!!!!

        final double e = (a + c) / 2;
        final double f = (b + d) / 2;

        //마크3 지정!!!!!!!!!
        MapPoint mapPoint3 = MapPoint.mapPointWithGeoCoord(e, f);   // 임의의 MapPoint 객체를 만듬
        marker3.setItemName("중간 지점");
        marker3.setTag(1);
        marker3.setMapPoint(mapPoint3);
        marker3.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker3);
        mapView.selectPOIItem(marker3, true); // 말풍선 항상 노출 허용
        //마크3지정!!!!!!!!!


        //polyline!!!!!!!!!!!!
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(255, 255, 169, 77)); // Polyline 컬러 지정.

        // Polyline 좌표 지정.
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(a, b));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(c, d));
        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 150; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
        //polyline!!!!!!!!!!!!

        final int radi[] = {1000, 2000, 3000, 4000, 5000, 10000};
        final int[] i = {2};
        final MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(e, f), // center
                radi[i[0]], // radius
                Color.argb(255, 255, 169, 77), // strokeColor
                Color.argb(125, 173, 181, 189) // fillColor
        );
        circle1.setTag(1234);
        mapView.addCircle(circle1);



        /*지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정. circle
        MapPointBounds[] mapPointBoundsArray = { circle1.getBound() };
        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
        int padding = 200; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
        */

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, setAdress.class);
                mapView.removePOIItem(marker1);
                mapView.removePOIItem(marker2);
                mapView.removePOIItem(marker3);
                mapView.removeCircle(circle1);
                startActivity(intent);

            }
        });


        up = findViewById(R.id.btn_up);
        down = findViewById(R.id.btn_down);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // 확장 버튼 구현
                i[0] = i[0] + 1;
                if (i[0] < radi.length) {
                    mapView.removeCircle(circle1);
                    circle1.setRadius(radi[i[0]]);
                    final MapCircle circle1 = new MapCircle(
                            MapPoint.mapPointWithGeoCoord(e, f), // center
                            radi[i[0]], // radius
                            Color.argb(255, 255, 169, 77), // strokeColor
                            Color.argb(125, 173, 181, 189) // fillColor
                    );
                    circle1.setTag(1234);
                    mapView.addCircle(circle1);


                    // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
                    MapPointBounds[] mapPointBoundsArray = {circle1.getBound()};
                    MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
                    int padding = 200; // px
                    mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
                } else {
                    Toast.makeText(getApplicationContext(), "최대 반경 입니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // 축소 버튼 구현
                i[0] = i[0] - 1;
                if (i[0] >= 0) {
                    mapView.removeCircle(circle1);
                    circle1.setRadius(radi[i[0]]);
                    final MapCircle circle1 = new MapCircle(
                            MapPoint.mapPointWithGeoCoord(e, f), // center
                            radi[i[0]], // radius
                            Color.argb(255, 255, 169, 77), // strokeColor
                            Color.argb(125, 173, 181, 189) // fillColor
                    );
                    circle1.setTag(1234);
                    mapView.addCircle(circle1);


                    // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
                    MapPointBounds[] mapPointBoundsArray = {circle1.getBound()};
                    MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
                    int padding = 200; // px
                    mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
                } else {
                    Toast.makeText(getApplicationContext(), "최소 반경 입니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mapView.setMapViewEventListener(this); // this에 MapView.MapViewEventListener 구현.
        mapView.setPOIItemEventListener(this);
        onMapViewInitialized(mapView);


        // 내 위치 버튼 구현
        final Drawable mylocation_icon = getDrawable(R.drawable.ic_baseline_change_location_searching_24);
        mapView.setCurrentLocationEventListener(this);
        final Button mylocation = findViewById(R.id.btn_mylocation);
        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylocation.setBackground(mylocation_icon);
                if (!checkLocationServicesStatus()) {
                    showDialogForLocationServiceSetting();
                }else {

                    checkRunTimePermission();
                }
            }
        });


    }

    //태혁 통신 메소드
    private void requestSearchLocal(double x, double y) {

        //여밑에가 통신 코드를 메인에서 세팅해서 필요한거 불러오기
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);   // ApiInterface 객체에 레트로핏 객체를 매핑시키는 부분이다.
        Call<CategoryResult> call = apiInterface.getSearchCategory("MT1", x + "", y + "", 1000); //레트로핏 객체를 통해 만들어논 요청 메소드에 원하는거 담아서 보내기
        call.enqueue(new Callback<CategoryResult>() { //실행하는 메소드
            @Override
            public void onResponse(@NonNull Call<CategoryResult> call, @NonNull Response<CategoryResult> response) {

                if (response.isSuccessful()) {


                    bigMartList.addAll(response.body().getDocuments()); //리스트안에 응답받아온것을(documents를) 넣는다.
                    Log.d(LOG_TAG, "bigMartList Success");


//                   통신 성공 시 circle 생성
                    MapCircle circle1 = new MapCircle(
                            mapPointWithGeoCoord(y, x), // center
                            1000, // radius
                            Color.argb(128, 255, 0, 0), // strokeColor
                            Color.argb(128, 0, 255, 0) // fillColor
                    );
                    circle1.setTag(1234);
                    mapView.addCircle(circle1);
                    Log.d("SIZE1", bigMartList.size() + "");

                    int tagNum = 0;
                    for (Document document : bigMartList) {
                        MapPOIItem marker = new MapPOIItem();

                        marker.setItemName(document.getPlaceName());
                        marker.setTag(tagNum++);
                        double x = Double.parseDouble(document.getY());
                        double y = Double.parseDouble(document.getX());
                        //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
                        MapPoint mapPoint = mapPointWithGeoCoord(x, y);
                        marker.setMapPoint(mapPoint);
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
//                        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
//                        marker.setCustomImageResourceId(R.drawable.ic_add_black_24dp); // 마커 이미지.
//                        marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//                        marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                        mapView.addPOIItem(marker);
                    }

                }
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {
                Log.d(LOG_TAG, "FAIL");

            }
        });



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setShowCurrentLocationMarker(false);
    }

    @Override //태혁이 여기 추가함
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
        currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
//        이 좌표로 지도 중심 이동
        mapView.setMapCenterPoint(currentMapPoint, true);
//        전역변수로 현재 좌표 저장
        mCurrentLat = mapPointGeo.latitude;
        mCurrentLng = mapPointGeo.longitude;
        Log.d(LOG_TAG, "현재위치 => " + mCurrentLat + "  " + mCurrentLng);
//        mLoaderLayout.setVisibility(View.GONE);
        //트래킹 모드가 아닌 단순 현재위치 업데이트일 경우, 한번만 위치 업데이트하고 트래킹을 중단시키기 위한 로직
        if (!isTrackingMode) {
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        }
    }


    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }




    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }



    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint, MapPolyline polyline) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}