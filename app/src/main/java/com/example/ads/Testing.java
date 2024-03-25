package com.example.ads;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Testing extends AppCompatActivity implements PlacesAdapter.OnItemClickListener{

    private RecyclerView recycler;
    private PlacesAdapter adapter;
    private List<Place> hotelList;
    private String queryName,imageUrl;
    private Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
         queryName = getIntent().getStringExtra("CARD_NAME");
         lat = getIntent().getDoubleExtra("lat",0);
         lon = getIntent().getDoubleExtra("lon",0);
        hotelList = new ArrayList<>();
        adapter = new PlacesAdapter(this,hotelList);
        recycler.setAdapter(adapter);
//        double latitude = 41.8781;
//        double longitude = -87.6298;

        fetchData(lat, lon);
    }

    private void fetchData(double lat, double lon) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.foursquare.com/v3/places/nearby?ll=" + lat + "%2C" + lon +
                "&query=" + queryName.toLowerCase() + "&limit=20")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "fsq3nkS+3mfv7FHWuI8LpVUUxzXVQEGSjLo7EECjMPblQjQ=")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    final String responseData = response.body().string();
                    Testing.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parseData(responseData);
                        }
                    });
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parseData(String responseData) {
        try {
            JSONObject jsonResponse = new JSONObject(responseData);
            JSONArray resultsArray = jsonResponse.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultObject = resultsArray.getJSONObject(i);

                String hotelName = resultObject.getString("name");
                String address = resultObject.getString("address");
                JSONObject iconObject = resultObject.getJSONObject("icon");
                String prefix = iconObject.getString("prefix");
                String suffix = iconObject.getString("suffix");
                 imageUrl = prefix + "88" + suffix;


                Place hotel = new Place(hotelName,lat,lon,address);
                hotelList.add(hotel);
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(Place place) {
        Intent intent = new Intent(Testing.this, DetailsActivity.class);
        intent.putExtra("place_name", place.getName());
        intent.putExtra("place_address", place.getVicinity());
        intent.putExtra("lat", place.getLatitude());
        intent.putExtra("lon", place.getLongitude());
        intent.putExtra("url", place.getFileUrl());
        startActivity(intent);
    }
}
