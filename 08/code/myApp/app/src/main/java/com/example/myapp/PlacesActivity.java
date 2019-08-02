package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp.Server.Server;
import com.example.myapp.Server.ServerInterface;
import com.example.myapp.com.example.myapp.models.Place;

import java.util.ArrayList;

public class PlacesActivity extends Activity implements IWebService {
    private RecyclerView placesRecyclerView;
    private RecyclerView.Adapter placesAdapter;
    private ArrayList<Place> places = new ArrayList<Place>();
    private SwipeRefreshLayout mswipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String email = extras.getString("email");
        }
        placesRecyclerView = findViewById(R.id.placesRecyclerView);
        mswipeRefreshLayout = findViewById(R.id.mswipeRefreshLayout);
        getPlacesFromServer();
        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.d("aa", "pull to RRR");
                mswipeRefreshLayout.setRefreshing(true);
                PlacesActivity.this.getPlacesFromServer();
            }
        });
    }
    public void getPlacesFromServer() {
        Server server = new Server(this);
        server.queryPlaces(13,0,10);
    }
    public void reloadData() {
        placesAdapter = new PlacesAdapter(places);
        placesRecyclerView.setAdapter(placesAdapter);
        ((PlacesAdapter) placesAdapter).setPlacesActivity(this);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);

        placesRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void getResponse(Object responseObject, String errorMessage) {
        this.places = (ArrayList<Place>)responseObject;
        reloadData();
        mswipeRefreshLayout.setRefreshing(false);
    }

    public void navigateToDetailPlace(Integer position) {
        Intent intent = new Intent(PlacesActivity.this, DetailPlaceActivity.class);
        Place selectedPlace = places.get(position);
        intent.putExtra("selectedPlace", selectedPlace);
        startActivity(intent);
        DetailPlaceActivity.placesActivity = this;
    }
    public void deletePlace(final Integer placeId) {
        places.removeIf(place -> place.getPlaceId().equals(placeId));
        placesAdapter.notifyDataSetChanged();
    }
    public void updatePlace(Place updatedPlace) {
        places.forEach(place -> {
            if(place.getPlaceId().equals(updatedPlace.getPlaceId())){
                place.setPlaceName(updatedPlace.getPlaceName());
                place.setAddress(updatedPlace.getAddress());
            }
        });
    }
}
