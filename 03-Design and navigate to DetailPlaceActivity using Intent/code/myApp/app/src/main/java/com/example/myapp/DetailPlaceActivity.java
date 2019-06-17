package com.example.myapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.com.example.myapp.models.Place;

public class DetailPlaceActivity extends Activity {
    private Place selectedPlace;
    private TextView txtPlaceName;
    private TextView txtDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        selectedPlace = (Place)getIntent().getExtras().getSerializable("selectedPlace");
        Toast.makeText(this,selectedPlace.toString(), Toast.LENGTH_LONG).show();

        txtPlaceName = (TextView) findViewById(R.id.txtPlaceName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtPlaceName.setText(selectedPlace.getPlaceName());
        txtDescription.setText(selectedPlace.getDescription());
    }
}
