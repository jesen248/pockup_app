package com.pockinc.pockup;
/*
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
*/



/*

ADDED FROM THE JSON TUTORIAL

CAN BE FOUND ON THIS PAGE

http://www.androidhive.info/2012/01/android-json-parsing-tutorial/



 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;







public class GroupActivity extends AppCompatActivity {

    private String TAG = GroupActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://pockup.herokuapp.com/api/categories/";
    //on this url we need to add {category_number}/groups

    ArrayList<HashMap<String, String>> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent = getIntent();

        int id = intent.getIntExtra("category_id",0);
        String message = String.valueOf(id);
        //int id = Integer.parseInt(message);

        url = "https://pockup.herokuapp.com/api/categories/" + message + "/groups";


        groupList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetGroups().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetGroups extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(GroupActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray groups = new JSONArray(jsonStr);

                    // sustitui contatcs con groups



                    // looping through All groups
                    for (int i = 0; i < groups.length(); i++) {
                        JSONObject c = groups.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String is_lucrative = c.getString("is_lucrative");
                        String description = c.getString("description");
                        String category_id = c.getString("category_id");
                        String created_at = c.getString("created_at");
                        String updated_at = c.getString("updated_at");


                        /*
                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");
                        */



                        // tmp hash map for single contact
                        HashMap<String, String> group = new HashMap<>();

                        // adding each child node to HashMap key => value
                        group.put("id", id);
                        group.put("name", name);
                        group.put("description", description);

                        // adding contact to contact list
                        groupList.add(group);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    GroupActivity.this, groupList,
                    R.layout.list_item, new String[]{"name", "description"}, new int[]{R.id.name,
                    R.id.description});

            lv.setAdapter(adapter);
        }

    }




























/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        //String jsonInput = getJSON("https://pockup.herokuapp.com/api/categories/1/groups");
        //TestAsyncTask testAsyncTask = new TestAsyncTask(GroupActivity.this, "https://pockup.herokuapp.com/api/categories/1/groups");
        //testAsyncTask.execute();




        // NEW CODE ADDED
        
        try
        {
            //"two","three","four","five","six","seven","eight","nine","ten"
            //String jsonInput = "[\"Group 1\"]";
            //String jsonInput = HttpHandler
            //HttpHandler handler = null;
            //String jsonInput = handler.makeServiceCall(url);


            String jsonInput = new TestAsyncTask(getApplicationContext(), url).get();
            JSONArray jsonArray = new JSONArray(jsonInput);
            int length = jsonArray.length();
            List<String> listContents = new ArrayList<String>(length);

            for (int i = 0; i < length; i++)
            {
                listContents.add(jsonArray.getString(i));
            }

            ListView myListView = (ListView) findViewById(R.id.group_list_view);
            //myListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listContents));
            //lv = (ListView) findViewById(R.id.group_list_view);
        }
        catch (Exception e)
        {
            // this is just an example
        }

    }










    public void returnToBackScreen(){


            //Snackbar.make(view, getResources().getResourceName(view.getId()), Snackbar.LENGTH_LONG);


            //Snackbar.make(view, getResources().getResourceName(view.getId()), Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();

        //Intent intent = new Intent(GroupActivity.this, MapsActivity.class);
        //    startActivity(intent);
    }


    private String getJSON(String url) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (Exception ex) {
            return ex.toString();
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    //disconnect error
                }
            }
        }
        return null;
    }




*/
}
