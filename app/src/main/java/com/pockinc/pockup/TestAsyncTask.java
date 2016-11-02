package com.pockinc.pockup;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dell on 01/11/2016.
 */

public class TestAsyncTask extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private String mUrl;

    public TestAsyncTask(Context context, String url) {
        mContext = context;
        mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //dynamictext = (TextView) findViewById(R.id.dynamictext);
    }

    @Override
    protected String doInBackground(Void... params) {
        String resultString = null;
        resultString = getJSON(mUrl);

        return resultString;
    }

    @Override
    protected void onPostExecute(String strings) {
        super.onPostExecute(strings);
        //dynamictext.setText(strings);
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
