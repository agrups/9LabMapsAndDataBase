package com.example.a9labmapanddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

// [START maps_marker_on_map_ready]
public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    MyDatabaseHelper myDB;
    ArrayList<String> id, latitude, longitude;

    // [START_EXCLUDE]
    // [START maps_marker_get_map_async]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new MyDatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        latitude = new ArrayList<>();
        longitude = new ArrayList<>();
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_main);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                LatLng location = new LatLng(Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)));
                googleMap.addMarker(new MarkerOptions().position(location));
            }
        }

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // [END_EXCLUDE]
    }
    // [END maps_marker_on_map_ready_add_marker]
}
// [END maps_marker_on_map_ready]