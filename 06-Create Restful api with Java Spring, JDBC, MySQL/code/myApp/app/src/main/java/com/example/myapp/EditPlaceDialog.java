package com.example.myapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp.com.example.myapp.models.Place;

public class EditPlaceDialog extends Dialog {
    private PlacesActivity placesActivity;
    private DetailPlaceActivity detailPlaceActivity;
    private Place selectedPlace;
    //UI
    private EditText txtPlaceName;
    private EditText txtDescription;
    private Button btnSave;

    EditPlaceDialog(Context context, PlacesActivity placesActivity,
                    Place selectedPlace, int layoutResId, DetailPlaceActivity detailPlaceActivity) {
        super(context);
        this.placesActivity = placesActivity;
        this.selectedPlace = selectedPlace;
        this.detailPlaceActivity = detailPlaceActivity;
        this.setContentView(layoutResId);
        setupUI();
    }
    private void setupUI() {
        txtPlaceName = (EditText) findViewById(R.id.txtPlaceName);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        btnSave = (Button)findViewById(R.id.btnSave);

        txtPlaceName.setText(selectedPlace.getPlaceName());
        txtDescription.setText(selectedPlace.getDescription());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPlace.setPlaceName(txtPlaceName.getText().toString().trim());
                selectedPlace.setDescription(txtDescription.getText().toString().trim());
                EditPlaceDialog.this.placesActivity.updatePlace(selectedPlace);
                EditPlaceDialog.this.detailPlaceActivity.setSelectedPlace(selectedPlace);
                EditPlaceDialog.this.dismiss();
            }
        });
    }
}
