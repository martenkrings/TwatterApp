package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Sander on 3-6-2016.
 */
public class AssyncSearchTask extends AsyncTask {
    private static final String TAG = "AssyncSearchTask";
    private String searchParameters;

    public AssyncSearchTask(String searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL url = new URL("https://api.twitter.com/1.1/search/tweets.json?q=" + searchParameters);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //set headers
            conn.addRequestProperty("Authorization", "Bearer " + TwatterApp.getInstance().getBearerToken());
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            Log.d(TAG, "" + conn.getResponseCode());
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                InputStream is = conn.getInputStream();
                JSONArray jsonObjects = new JSONArray(IOUtils.toString(is));
                Log.d(TAG, jsonObjects.toString());
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, e.getMessage());
        } catch (ProtocolException e) {
            Log.d(TAG, e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
