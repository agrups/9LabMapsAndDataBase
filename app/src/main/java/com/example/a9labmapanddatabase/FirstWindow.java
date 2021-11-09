package com.example.a9labmapanddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstWindow extends AppCompatActivity {
    private static final String TAG = "Pranesimas";
    EditText addressInput;
    Button saveButton;
    Button showMapButton;
    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_window);

        saveButton = findViewById(R.id.saveButton);
        showMapButton = findViewById(R.id.mapButton);
        addressInput = findViewById(R.id.koordinates);
        init();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress(v);
            }
        });

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapWindow(v);
            }
        });
    }

    private void init(){
        Log.d(TAG, "init: initializing");

        addressInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = addressInput.getText().toString();

        Geocoder geocoder = new Geocoder(FirstWindow.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);
            latitude = String.valueOf(address.getLatitude());
            longitude = String.valueOf(address.getLongitude());

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            Log.e(TAG, latitude );
            Log.e(TAG, longitude );
            Toast.makeText(this, "Rasta", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAddress(View v) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(FirstWindow.this);
        myDB.addLocation(latitude, longitude);
    }

    private void showMapWindow(View v) {
        Intent showMapWindow = new Intent(FirstWindow.this, MainActivity.class);
        startActivity(showMapWindow);
    }
}