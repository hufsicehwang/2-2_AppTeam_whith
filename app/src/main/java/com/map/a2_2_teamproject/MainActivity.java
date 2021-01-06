package com.map.a2_2_teamproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.CameraUpdateFactory;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mapView = new MapView(this); // MapView 객체
        setMapTilePersistentCacheEnabled(true); // 한번 로드시 캐시에 저장
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view); // id 연결

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x, y);   // 임의의 MapPoint 객체를 만듬


        mapViewContainer.addView(mapView);
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.337538375685774, 127.25230075560606), 3, true);


        /* 마크 지정!!!!!!!!!
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("My House");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, true);
        마크지정!!!!!!!!!*/

        //poly 라인 만들기!!!!!!!!!!!!
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.

        // Polyline 좌표 지정.
        double a = 37.337538375685774;
        double b = 127.25230075560606;
        double c = 37.38563109876927;
        double d =127.12332776050219;
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(a, b));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(c,d));
        double e = (37.337538375685774 + 37.38563109876927) / 2;
        double f = (127.25230075560606 + 127.12332776050219) / 2;
        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));


        //poly 라인 만들기!!!!!!!!!!!!
        MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(e,f);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Center");
        marker.setTag(0);
        marker.setMapPoint(mapPoint2);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, true);









        mapView.setMapViewEventListener(this); // this에 MapView.MapViewEventListener 구현.
        mapView.setPOIItemEventListener(this);
        onMapViewInitialized(mapView);


        onMapViewSingleTapped(mapView, mapPoint);





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

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        MapPOIItem marker = new MapPOIItem();
        marker.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven); // 하늘에서 날라오는 애니메이션 마커 생성 전에 선언 해야함.//
        if(i==0|| i==1 ) {
            String num = String.valueOf(i);
            marker.setItemName(num);
            marker.setTag(0);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mapView.selectPOIItem(marker, true);
            marker.setMapPoint(mapPoint);
            mapView.addPOIItem(marker);
            mapView.selectPOIItem(marker, true);

        }
        else if(i == 2){
            String num = String.valueOf(i);
            marker.setItemName(num);
            marker.setTag(1);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mapView.selectPOIItem(marker, true);
            marker.setMapPoint(mapPoint);
            mapView.addPOIItem(marker);
            mapView.selectPOIItem(marker, true);
        }
        else{
            Toast.makeText(getApplicationContext(), "두개 이상 마커 생성이 불가능 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        ++i;

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
