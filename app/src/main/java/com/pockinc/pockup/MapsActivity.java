package com.pockinc.pockup;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public final static String EXTRA_MESSAGE = "com.pockinc.pockup.MESSAGE";
    private GoogleMap mMap;
    private int category_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Category Map");
        setContentView(R.layout.category_activity_maps);


        Intent intent = getIntent();
        int id = intent.getIntExtra(NavigationDrawerActivity.EXTRA_MESSAGE,0);
        category_id = id;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ensenada = new LatLng(31.859618,  -116.624181);
        mMap.addMarker(new MarkerOptions().position(ensenada).title("Marker in Ensenada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ensenada));



        // Add set my location button

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            mMap.setMyLocationEnabled(false);
        }


    }


    public void openGroupList(View view) {
        //Snackbar.make(view, getResources().getResourceName(view.getId()), Snackbar.LENGTH_LONG);


        //Snackbar.make(view, getResources().getResourceName(view.getId()), Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show();
        Intent intent = new Intent(MapsActivity.this, GroupActivity.class);



        intent.putExtra(EXTRA_MESSAGE,category_id);
        startActivity(intent);
    }




}
