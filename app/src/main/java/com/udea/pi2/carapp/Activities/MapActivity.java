package com.udea.pi2.carapp.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.udea.pi2.carapp.R;


public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(6.267577,-75.5706907);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 17.0f ));
        mMap.setOnMapLongClickListener(this);
    }

    public Context getContext(){
        return this;
    }

    public LatLng getLatLng(){
        return new LatLng(this.lat,this.lng);
    }

    public void resultActivity(LatLng latLng){
        Intent data = new Intent();
        //---set the data to pass back---
        data.setData(Uri.parse(String.valueOf(latLng.latitude) +","+ String.valueOf(latLng.longitude) ));
        setResult(RESULT_OK, data);
        //---close the activity---
        finish();
    }


    @Override
    public void onMapLongClick(final LatLng latLng) {
        mMap.clear();
        MarkerOptions marker = new MarkerOptions().position(latLng);
        mMap.addMarker(marker);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comfirmar");
        builder.setMessage("¿Desea selecionar este punto? \n" + latLng);
        // Add the buttons
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resultActivity(latLng);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
