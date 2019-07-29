package com.example.myapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class PlacesActivity extends AppCompatActivity {

    private TextView txtInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        txtInfo = (TextView) findViewById(R.id.txtInfo);
        
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String email = extras.getString("email");
            txtInfo.setText("Your email:"+email);
        }
        
    }
}
