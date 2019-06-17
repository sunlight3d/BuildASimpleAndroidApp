package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static final String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]{2,}";
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView txtValidate;
    //Validation
    private Boolean isValidEmail = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Map properties in .xml file
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtValidate = (TextView)findViewById(R.id.txtValidate);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if(!isValidEmail) {
                    Toast.makeText(getApplicationContext(),"Validate email failed",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                */
                //Do something like "Navigate to second Activity"
                Intent intent = new Intent(MainActivity.this, PlacesActivity.class);
                intent.putExtra("email", txtEmail.getText().toString().trim());
                startActivity(intent);
            }
        });

        //validation
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Validate here
                txtValidate.setText("");
                String email = txtEmail.getText().toString().trim();
                isValidEmail = (email.matches(emailPattern) && s.length() > 0);
                if(!isValidEmail) {
                    txtValidate.setTextColor(Color.rgb(255,0,0));
                    txtValidate.setText("Invalid email address");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
