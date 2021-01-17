package com.map.a2_2_teamproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;


import static net.daum.mf.map.api.MapView.setMapTilePersistentCacheEnabled;
public class MainActivity extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.OpenAPIKeyAuthenticationResultListener{
    double x;    // 위도 변수
    double y;   // 경도 변수
    int i = 0;
    Button up;
    Button down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MapView mapView = new MapView(this); // MapView 객체
        setMapTilePersistentCacheEnabled(true); // 한번 로드시 캐시에 저장
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view); // id 연결

        mapViewContainer.addView(mapView);

        Intent intent = getIntent();
        double a = intent.getDoubleExtra("위도1", 1);
        double b = intent.getDoubleExtra("경도1", 2);
        double c = intent.getDoubleExtra("위도2", 3);
        double d = intent.getDoubleExtra("경도2", 4);
        MapPOIItem marker = new MapPOIItem();

        marker.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven); // 하늘에서 날라오는 애니메이션 마커 생성 전에 선언 해야함.




        //마크1 지정!!!!!!!!!
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(a, b);   // 임의의 MapPoint 객체를 만듬
        marker.setItemName("나의 위치");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        //마크1지정!!!!!!!!!
        //마크2 지정!!!!!!!!!
        MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(c, d);   // 임의의 MapPoint 객체를 만듬
        marker.setItemName("친구 위치");
        marker.setTag(1);
        marker.setMapPoint(mapPoint2);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        //마크2지정!!!!!!!!!
        final double e = (a+c) /2;
        final double f = (b+d) /2;
        //마크3 지정!!!!!!!!!
        MapPoint mapPoint3 = MapPoint.mapPointWithGeoCoord(e, f);   // 임의의 MapPoint 객체를 만듬
        marker.setItemName("중간 지점");
        marker.setTag(1);
        marker.setMapPoint(mapPoint3);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        //마크3지정!!!!!!!!!
        mapView.selectPOIItem(marker, true);

        //polyline!!!!!!!!!!!!
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.

        // Polyline 좌표 지정.
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(a, b));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(c,d));
        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

        /* 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 150; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
        polyline!!!!!!!!!!!!*/

        final int radi = 4000;
        final int upnum = 1000;
        final MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(e,f), // center
                radi, // radius
                Color.argb(128, 255, 0, 0), // strokeColor
                Color.argb(128, 0, 255, 0) // fillColor
        );
        circle1.setTag(1234);
        mapView.addCircle(circle1);


        // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
        MapPointBounds[] mapPointBoundsArray = { circle1.getBound() };
        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
        int padding = 200; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

        up = findViewById(R.id.btn_up);
        down = findViewById(R.id.btn_down);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radii =radi+ upnum;
                mapView.removeCircle(circle1);
                final MapCircle circle1 = new MapCircle(
                        MapPoint.mapPointWithGeoCoord(e,f), // center
                        radii, // radius
                        Color.argb(128, 255, 0, 0), // strokeColor
                        Color.argb(128, 0, 255, 0) // fillColor
                );
                circle1.setTag(1234);
                mapView.addCircle(circle1);


                // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
                MapPointBounds[] mapPointBoundsArray = { circle1.getBound() };
                MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
                int padding = 200; // px
                mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
            }
        });










        mapView.setMapViewEventListener(this); // this에 MapView.MapViewEventListener 구현.
        mapView.setPOIItemEventListener(this);
        onMapViewInitialized(mapView);






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

    }
}
