package com.example.ads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;;

import android.os.Bundle;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    private LocationManager locationManager;
    private String provider;
    private MyLocationListener myListener;
    private String cityName, stateName, countryName, postalCode;
    private double latitude,longitude;
private CardView sch,gy,hosp,hot,coff,elect;
//private TextView

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(false);
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            int REQUEST_LOCATION = 99;
            ActivityCompat.requestPermissions(Dashboard.this, new String[]
                    {ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        final Location location = locationManager.getLastKnownLocation(provider);

        myListener = new MyLocationListener();

        if (location != null) {

            myListener.onLocationChanged(location);
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            final AlertDialog alertDialog = new AlertDialog.Builder(Dashboard.this).create();
            alertDialog.setTitle("Obtaining Location ...");
            alertDialog.setMessage("00:12");
            alertDialog.show();

            new CountDownTimer(12000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    alertDialog.setMessage("00:" + (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    alertDialog.dismiss();
                }
            }.start();
        }
        if (ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        locationManager.requestLocationUpdates(provider, 200, 1, myListener);
        if (location != null) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            latitude = Double.parseDouble(String.valueOf(location.getLatitude()));
            longitude = Double.parseDouble(String.valueOf(location.getLongitude()));
            Toast.makeText(Dashboard.this, String.valueOf(location.getLatitude()) + location.getLongitude() + cityName,
                    Toast.LENGTH_SHORT).show();

            Geocoder geocoder = new Geocoder(Dashboard.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(lat, lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert addresses != null;
            cityName = addresses.get(0).getLocality();
            stateName = addresses.get(0).getAdminArea();
            countryName = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
        } else {
            Toast.makeText(Dashboard.this, "Please open your location", Toast.LENGTH_SHORT).show();
        }
        sch = findViewById(R.id.schoolCard);
        gy = findViewById(R.id.gymCard);
        hosp = findViewById(R.id.hospitalCard);
        hot = findViewById(R.id.hotelCard);
        coff = findViewById(R.id.coffeeCard);
        elect = findViewById(R.id.electronicsCard);

        // Set OnClickListener for each CardView
        sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity("School",latitude,longitude);
            }
        });

        gy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity("Gym",latitude,longitude);
            }
        });

        hosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity("Hospital",latitude,longitude);
            }
        });

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity("Hotel",latitude,longitude);
            }
        });

        coff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity("Coffee Shop",latitude,longitude);
            }
        });

        elect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextActivity("Electronics Store",latitude,longitude);
            }
        });

    }

    private void openNextActivity(String cardName,double lat,double lon) {
        Intent intent = new Intent(Dashboard.this, Testing.class);
        intent.putExtra("CARD_NAME", cardName);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        startActivity(intent);
    }
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            latitude = location.getLatitude();
            longitude =location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(Dashboard.this, provider + "'s status changed to " + status + "!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            Toast.makeText(Dashboard.this, "Provider " + provider + " enabled!",
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            Toast.makeText(Dashboard.this, "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

