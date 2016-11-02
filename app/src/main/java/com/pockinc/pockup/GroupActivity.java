package com.pockinc.pockup;

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

public class GroupActivity extends AppCompatActivity {

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
            String jsonInput = "[\"Group 1\"]";
            JSONArray jsonArray = new JSONArray(jsonInput);
            int length = jsonArray.length();
            List<String> listContents = new ArrayList<String>(length);
            for (int i = 0; i < length; i++)
            {
                listContents.add(jsonArray.getString(i));
            }

            ListView myListView = (ListView) findViewById(R.id.group_list_view);
            myListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listContents));
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





}
