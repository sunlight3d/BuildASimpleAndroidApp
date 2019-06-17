package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapp.com.example.myapp.models.Place;

import java.util.ArrayList;

public class PlacesActivity extends Activity {
    private RecyclerView placesRecyclerView;
    private RecyclerView.Adapter placesAdapter;
    private ArrayList<Place> placesData = new ArrayList<Place>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String email = extras.getString("email");
        }
        placesRecyclerView = findViewById(R.id.placesRecyclerView);
        //fake data

        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place"));
        placesData.add(new Place("ABC park", "An exciting park"));
        placesData.add(new Place("XY place", "An good park"));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place"));
        placesData.add(new Place("ABC park", "An exciting park"));
        placesData.add(new Place("XY place", "An good park"));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place"));
        placesData.add(new Place("ABC park", "An exciting park"));
        placesData.add(new Place("XY place", "An good park"));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place"));
        placesData.add(new Place("ABC park", "An exciting park"));
        placesData.add(new Place("XY place", "An good park"));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place"));
        placesData.add(new Place("ABC park", "An exciting park"));
        placesData.add(new Place("XY place", "An good park"));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place"));
        placesData.add(new Place("ABC park", "An exciting park"));
        placesData.add(new Place("XY place", "An good park"));


        placesAdapter = new PlacesAdapter(placesData);
        ((PlacesAdapter) placesAdapter).setPlacesActivity(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        placesRecyclerView.setAdapter(placesAdapter);
        placesRecyclerView.setLayoutManager(layoutManager);
    }
    public void navigateToDetailPlace(Integer position) {
        Intent intent = new Intent(PlacesActivity.this, DetailPlaceActivity.class);
        Place selectedPlace = placesData.get(position);
        intent.putExtra("selectedPlace", selectedPlace);
        startActivity(intent);
    }
}
