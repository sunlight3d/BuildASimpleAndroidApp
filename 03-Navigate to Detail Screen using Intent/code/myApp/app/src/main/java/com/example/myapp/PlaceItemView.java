package com.example.myapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PlaceItemView extends RecyclerView.ViewHolder {
    public TextView txtPlaceName;
    public TextView txtDescription;
    private PlacesActivity placesActivity;

    public void setPlacesActivity(PlacesActivity placesActivity) {
        this.placesActivity = placesActivity;
    }

    public PlaceItemView(View view) {
        super(view);
        txtPlaceName = (TextView) view.findViewById(R.id.txtPlaceName);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceItemView.this.placesActivity.navigateToDetailPlace(getLayoutPosition());
            }
        });
    }
}
