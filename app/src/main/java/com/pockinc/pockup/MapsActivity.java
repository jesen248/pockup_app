package com.pockinc.pockup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Volley
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


//ending volley

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    ArrayList<HashMap<String, String>> placeList;
    ArrayList<HashMap<String, Double>> placeCoord;
    public final static String EXTRA_MESSAGE = "com.pockinc.pockup.MESSAGE";
    private GoogleMap mMap;
    //Intent intent = getIntent();
    //int id = intent.getIntExtra("category_id",0);
    //private int category_id= id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Category Map");
        setContentView(R.layout.category_activity_maps);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        String url ="https://pockup.herokuapp.com/api/categories/1/places";
        placeList = new ArrayList<>();
        placeCoord = new ArrayList<>();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray places = new JSONArray(response);

                            for (int i = 0; i < places.length(); i++) {
                                JSONObject c = places.getJSONObject(i);

                                String place_id = c.getString("id"); //c.getInt
                                String name = c.getString("name");
                                double lat = c.getDouble("lat");
                                double longitud = c.getDouble("long");
                                String address = c.getString("address");
                                String description = c.getString("description");
                                String photo = c.getString("photo");
                                String general_price = c.getString("general_price");
                                String user_id = c.getString("user_id");
                                String category_id = c.getString("category_id");
                                String created_at = c.getString("created_at");
                                String updated_at = c.getString("updated_at");


                                // tmp hash map for single contact
                                HashMap<String, String> place = new HashMap<>();
                                HashMap<String, Double> coord = new HashMap<>();

                                // adding each child node to HashMap key => value
                                place.put("place_id", place_id);
                                place.put("name", name);
                                place.put("description", description);
                                coord.put("lat",lat);
                                coord.put("long",longitud);

                                // adding contact to contact list
                                placeList.add(place);
                                placeCoord.add(coord);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // whatever does on errors
            }
        });


        // Add the request to the RequestQueue.
        queue.add(stringRequest);



        /*
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        int user_id = jsonResponse.getInt("id");
                        SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", username);
                        editor.putString("password",password);
                        editor.putInt("user_id",user_id);
                        editor.commit();


                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Granted")
                                .create()
                                .show();
                        //String name = jsonResponse.getString("username");
                        //Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);

                        //intent.putExtra("email", username);
                        //LoginActivity.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
*/



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



        //Retrieving data from the hashmap
        double lat=0,longitud=0;
        //lat = placeCoord.get(0).get("lat");
        //longitud = placeCoord.get(0).get("long");
        //Add a marker in Sydney and move the camera
        LatLng ensenada = new LatLng(lat,longitud );  // Gimnasio 31.866743 , -116.596371
        mMap.addMarker(new MarkerOptions().position(ensenada).title("Marker in" ));
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



        //intent.putExtra(EXTRA_MESSAGE,category_id);
        startActivity(intent);
    }




}
