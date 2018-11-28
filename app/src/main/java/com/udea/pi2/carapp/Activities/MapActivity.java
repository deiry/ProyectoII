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
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.udea.pi2.carapp.R;


public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private double lat;
    private double lng;
    private MarkerOptions markerInit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ubicación de partida");
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        double lat = getIntent().getDoubleExtra("lat",0);
        double lng = getIntent().getDoubleExtra("lng",0);
        String titleMarker = getIntent().getStringExtra("tittleMarker");
        String tittleActivity = getIntent().getStringExtra("tittleActivity");

        if (tittleActivity != null && !tittleActivity.isEmpty()) {
            setTitle(tittleActivity);
        }

        //si no se le envia nada no dibuja el marcador
        if(lat != 0 && lng != 0){
            markerInit = new MarkerOptions()
                    .position(new LatLng(lat,lng))
                    .title(titleMarker);
        }

        /*GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyD0N_3KGyBk4aKtINZGf5BiQH2UXhEeMtU")
                .build();

        DirectionsApiRequest request = DirectionsApi.getDirections(context,"Universidad de Antioquia","Gobernación de Antioquia");
        try {
            request.await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                System.out.print(result.toString());
            }

            @Override
            public void onFailure(Throwable e) {
                System.out.print(e);
            }
        });*/

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LatLng udea = new LatLng(6.267577,-75.5706907);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(udea));
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 15.0f ));
        mMap.setOnMapLongClickListener(this);
        //MarkerOptions marker = new MarkerOptions().position(udea);
        //mMap.addMarker(marker);
        //add marker
        if(markerInit!=null){
            mMap.addMarker(markerInit);

        }

        setupGoogleMapScreenSettings(mMap);
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
        mMap.addMarker(markerInit);

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

    private void setupGoogleMapScreenSettings(GoogleMap mMap) {
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        //mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }
}
