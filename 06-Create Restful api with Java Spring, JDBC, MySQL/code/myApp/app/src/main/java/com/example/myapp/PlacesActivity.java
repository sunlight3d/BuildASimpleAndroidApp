package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place", 11));
        placesData.add(new Place("ABC park", "An exciting park", 22));
        placesData.add(new Place("XY place", "An good park", 33));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place", 44));
        placesData.add(new Place("ABC park", "An exciting park", 55));
        placesData.add(new Place("XY place", "An good park", 66));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place", 77));
        placesData.add(new Place("ABC park", "An exciting park", 88));
        placesData.add(new Place("XY place", "An good park", 99));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place", 111));
        placesData.add(new Place("ABC park", "An exciting park", 222));
        placesData.add(new Place("XY place", "An good park", 333));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place", 333));
        placesData.add(new Place("ABC park", "An exciting park", 444));
        placesData.add(new Place("XY place", "An good park", 555));
        placesData.add(new Place("Hoan Kiem Lake", "This is a beautiful place", 666));
        placesData.add(new Place("ABC park", "An exciting park", 777));
        placesData.add(new Place("XY place", "An good park", 888));


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
        DetailPlaceActivity.placesActivity = this;
    }
    public void deletePlace(final Integer placeId) {
        placesData.removeIf(place -> place.getPlaceId().equals(placeId));
        placesAdapter.notifyDataSetChanged();
    }
    public void updatePlace(Place updatedPlace) {
        placesData.forEach(place -> {
            if(place.getPlaceId().equals(updatedPlace.getPlaceId())){
                place.setPlaceName(updatedPlace.getPlaceName());
                place.setDescription(updatedPlace.getDescription());
            }
        });
    }
}
