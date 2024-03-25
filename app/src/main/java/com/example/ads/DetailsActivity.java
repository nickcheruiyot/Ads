package com.example.ads;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private double lat ;
    private double lon ;
    private ImageView image ;
    private TextView title,address;
    private  String placeAddress,placeName,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiZGV2c2NyaWJiZSIsImEiOiJjbHU2NzJkNzIwejMwMmlueG9wMHZqazk0In0.Rk2LHEJ0AvUu4Z4z4_lNWw");
        setContentView(R.layout.activity_details);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        title=findViewById(R.id.titleTextView);
        address=findViewById(R.id.addressTextView);
        image=findViewById(R.id.imageView);


    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(lat, lon))
                    .zoom(12)
                    .tilt(20)
                    .build();
            mapboxMap.setCameraPosition(position);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
         placeName = getIntent().getStringExtra("place_name");
         placeAddress = getIntent().getStringExtra("place_address");
         url = getIntent().getStringExtra("url");
         lat = getIntent().getDoubleExtra("lat",0);
         lon = getIntent().getDoubleExtra("lon",0);
         title.setText(placeName);
         address.setText(placeAddress);
        Picasso.get().load(url).into(image);
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
