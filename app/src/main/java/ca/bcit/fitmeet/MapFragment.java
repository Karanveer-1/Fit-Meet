package ca.bcit.fitmeet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import ca.bcit.fitmeet.dashboard.model.Feature;

public class MapFragment extends Fragment {
    private MapView mMapView;
    private MapboxMap mMapboxMap;
    private LatLng location;
    private Feature feature;

    public MapFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getActivity());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        feature = (Feature) getArguments().getSerializable("feature");

        double latitude = feature.getGeometry().getCoordinates().get(1);
        double longitude = feature.getGeometry().getCoordinates().get(0);

        location = new LatLng(latitude, longitude);

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = view.findViewById(R.id.myMapView);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapView.setStreetMode();
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
                addMarker(mapboxMap);
            }
        });

        final NestedScrollView sv = getActivity().findViewById(R.id.nestedScrollView);

        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        sv.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        sv.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return mMapView.onTouchEvent(event);
            }
        });
    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(feature.getProperties().getName());
        mapboxMap.addMarker(markerOptions);
    }

}
