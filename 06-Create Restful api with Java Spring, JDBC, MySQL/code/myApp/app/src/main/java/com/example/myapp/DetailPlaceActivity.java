package com.example.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.com.example.myapp.models.Place;

public class DetailPlaceActivity extends Activity {
    public static PlacesActivity placesActivity;

    public void setSelectedPlace(Place selectedPlace) {
        this.selectedPlace = selectedPlace;
        txtPlaceName.setText(selectedPlace.getPlaceName());
        txtDescription.setText(selectedPlace.getDescription());
    }

    private Place selectedPlace;
    private TextView txtPlaceName;
    private TextView txtDescription;
    private Button btnDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        selectedPlace = (Place)getIntent().getExtras().getSerializable("selectedPlace");

        txtPlaceName = (TextView) findViewById(R.id.txtPlaceName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        btnDelete = findViewById(R.id.btnDelete);

        txtPlaceName.setText(selectedPlace.getPlaceName());
        txtDescription.setText(selectedPlace.getDescription());
        setupActions();
    }
    private void actionUpdatePlace() {
        //Show EditDialog
        EditPlaceDialog editPlaceDialog = new EditPlaceDialog(this,
                DetailPlaceActivity.placesActivity,
                selectedPlace,
                R.layout.edit_place,
                this);
        editPlaceDialog.show();

    }
    private void setupActions() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailPlaceActivity.this)
                        .setTitle("Delete a place")
                        .setMessage("Are you sure you want to delete this ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //When click "Yes"
                                DetailPlaceActivity.this.placesActivity.deletePlace(selectedPlace.getPlaceId());
                                finish();
                            }
                        }).
                        setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //When click "No"
                                finish();
                            }
                        }).show();
            }
        });
        txtPlaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionUpdatePlace();
            }
        });
        txtDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionUpdatePlace();
            }
        });
    }
}
