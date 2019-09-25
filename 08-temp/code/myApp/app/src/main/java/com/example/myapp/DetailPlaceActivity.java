package com.example.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import com.google.android.gms.*;


import com.example.myapp.com.example.myapp.models.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailPlaceActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static PlacesActivity placesActivity;
    private GoogleMap mMap;

    public void setSelectedPlace(Place selectedPlace) {
        this.selectedPlace = selectedPlace;
        txtPlaceName.setText(selectedPlace.getPlaceName());
        txtDescription.setText(selectedPlace.getAddress());
    }

    private Place selectedPlace;
    private SupportMapFragment mapView;
    private TextView txtPlaceName;
    private TextView txtDescription;
    private Button btnDelete;
    //API key: AIzaSyDkqCgV1Nl_-lVNiPwIkMy7TVSVNgu0wII


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        selectedPlace = (Place)getIntent().getExtras().getSerializable("selectedPlace");

        txtPlaceName = (TextView) findViewById(R.id.txtPlaceName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        btnDelete = findViewById(R.id.btnDelete);
        SupportMapFragment mapView = (SupportMapFragment)getSupportFragmentManager().
                findFragmentById(R.id.mapView);
        try {
            mapView.getMapAsync(this);
        }catch (Exception e) {

        }



        txtPlaceName.setText(selectedPlace.getPlaceName());
        txtDescription.setText(selectedPlace.getAddress());
        setupActions();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng currentLocation = new LatLng(selectedPlace.getLat(), selectedPlace.getLon());
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("current Location"));

        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(currentLocation, 5);
        mMap.animateCamera(yourLocation);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation), 4);
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
