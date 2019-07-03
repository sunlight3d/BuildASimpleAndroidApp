package com.example.myapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.com.example.myapp.models.Place;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlaceItemView> {
    private ArrayList<Place> placesData;
    private PlacesActivity placesActivity;
    //constructor
    PlacesAdapter(ArrayList<Place> placesData) {
        this.placesData = placesData;
    }

    public void setPlacesActivity(PlacesActivity placesActivity) {
        this.placesActivity = placesActivity;
    }

    @NonNull
    @Override
    public PlaceItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item_view, viewGroup, false);
        PlaceItemView placeItemView = new PlaceItemView(view);
        placeItemView.setPlacesActivity(this.placesActivity);
        return placeItemView;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceItemView placeItemView, int i) {
        Place selectedPlace = placesData.get(i);
        String placeName = selectedPlace.getPlaceName();
        String description = selectedPlace.getDescription();
        placeItemView.txtPlaceName.setText(placeName);
        placeItemView.txtDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return placesData.size();
    }
}
