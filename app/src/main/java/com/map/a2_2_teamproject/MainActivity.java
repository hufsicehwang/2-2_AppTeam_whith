package com.map.a2_2_teamproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
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

    double x;
    double y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mapView = new MapView(this);
        setMapTilePersistentCacheEnabled(true); // 한번 로드시 캐시에 저장
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.33785369170434, 127.25249383940952); // 위치를 지정해 주는 MapPoint 객체 생성
        mapView.setMapCenterPoint(mapPoint, true);

        mapViewContainer.addView(mapView);


        // 마크 지정!!!!!!!!!
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("My House");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, true);
        // 마크 지정!!!!!!!!!




        // Polyline 좌표 지정!!!!!!!!!!!!!!
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(126, 0, 0, 0)); // Polyline 컬러 지정. argb
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.33758955338443, 127.26937613483179));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.33640308871953, 127.25242953036509));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.33785369170434, 127.25249383940952));
        mapView.addPolyline(polyline); // Polyline 지도에 올리기.
        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
        // Polyline 좌표 지정!!!!!!!!!!!!!!



        mapView.setMapViewEventListener(this); // this에 MapView.MapViewEventListener 구현.
        mapView.setPOIItemEventListener(this);
        onMapViewInitialized(mapView);

        MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(x, y);
        onMapViewSingleTapped(mapView, mapPoint2);






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
        MapPOIItem marker2 = new MapPOIItem();
        marker2.setItemName("new marker");
        marker2.setTag(0);
        marker2.setMapPoint(mapPoint);
        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker2);
        mapView.selectPOIItem(marker2, true);

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